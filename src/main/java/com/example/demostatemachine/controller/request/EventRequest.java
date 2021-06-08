package com.example.demostatemachine.controller.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Data
public class EventRequest {

    @JsonProperty("warehouse_id")
    @NotBlank(message = "warehouse_id is mandatory")
    String warehouseId;

    @JsonProperty("user_id")
    @NotBlank(message = "user_id is mandatory")
    Long userId;

    @JsonProperty("name")
    @NotBlank(message = "name is mandatory")
    String name;
    
    Map<String, String> data;
}
