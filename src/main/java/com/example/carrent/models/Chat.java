package com.example.carrent.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "chats")
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_name")
    private String from;
    private String email;

    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "receiver_name")
    private String to;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String sessionId;

    public Chat(String sessionId, String from) {
        this.sessionId = sessionId;
        this.from = from;
    }
}