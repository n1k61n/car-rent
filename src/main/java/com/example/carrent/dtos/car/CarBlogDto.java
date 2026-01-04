package com.example.carrent.dtos.car;

import lombok.Data;

@Data
public class CarBlogDto {
    private Long id;
    private String brand;
    private String model;
    private String imageUrl;
}
