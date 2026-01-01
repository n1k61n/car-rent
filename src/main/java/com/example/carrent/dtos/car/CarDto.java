package com.example.carrent.dtos.car;

import lombok.Data;

import java.util.List;

@Data
public class CarDto {
    private Long id;
    private String imageUrl;
    private String brand;
    private String model;
    private Double dailyPrice;
    private Integer doorCount;
    private Integer passengerCount;
    private Integer luggageCapacity;
    private String description;
    private String transmission;
    private String fuelType;
    private List<String> features;

}
