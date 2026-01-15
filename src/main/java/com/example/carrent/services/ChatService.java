package com.example.carrent.services;

import com.example.carrent.models.Chat;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatService {
    boolean saveChat(Chat chat);
    List<Chat> getHistoryBySessionId(String sessionId);
    List<Chat> getUniqueChatSessionsAfter(LocalDateTime date);
}