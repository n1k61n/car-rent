package com.example.carrent.dtos.testimonial;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TestimonialDto {
    private Long id;
    private String authorName;
    private String authorRole;
    private String content;
    private Integer rating;
    private String imageUrl;
    private LocalDate createdAt;
}
