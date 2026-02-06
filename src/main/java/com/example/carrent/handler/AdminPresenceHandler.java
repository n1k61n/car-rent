package com.example.carrent.handler;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AdminPresenceHandler {

    // Stores Session IDs of clients subscribed to /topic/admin
    private final Set<String> activeAdminSessions = Collections.newSetFromMap(new ConcurrentHashMap<>());

    @EventListener
    public void handleSubscribe(SessionSubscribeEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String destination = headerAccessor.getDestination();
        String sessionId = headerAccessor.getSessionId();

        // If someone subscribes to the admin topic, mark them as an active admin
        if ("/topic/admin".equals(destination) && sessionId != null) {
            activeAdminSessions.add(sessionId);
        }
    }

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = headerAccessor.getSessionId();

        if (sessionId != null) {
            activeAdminSessions.remove(sessionId);
        }
    }

    public boolean isAdminOnline() {
        return !activeAdminSessions.isEmpty();
    }
}
