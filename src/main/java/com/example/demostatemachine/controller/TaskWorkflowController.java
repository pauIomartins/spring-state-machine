package com.example.demostatemachine.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.data.jpa.JpaRepositoryStateMachine;
import org.springframework.statemachine.data.jpa.JpaStateMachineRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RequiredArgsConstructor
@RestController
public class TaskWorkflowController {

    private final JpaStateMachineRepository jpaStateMachineRepository;

    @GetMapping("/workflow-storage")
    public List<JpaRepositoryStateMachine> getWorkflowStorage() {
        return StreamSupport.stream(jpaStateMachineRepository.findAll().spliterator(), false)
                        .collect(Collectors.toList());
    }

    @GetMapping("/hello")
    public String getHello() {
        return "hello state machine!";
    }
}
