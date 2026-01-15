package com.example.carrent.services.impl;

import com.example.carrent.models.Chat;
import com.example.carrent.repositories.ChatRepository;
import com.example.carrent.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    @Override
    public boolean saveChat(Chat chat) {
        if(chat == null) return false;
        chatRepository.save(chat);
        return true;
    }

    @Override
    public List<Chat> getHistoryBySessionId(String sessionId) {
        return chatRepository.findAllBySessionIdOrderByIdAsc(sessionId);
    }

    @Override
    public List<Chat> getUniqueChatSessionsAfter(LocalDateTime date) {
        return chatRepository.findUniqueSessionsAfter(date);
    }

}