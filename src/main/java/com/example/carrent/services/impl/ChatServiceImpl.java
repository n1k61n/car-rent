package com.example.carrent.services.impl;

import com.example.carrent.dtos.chat.ChatDto;
import com.example.carrent.models.Chat;
import com.example.carrent.repositories.ChatRepository;
import com.example.carrent.services.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    @Override
    public boolean saveChat(ChatDto chatDto) {
        if(chatDto == null) return false;
        Chat chat = mapToEntity(chatDto);
        chatRepository.save(chat);
        return true;
    }

    @Override
    public List<ChatDto> getHistoryBySessionId(String sessionId) {
        return chatRepository.findAllBySessionIdOrderByIdAsc(sessionId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatDto> getUniqueChatSessionsAfter(LocalDateTime date) {
        return chatRepository.findUniqueSessionsAfter(date)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ChatDto mapToDto(Chat chat) {
        return new ChatDto(
                chat.getId(),
                chat.getFrom(),
                chat.getTo(),
                chat.getEmail(),
                chat.getContent(),
                chat.getCreatedAt(),
                chat.getSessionId()
        );
    }

    private Chat mapToEntity(ChatDto dto) {
        Chat chat = new Chat();
        chat.setId(dto.getId());
        chat.setFrom(dto.getFrom());
        chat.setTo(dto.getTo());
        chat.setEmail(dto.getEmail());
        chat.setContent(dto.getContent());
        if (dto.getCreatedAt() != null) {
            chat.setCreatedAt(dto.getCreatedAt());
        } else {
            chat.setCreatedAt(LocalDateTime.now());
        }
        chat.setSessionId(dto.getSessionId());
        return chat;
    }
}