package com.example.carrent.services;

import com.example.carrent.dtos.message.MessageDto;

public interface MessageService {
    boolean createUserMessage(MessageDto messageDto);
}
