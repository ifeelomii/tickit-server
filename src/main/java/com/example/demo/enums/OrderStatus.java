package com.example.demo.enums;

import java.util.Set;

public enum OrderStatus {
    CREATED,
    DRIVER_ASSIGNED,
    PICKED_UP,
    IN_TRANSIT,
    DELIVERED,
    CANCELLED;
    
    public boolean canTransitionTo(OrderStatus nextStatus) {

        return switch (this) {
            case CREATED -> Set.of(DRIVER_ASSIGNED, CANCELLED).contains(nextStatus);

            case DRIVER_ASSIGNED -> Set.of(PICKED_UP, CANCELLED).contains(nextStatus);

            case PICKED_UP -> Set.of(IN_TRANSIT).contains(nextStatus);

            case IN_TRANSIT -> Set.of(DELIVERED).contains(nextStatus);

            case DELIVERED, CANCELLED -> false;
        };
    }
}