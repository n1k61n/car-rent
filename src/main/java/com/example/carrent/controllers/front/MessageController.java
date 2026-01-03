package com.example.carrent.controllers.front;

import com.example.carrent.dtos.message.MessageDto;
import com.example.carrent.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @PostMapping("/contact")
    public String messageCreate(MessageDto messageDto){
        boolean result = messageService.createUserMessage(messageDto);
        return "front/contact";
    }


}
