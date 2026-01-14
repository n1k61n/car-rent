package com.example.carrent.controllers.front;

import com.example.carrent.models.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(ChatMessage chatMessage) {
        log.info("Received chat message: From = {}, Text = {}", chatMessage.getFrom(), chatMessage.getText());
        return chatMessage;
    }
}
