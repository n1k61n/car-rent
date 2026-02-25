package com.example.carrent.dtos.car;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CarUpdateDto {
    private Long id;
    private String brand;
    private String model;
    private String imageUrl;
    @NotNull(message = "Daily price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Daily price must be greater than 0")
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
    private Boolean available;
}
