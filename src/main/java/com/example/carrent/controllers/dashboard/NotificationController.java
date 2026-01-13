package com.example.carrent.controllers.dashboard;

import com.example.carrent.models.Notification;
import com.example.carrent.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/read/{id}")
    public String markAsReadAndRedirect(@PathVariable Long id) {
        Notification notification = notificationService.markAsRead(id);
        return "redirect:" + notification.getLink();
    }
}
