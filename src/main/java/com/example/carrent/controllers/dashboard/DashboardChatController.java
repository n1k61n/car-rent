package com.example.carrent.controllers.dashboard;

import com.example.carrent.dtos.chat.ChatDto;
import com.example.carrent.handler.AdminPresenceHandler;
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
    private final AdminPresenceHandler adminPresenceHandler;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("users", userService.getRecentUsers());
        return "dashboard/chat/index";
    }

    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatDto chat) {

        if (chat.getEmail() == null) chat.setEmail(chat.getFrom());
        if ("ADMIN".equals(chat.getTo()) && chat.getSessionId() == null) chat.setSessionId(chat.getFrom());
        if ("AI".equals(chat.getTo()) && chat.getSessionId() == null) chat.setSessionId(chat.getFrom());

        // 1. Check if it's the initial hidden trigger "Salam"
        boolean isAiTrigger = "Salam".equalsIgnoreCase(chat.getContent());

        // 2. Save User Message (unless it's the hidden trigger)
        if (!isAiTrigger) {
            chatService.saveChat(chat);
        }

        // 3. Determine Routing Logic
        boolean isAdminOnline = adminPresenceHandler.isAdminOnline();
        boolean isExplicitlyForAi = "AI".equals(chat.getTo());

        // LOGIC:
        // - If explicit AI request OR Trigger -> AI
        // - If Admin is OFFLINE -> AI
        // - If Admin is ONLINE -> Admin

        if (isExplicitlyForAi || isAiTrigger || !isAdminOnline) {
            // --- AI HANDLES IT ---
            ChatDto aiReply = chatService.generateAiReply(chat);
            
            // Check if AI failed (quota exceeded)
            if (aiReply.getContent() != null && aiReply.getContent().contains("Hazırda sistem çox yüklüdür")) {
                // Send the apology to the user
                chatService.saveChat(aiReply);
                messagingTemplate.convertAndSend("/topic/user/" + chat.getFrom(), aiReply);

                // ALSO forward the original user message to ADMIN so they can take over
                messagingTemplate.convertAndSend("/topic/admin", chat);
                String link = "/dashboard/chat?user=" + chat.getSessionId();
                Notification notification = notificationService.createNotification(
                        "AI Quota Exceeded - Message from " + chat.getFrom(), link, "CHAT"
                );
                messagingTemplate.convertAndSend("/topic/notifications", notification);
            } else {
                // Normal AI success
                chatService.saveChat(aiReply);
                messagingTemplate.convertAndSend("/topic/user/" + chat.getFrom(), aiReply);
            }
        } else {
            // --- ADMIN HANDLES IT ---
            if ("ADMIN".equals(chat.getTo())) {
                messagingTemplate.convertAndSend("/topic/admin", chat);
                String link = "/dashboard/chat?user=" + chat.getSessionId();
                Notification notification = notificationService.createNotification(
                        "New message from " + chat.getFrom(), link, "CHAT"
                );
                messagingTemplate.convertAndSend("/topic/notifications", notification);
            } else {
                // Admin replying to User
                messagingTemplate.convertAndSend("/topic/user/" + chat.getTo(), chat);
            }
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