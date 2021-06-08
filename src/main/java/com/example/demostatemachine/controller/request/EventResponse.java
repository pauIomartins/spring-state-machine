package com.example.demostatemachine.controller.request;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class EventResponse {
    String id;
    String uuid;
    String currentState;
    String allowedEvents;
}
