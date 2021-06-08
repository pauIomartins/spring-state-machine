package com.example.demostatemachine.workflow.config.listener;

import com.example.demostatemachine.workflow.TaskEvents;
import com.example.demostatemachine.workflow.TaskStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Slf4j
@Component
public class StateMachineListener extends StateMachineListenerAdapter<TaskStates, TaskEvents> {

    private String getStateInfo(final State<TaskStates, TaskEvents> state) {
        return state == null ? "none" : state.getId().toString().toLowerCase(Locale.ROOT);
    }

    @Override
    public void stateChanged(final State<TaskStates, TaskEvents> from, final State<TaskStates, TaskEvents> to) {
        log.info("State changed from {} to {}", getStateInfo(from), getStateInfo(to));
    }

    @Override
    public void stateEntered(final State<TaskStates, TaskEvents> state) {
        log.info("Entered state {}", getStateInfo(state));
    }

    @Override
    public void stateExited(final State<TaskStates, TaskEvents> state) {
        log.info("Exited state {}", getStateInfo(state));
    }

    @Override
    public void eventNotAccepted(final Message<TaskEvents> event) {
        log.error("Event not accepted: {}", event.getPayload());
    }

    @Override
    public void transition(final Transition<TaskStates, TaskEvents> transition) {
        // 
    }

    @Override
    public void transitionStarted(final Transition<TaskStates, TaskEvents> transition) {
        // 
    }

    @Override
    public void transitionEnded(final Transition<TaskStates, TaskEvents> transition) {
        // 
    }

    @Override
    public void stateMachineStarted(final StateMachine<TaskStates, TaskEvents> stateMachine) {
        log.info("Machine started: {}", stateMachine);
    }

    @Override
    public void stateMachineStopped(final StateMachine<TaskStates, TaskEvents> stateMachine) {
        log.info("Machine stopped: {}", stateMachine);
    }

    @Override
    public void stateMachineError(final StateMachine<TaskStates, TaskEvents> stateMachine,
                                  final Exception exception) {
        log.error("Machine error: {}", stateMachine);
    }

    @Override
    public void extendedStateChanged(final Object key, final Object value) {
        log.info("Extended state changed: [{}: {}]", key, value);
    }

    @Override
    public void stateContext(final StateContext<TaskStates, TaskEvents> stateContext) {
        // 
    }
}
