package com.example.demostatemachine.service;

import com.example.demostatemachine.model.Task;
import com.example.demostatemachine.model.TaskStatus;
import com.example.demostatemachine.repository.TaskRepository;
import com.example.demostatemachine.workflow.TaskEvents;
import com.example.demostatemachine.workflow.TaskStates;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TaskService {

    private final StateMachineService<TaskStates, TaskEvents> stateMachineService;
    private final TaskRepository taskRepository;

    @Transactional
    public void create(final Task task) {
        taskRepository.saveAndFlush(task);
    }

    @Transactional(readOnly = true)
    public Task getById(final long id) {
        return taskRepository.getById(id);
    }

    @Transactional
    public Task assignTo(final String warehouseId, final long userId) {
        List<Task> workingList =
                taskRepository.findByWarehouseIdAndStatusAndUserId(warehouseId, TaskStatus.WORKING, userId);
        if (!workingList.isEmpty()) {
            return workingList.get(0);
        }

        List<Task> taskList =
                taskRepository.findByWarehouseIdAndStatusAndUserIdIsNull(warehouseId, TaskStatus.PENDING);

        if (taskList.isEmpty()) {
            throw new RuntimeException("no tasks");
        }
        final Task task = taskList.get(0);

        final StateMachine<TaskStates, TaskEvents> stateMachine =
                stateMachineService.acquireStateMachine(task.getId().toString());
        task.assignTo(userId, warehouseId, stateMachine.getUuid().toString());
        return task;
    }

    @Transactional
    public StateMachine<TaskStates, TaskEvents> emitEvent(final String warehouseId,
                                                          final long userId,
                                                          final TaskEvents event) {

        final List<Task> tasks =
                taskRepository.findByWarehouseIdAndStatusAndUserId(warehouseId, TaskStatus.WORKING, userId);
        if (tasks.isEmpty()) {
            throw new RuntimeException("user do not have task working");
        }
        final Task task = tasks.get(0);
        if (task.getStatus() == TaskStatus.DONE) {
            throw new RuntimeException("task is done");
        }

        final StateMachine<TaskStates, TaskEvents> stateMachine =
                stateMachineService.acquireStateMachine(task.getId().toString());
        stateMachine.sendEvent(event);
        return stateMachine;
    }
}