package com.example.carrent.dtos.car;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CarListingDto {
    private Long id;
    private String brand;
    private String model;
    private String imageUrl;
    @NotNull(message = "Daily price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Daily price must be greater than 0")
    private Double dailyPrice;
    private Integer doorCount;
    private Integer passengerCount;
    private Integer luggageCapacity;

    private String description;
    private Boolean available;
}
