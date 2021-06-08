package com.example.demostatemachine.workflow.config;

import com.example.demostatemachine.workflow.TaskEvents;
import com.example.demostatemachine.workflow.TaskStates;
import com.example.demostatemachine.workflow.config.persistence.InMemoryPersist;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.data.jpa.JpaPersistingStateMachineInterceptor;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.persist.StateMachineRuntimePersister;

@Configuration
public class StateMachinePersisterConfiguration {

    @Profile("test")
    @Bean
    public StateMachinePersist<TaskStates, TaskEvents, String> inMemoryPersistence() {
        return new InMemoryPersist();
    }

    @Profile("test")
    @Bean
    public StateMachinePersister<TaskStates, TaskEvents, String> stateMachinePersister(
            final StateMachinePersist<TaskStates, TaskEvents, String> defaultPersist) {

        return new DefaultStateMachinePersister<>(defaultPersist);
    }

    @Profile("!test")
    @Bean
    public StateMachineRuntimePersister<TaskStates, TaskEvents, String> stateMachineRuntimePersister(
            final JpaStateMachineRepository jpaStateMachineRepository) {
        return new JpaPersistingStateMachineInterceptor<TaskStates, TaskEvents, String>(jpaStateMachineRepository);
    }
}
