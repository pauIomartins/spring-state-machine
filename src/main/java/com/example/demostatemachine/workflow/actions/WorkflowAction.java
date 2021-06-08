package com.example.demostatemachine.workflow.actions;

import com.example.demostatemachine.workflow.TaskEvents;
import com.example.demostatemachine.workflow.TaskStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;

@Slf4j
@Configuration
public class WorkflowAction {

    @Bean
    public Action<TaskStates, TaskEvents> executeAddressFromScanned() {
        return (context) -> {
            log.info("Doing address from scanned action. Event: " + context.getEvent());
        };
    }

    @Bean
    public Action<TaskStates, TaskEvents> executeInventoryScanned() {
        return (context) -> {
            log.info("Doing inventory scanned action. Event: " + context.getEvent());
        };
    }

    @Bean
    public Action<TaskStates, TaskEvents> executeAddressToScanned() {
        return (context) -> {
            log.info("Doing address to scanned action. Event: " + context.getEvent());
        };
    }
}
