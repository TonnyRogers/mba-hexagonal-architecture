package com.example.demo.infrastructure.dtos;

public record NewEventDTO(
        String name,
        String date,
        Integer totalSpots,
        Long partnerId) {

}
