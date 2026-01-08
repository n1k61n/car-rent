package com.example.carrent.services.impl;

import com.example.carrent.dtos.message.MessageDto;
import com.example.carrent.models.Message;
import com.example.carrent.models.User;
import com.example.carrent.repositories.MessageRepository;
import com.example.carrent.repositories.UserRepository;
import com.example.carrent.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public boolean createUserMessage(MessageDto messageDto) {
        if(messageDto == null) return false;

        Message message = modelMapper.map(messageDto, Message.class);
        message.setCreatedAt(LocalDateTime.now());

        userRepository.findByEmail(messageDto.getEmail()).ifPresent(user -> message.setUser(user));

        messageRepository.save(message);
        return true;
    }
}
