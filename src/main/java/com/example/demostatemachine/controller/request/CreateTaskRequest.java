package com.example.demostatemachine.controller.request;

import com.example.demostatemachine.model.Task;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateTaskRequest {

    @JsonProperty("warehouse_id")
    @NotBlank(message = "warehouse_id is mandatory")
    String warehouseId;

    public Task toTask() {
        return new Task(this.warehouseId);
    }
}
