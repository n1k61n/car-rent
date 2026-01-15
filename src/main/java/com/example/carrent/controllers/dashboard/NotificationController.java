package com.example.carrent.controllers.dashboard;

import com.example.carrent.models.Notification;
import com.example.carrent.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/dashboard/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/read/{id}")
    public RedirectView readNotification(@PathVariable Long id) {
        Notification notification = notificationService.markAsRead(id);
        if (notification != null) {
            return new RedirectView(notification.getLink());
        }
        return new RedirectView("/dashboard");
    }
}