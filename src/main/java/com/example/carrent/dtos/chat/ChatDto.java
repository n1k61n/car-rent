package com.example.carrent.dtos.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatDto {
    private Long id;
    private String from;
    private String to;
    private String email;
    private String content;

    private LocalDateTime createdAt;
    private String sessionId;

    // AI tövsiyələri üçün yeni sahə
    private List<Long> recommendedCarIds;
}
