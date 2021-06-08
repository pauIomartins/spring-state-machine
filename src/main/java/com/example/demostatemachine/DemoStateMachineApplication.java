package com.example.demostatemachine;

import com.example.demostatemachine.model.Task;
import com.example.demostatemachine.repository.TaskRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.data.jpa.JpaRepositoryState;
import org.springframework.statemachine.data.jpa.JpaStateRepository;

@EntityScan(basePackageClasses = {JpaRepositoryState.class, Task.class})
@EnableJpaRepositories(basePackageClasses = {JpaStateRepository.class, TaskRepository.class})
@SpringBootApplication
@EnableStateMachine
public class DemoStateMachineApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoStateMachineApplication.class, args);
    }

}
