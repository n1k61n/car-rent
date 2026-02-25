package com.example.carrent.dtos.car;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CarListingDto {
    private Long id;
    private String brand;
    private String model;
    private String imageUrl;
    @NotNull(message = "Qiymət boş ola bilməz")
    @Positive(message = "Günlük qiymət 0-dan böyük olmalıdır")
    private Double dailyPrice;
    private Integer doorCount;
    private Integer passengerCount;
    private Integer luggageCapacity;

    private String description;
    private Boolean available;
}
