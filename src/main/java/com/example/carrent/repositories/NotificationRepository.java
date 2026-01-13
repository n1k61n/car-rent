package com.example.carrent.repositories;

import com.example.carrent.models.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findTop5ByIsReadFalseOrderByCreatedAtDesc();
    int countByIsReadFalse();
}
