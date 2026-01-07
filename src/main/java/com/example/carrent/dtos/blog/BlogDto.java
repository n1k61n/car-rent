package com.example.carrent.dtos.blog;

import com.example.carrent.dtos.car.CarBlogDto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogDto {
    private Long id;
    private String title;

    private String content;
    private String authorImageUrl;
    private String imageUrl;

    private String author;

    private LocalDateTime createdAt;
    private CarBlogDto car;
}
