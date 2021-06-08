package com.example.demostatemachine.workflow;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.service.StateMachineService;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class WorkflowTest {

    @Autowired
    private StateMachineService<TaskStates, TaskEvents> stateMachineService;

    @Test
    public void shouldCompleteWorkflow() {
        // given
        final StateMachine<TaskStates, TaskEvents> givenStateMachine = stateMachineService.acquireStateMachine("test");

        // when
        givenStateMachine.sendEvent(TaskEvents.ADDRESS_FROM_SCANNED);
        givenStateMachine.sendEvent(TaskEvents.INVENTORY_SCANNED);
        givenStateMachine.sendEvent(TaskEvents.ADDRESS_TO_SCANNED);

        // then
        Assertions.assertThat(givenStateMachine.getState().getId()).isEqualTo(TaskStates.DONE);
    }

    @Test
    public void shouldNotAcceptWrongEvent() {
        // given
        final StateMachine<TaskStates, TaskEvents> givenStateMachine = stateMachineService.acquireStateMachine("test1");

        // when
        givenStateMachine.sendEvent(TaskEvents.ADDRESS_FROM_SCANNED);
        givenStateMachine.sendEvent(TaskEvents.ADDRESS_TO_SCANNED); // should ignore

        // then
        Assertions.assertThat(givenStateMachine.getState().getId()).isEqualTo(TaskStates.GET_INVENTORY);
    }
}
