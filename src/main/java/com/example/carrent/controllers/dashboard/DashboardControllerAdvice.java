package com.example.carrent.controllers.dashboard;

import com.example.carrent.dtos.message.MessageDto;
import com.example.carrent.models.Notification;
import com.example.carrent.services.MessageService;
import com.example.carrent.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice(basePackages = "com.example.carrent.controllers.dashboard")
@RequiredArgsConstructor
public class DashboardControllerAdvice {

    private final NotificationService notificationService;
    private final MessageService messageService;

    @ModelAttribute("unreadNotifications")
    public List<Notification> getUnreadNotifications() {
        return notificationService.getUnreadNotifications();
    }

    @ModelAttribute("notificationCount")
    public int getNotificationCount() {
        return notificationService.getUnreadNotificationCount();
    }

    @ModelAttribute("messages")
    public List<MessageDto> getRecentMessages() {
        return messageService.getRecentMessages();
    }
}
