package com.example.demostatemachine.workflow.config;

import com.example.demostatemachine.workflow.TaskEvents;
import com.example.demostatemachine.workflow.TaskStates;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;
import org.springframework.statemachine.service.DefaultStateMachineService;
import org.springframework.statemachine.service.StateMachineService;

@RequiredArgsConstructor
@Configuration
public class StateMachineServiceConfiguration {

    private final StateMachineFactory<TaskStates, TaskEvents> stateMachineFactory;
    private final StateMachineRuntimePersister<TaskStates, TaskEvents, String> stateMachineRuntimePersister;

    @Bean
    public StateMachineService<TaskStates, TaskEvents> stateMachineService() {
        return new DefaultStateMachineService<TaskStates, TaskEvents>(stateMachineFactory, stateMachineRuntimePersister);
    }
}
