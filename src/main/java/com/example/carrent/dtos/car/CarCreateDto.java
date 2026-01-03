package com.example.carrent.dtos.car;

import lombok.Data;

import java.util.List;

@Data
public class CarCreateDto {
    private String brand;
    private String model;
    private String imageUrl;
    private Double dailyPrice;
    private Integer doorCount;
    private Integer passengerCount;
    private Integer luggageCapacity;
    private String transmission;
    private String fuelType;
    private List<String> features;
    private Integer year;
    private String suitcases;
    private String fuelPolicy;
    private String mileage;


    private Boolean roadAssistance ;
    private Boolean insuranceIncluded ;
    private Boolean freeCancellation;
}
