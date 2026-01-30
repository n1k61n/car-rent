package com.example.carrent.services.impl;

import com.example.carrent.models.Notification;
import com.example.carrent.repositories.NotificationRepository;
import com.example.carrent.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public Notification createNotification(String message, String link, String type) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setLink(link);
        notification.setType(type);
        notification.setRead(false);
        Notification savedNotification = notificationRepository.save(notification);

        // Send notification to WebSocket topic
        messagingTemplate.convertAndSend("/topic/notifications", savedNotification);

        return savedNotification;
    }

    @Override
    public List<Notification> getUnreadNotifications() {
        return notificationRepository.findTop5ByIsReadFalseOrderByCreatedAtDesc();
    }

    @Override
    public int getUnreadNotificationCount() {
        return notificationRepository.countByIsReadFalse();
    }

    @Override
    @Transactional
    public Notification markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        return notificationRepository.save(notification);
    }
}
