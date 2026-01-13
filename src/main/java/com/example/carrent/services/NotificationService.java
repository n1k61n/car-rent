package com.example.carrent.services;

import com.example.carrent.models.Notification;

import java.util.List;

public interface NotificationService {
    void createNotification(String message, String link, String type);
    List<Notification> getUnreadNotifications();
    int getUnreadNotificationCount();
    Notification markAsRead(Long id);
}
