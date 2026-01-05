package com.example.carrent.dtos.blog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogDahboardUpdateDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDate createdAt;
    private String categoryName;
    private String authorImageUrl;
    private String post;
}
