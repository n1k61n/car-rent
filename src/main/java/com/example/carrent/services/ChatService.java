package com.example.carrent.services;

import com.example.carrent.dtos.chat.ChatDto;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatService {
    boolean saveChat(ChatDto chatDto);
    List<ChatDto> getHistoryBySessionId(String sessionId);
    List<ChatDto> getUniqueChatSessionsAfter(LocalDateTime date);
}