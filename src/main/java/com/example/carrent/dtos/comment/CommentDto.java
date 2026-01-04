package com.example.carrent.dtos.comment;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private String name;
    private String email;
    private String message;
    private Long blogId;
    private LocalDateTime createdAt;

}
