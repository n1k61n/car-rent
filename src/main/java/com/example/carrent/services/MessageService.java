package com.example.carrent.services;

import com.example.carrent.dtos.message.MessageDto;

import java.util.List;

public interface MessageService {
    boolean createUserMessage(MessageDto messageDto);
    List<MessageDto> getRecentMessages();
}
