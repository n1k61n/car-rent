package com.example.carrent.controllers.front;

import com.example.carrent.models.Chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;



@Controller
public class ChatController {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload Chat chat) {
        System.out.println("Mesaj alındı: " + chat.getContent());

        messagingTemplate.convertAndSend("/topic/admin", chat);

        // Əgər mesaj admin tərəfindən müəyyən istifadəçiyə göndərilibsə
        if (chat.getTo() != null && !chat.getTo().equals("ADMIN")) {
            messagingTemplate.convertAndSend("/topic/user/" + chat.getTo(), chat);
        }
    }
}