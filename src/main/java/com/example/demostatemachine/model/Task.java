package com.example.demostatemachine.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@Data
@Table(name = "task")
@Entity
public class Task {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "warehouse_id")
    private String warehouseId;

    @Column(name = "workflow_id")
    private String workflowId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TaskStatus status;

    public Task(final String warehouseId) {
        this.warehouseId = warehouseId;
        this.status = TaskStatus.PENDING;
    }

    public void assignTo(final long userId, final String warehouseId, final String workflowId) {
        this.warehouseId = warehouseId;
        this.userId = userId;
        this.status = TaskStatus.WORKING;
        this.workflowId = workflowId;
    }
}
