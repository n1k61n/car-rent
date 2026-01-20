package com.example.carrent.dtos.car;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CarUpdateDto {
    private Long id;
    private String brand;
    private String model;
    private String imageUrl;
    private BigDecimal dailyPrice;
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
    private Long categoryId;
}
