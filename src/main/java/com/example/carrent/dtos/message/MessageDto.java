package com.example.carrent.dtos.message;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDto {
    private Long id;
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String message;
    private LocalDateTime createdAt;
}
