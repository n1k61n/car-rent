package com.example.carrent.dtos.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
}
