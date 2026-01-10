package com.example.carrent.dtos.car;

import lombok.Data;

@Data
public class CarListingDto {
    private Long id;
    private String brand;
    private String model;
    private String imageUrl;
    private Double dailyPrice;
    private Integer doorCount;
    private Integer passengerCount;
    private Integer luggageCapacity;

    private String description;
    private Boolean available;
}
