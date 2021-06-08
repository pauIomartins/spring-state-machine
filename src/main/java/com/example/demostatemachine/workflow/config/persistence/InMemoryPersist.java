package com.example.demostatemachine.workflow.config.persistence;

import com.example.demostatemachine.workflow.TaskEvents;
import com.example.demostatemachine.workflow.TaskStates;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;

import java.util.HashMap;
import java.util.Map;

public class InMemoryPersist implements StateMachinePersist<TaskStates, TaskEvents, String> {

    private final Map<String, StateMachineContext<TaskStates, TaskEvents>> storage = new HashMap<>();

    @Override
    public void write(final StateMachineContext<TaskStates, TaskEvents> context, String contextObj) {
        storage.put(contextObj, context);
    }

    @Override
    public StateMachineContext<TaskStates, TaskEvents> read(final String contextObj) {
        return storage.get(contextObj);
    }

    public Map<String, StateMachineContext<TaskStates, TaskEvents>> getStorage() {
        return storage;
    }
}

