package com.example.carrent.dtos.blog;

import com.example.carrent.dtos.comment.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogSingleDto {
    private Long id;
    private String title;
    private LocalDate createdAt;
    private String author;
    private String content;
    private String post;
    private String imageUrl;
    private String categoryName;

    private String authorImageUrl;
    private String authorBio;
    private String authorDescription;
    private List<CommentDto> comments ;



}



