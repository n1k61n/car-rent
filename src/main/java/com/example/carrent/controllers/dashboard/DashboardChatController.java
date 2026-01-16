package com.example.carrent.controllers.dashboard;

import com.example.carrent.dtos.chat.ChatDto;
import com.example.carrent.models.Notification;
import com.example.carrent.services.ChatService;
import com.example.carrent.services.NotificationService;
import com.example.carrent.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/dashboard/chat")
@RequiredArgsConstructor
public class DashboardChatController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private final UserService userService;
    private final ChatService chatService;
    private final NotificationService notificationService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("users", userService.getRecentUsers());
        return "dashboard/chat/index";
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatDto chat) {
        System.out.println("Gələn: " + chat.getContent() + " | Kimdən: " + chat.getFrom() + " | ID: " + chat.getSessionId());

        if (chat.getEmail() == null) {
            chat.setEmail(chat.getFrom());
        }
        if ("ADMIN".equals(chat.getTo()) && chat.getSessionId() == null) {
            chat.setSessionId(chat.getFrom());
        }

        chatService.saveChat(chat);

        if ("ADMIN".equals(chat.getTo())) {
            messagingTemplate.convertAndSend("/topic/admin", chat);
            String link = "/dashboard/chat?user=" + chat.getSessionId();
            Notification notification = notificationService.createNotification("New message from " + chat.getFrom(), link, "CHAT");
            messagingTemplate.convertAndSend("/topic/notifications", notification);

        } else {
            messagingTemplate.convertAndSend("/topic/user/" + chat.getTo(), chat);
        }
    }

    @GetMapping("/history/{id}")
    @ResponseBody
    public List<ChatDto> getChatHistory(@PathVariable String id) {
        return chatService.getHistoryBySessionId(id);
    }

    @GetMapping("/active-sessions")
    @ResponseBody
    public List<ChatDto> getActiveSessions() {
        return chatService.getUniqueChatSessionsAfter(LocalDateTime.now().minusHours(24));
    }
}