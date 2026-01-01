package com.example.carrent.dtos.testimonial;

import lombok.Data;

@Data
public class TestimonialDto {
    private Long id;

    private String authorName;

    private String content;

    private Integer rating;

    private String authorRole;

    private String imageUrl;
}
