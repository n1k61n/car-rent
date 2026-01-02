package com.example.carrent.dtos.blog;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogDto {

    private String title;

    private String content;

    private String imageUrl;

    private String author;

    private LocalDateTime createdAt;
}
