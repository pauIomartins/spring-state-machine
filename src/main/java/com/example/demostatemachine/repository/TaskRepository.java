package com.example.demostatemachine.repository;

import com.example.demostatemachine.model.Task;
import com.example.demostatemachine.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByWarehouseIdAndStatusAndUserIdIsNull(String warehouseId, TaskStatus status);

    List<Task> findByWarehouseIdAndStatusAndUserId(String warehouseId, TaskStatus status, long userId);
}
