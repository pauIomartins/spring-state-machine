package com.example.demostatemachine.workflow.config;

import com.example.demostatemachine.workflow.TaskEvents;
import com.example.demostatemachine.workflow.TaskStates;
import com.example.demostatemachine.workflow.config.listener.StateMachineListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;

import java.util.EnumSet;

@RequiredArgsConstructor
@Slf4j
@Configuration
//@EnableStateMachine
@EnableStateMachineFactory
public class StateMachineConfiguration extends EnumStateMachineConfigurerAdapter<TaskStates, TaskEvents> {

    private final StateMachineListener stateMachineListener;
    private final StateMachineRuntimePersister<TaskStates, TaskEvents, String> stateMachineRuntimePersister;
    private final Action<TaskStates, TaskEvents> executeAddressFromScanned;
    private final Action<TaskStates, TaskEvents> executeInventoryScanned;
    private final Action<TaskStates, TaskEvents> executeAddressToScanned;

    @Override
    public void configure(final StateMachineConfigurationConfigurer<TaskStates, TaskEvents> config) throws Exception {

        config.withConfiguration()
                //.machineId("task-state-machine")
                .autoStartup(false)
                .listener(stateMachineListener);
        config.withPersistence()
                .runtimePersister(stateMachineRuntimePersister);
    }

    @Override
    public void configure(final StateMachineStateConfigurer<TaskStates, TaskEvents> states) throws Exception {
        states.withStates()
                .initial(TaskStates.GET_ADDRESS_FROM)
                .end(TaskStates.DONE)
                .states(EnumSet.allOf(TaskStates.class));
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<TaskStates, TaskEvents> transitions) throws Exception {
        transitions.withExternal()
                .source(TaskStates.GET_ADDRESS_FROM)
                .target(TaskStates.GET_INVENTORY)
                .event(TaskEvents.ADDRESS_FROM_SCANNED)
                .action(executeAddressFromScanned)

                .and()
                .withExternal()
                .source(TaskStates.GET_INVENTORY)
                .target(TaskStates.GET_ADDRESS_TO)
                .event(TaskEvents.INVENTORY_SCANNED)
                .action(executeInventoryScanned)

                .and()
                .withExternal()
                .source(TaskStates.GET_ADDRESS_TO)
                .target(TaskStates.DONE)
                .event(TaskEvents.ADDRESS_TO_SCANNED)
                .action(executeAddressToScanned);
    }
}
