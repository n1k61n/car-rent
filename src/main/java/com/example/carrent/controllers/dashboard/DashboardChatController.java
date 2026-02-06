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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

    private final Map<String, Long> recentFallbacks = new ConcurrentHashMap<>();
    private static final long FALLBACK_DEDUP_WINDOW_MS = 10000; // 10 saniyə


    @GetMapping
    public String index(Model model) {
        model.addAttribute("users", userService.getRecentUsers());
        return "dashboard/chat/index";
    }



    @MessageMapping("/chat.sendMessage")
    public void sendMessage(@Payload ChatDto chat) {

        if (chat.getEmail() == null) chat.setEmail(chat.getFrom());
        if (chat.getSessionId() == null) chat.setSessionId(chat.getFrom());

        boolean isToSystem = "ADMIN".equals(chat.getTo()) || "AI".equals(chat.getTo());
        boolean isExplicitAi = "AI".equals(chat.getTo());
        boolean isAdminOnline = adminPresenceHandler.isAdminOnline();

        // 1) AI ROUTE yalnız system mesajları üçün
        if (isToSystem && (isExplicitAi || !isAdminOnline)) {

            // user mesajını yaz
            chatService.saveChat(chat);

            ChatDto aiReply = null;
            try {
                aiReply = chatService.generateAiReply(chat);
            } catch (Exception ignored) {}

            boolean aiFailed =
                    aiReply == null ||
                            aiReply.getContent() == null ||
                            aiReply.getContent().trim().isEmpty() ||
                            aiReply.getContent().contains("Hazırda sistem çox yüklüdür") ||
                            aiReply.getContent().toLowerCase().contains("quota") ||
                            aiReply.getContent().toLowerCase().contains("xəta") ||
                            aiReply.getContent().toLowerCase().contains("error");

            // AI normal cavab
            if (!aiFailed) {
                if (aiReply.getTo() == null) aiReply.setTo(chat.getFrom());
                chatService.saveChat(aiReply);
                messagingTemplate.convertAndSend("/topic/user/" + chat.getFrom(), aiReply);
                return;
            }

            // 2) AI FAIL -> adminə yönləndir (amma user-ə fallback 1 dəfə getsin)
            messagingTemplate.convertAndSend("/topic/admin", chat);

            String dedupKey = chat.getSessionId(); // session üzrə 1 dəfə
            long now = System.currentTimeMillis();
            Long last = recentFallbacks.get(dedupKey);
            if (last != null && (now - last) < FALLBACK_DEDUP_WINDOW_MS) {
                return; // adminə getdi, fallback təkrarlanmasın
            }
            recentFallbacks.put(dedupKey, now);

            ChatDto fallback = new ChatDto();
            fallback.setFrom("SYSTEM");
            fallback.setTo(chat.getFrom());
            fallback.setEmail(chat.getEmail());
            fallback.setSessionId(chat.getSessionId());
            fallback.setCreatedAt(LocalDateTime.now());
            fallback.setContent("Hazırda AI cavab verə bilmir. Mesajınız adminə yönləndirildi ✅");

            chatService.saveChat(fallback);
            messagingTemplate.convertAndSend("/topic/user/" + chat.getFrom(), fallback);

            String link = "/dashboard/chat?user=" + chat.getSessionId();
            Notification notification = notificationService.createNotification(
                    "AI failed - Message from " + chat.getFrom(), link, "CHAT"
            );
            messagingTemplate.convertAndSend("/topic/notifications", notification);

            return;
        }

        // 3) ADMIN ROUTE
        chatService.saveChat(chat);

        if ("ADMIN".equals(chat.getTo())) {
            messagingTemplate.convertAndSend("/topic/admin", chat);

            String link = "/dashboard/chat?user=" + chat.getSessionId();
            Notification notification = notificationService.createNotification(
                    "New message from " + chat.getFrom(), link, "CHAT"
            );
            messagingTemplate.convertAndSend("/topic/notifications", notification);
            return;
        }

        // Admin -> User
        String userTopicId = chat.getTo();
        if (userTopicId != null && userTopicId.startsWith("user_")) {
            userTopicId = userTopicId.substring("user_".length());
        }
        messagingTemplate.convertAndSend("/topic/user/" + userTopicId, chat);
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