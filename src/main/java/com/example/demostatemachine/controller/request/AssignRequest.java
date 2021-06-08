package com.example.demostatemachine.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AssignRequest {

    @JsonProperty("warehouse_id")
    @NotBlank(message = "warehouse_id is mandatory")
    String warehouseId;

    @JsonProperty("user_id")
    @NotBlank(message = "user_id is mandatory")
    Long userId;
}
