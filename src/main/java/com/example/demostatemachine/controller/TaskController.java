package com.example.demostatemachine.controller;

import com.example.demostatemachine.controller.request.CreateTaskRequest;
import com.example.demostatemachine.controller.request.AssignRequest;
import com.example.demostatemachine.controller.request.EventRequest;
import com.example.demostatemachine.controller.request.EventResponse;
import com.example.demostatemachine.model.Task;
import com.example.demostatemachine.service.TaskService;
import com.example.demostatemachine.workflow.TaskEvents;
import com.example.demostatemachine.workflow.TaskStates;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RequiredArgsConstructor
@RestController
public class TaskController {

    private final TaskService taskService;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("tasks")
    public void createTask(@RequestBody final CreateTaskRequest createTaskRequest) {
        taskService.create(createTaskRequest.toTask());
    }

    @GetMapping("tasks/{id}")
    public Task getTask(@PathVariable("id") final long id) {
        return taskService.getById(id);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("tasks/assign")
    public Task assignTo(@RequestBody final AssignRequest assignRequest) {
        return taskService.assignTo(assignRequest.getWarehouseId(), assignRequest.getUserId());
    }

    @PostMapping("/task/events")
    public EventResponse sendEvent(@RequestBody final EventRequest eventRequest) {

        final TaskEvents taskEvent = TaskEvents.valueOf(eventRequest.getName().toUpperCase(Locale.ROOT));

        final StateMachine<TaskStates, TaskEvents> stateMachine =
                taskService.emitEvent(eventRequest.getWarehouseId(),
                        eventRequest.getUserId(),
                        taskEvent);

        final State<TaskStates, TaskEvents> currentState = stateMachine.getState();

        return EventResponse.builder()
                .id(stateMachine.getId())
                .uuid(stateMachine.getUuid().toString())
                .currentState(currentState.getId().toString().toLowerCase(Locale.ROOT))
                .build();
    }
}
