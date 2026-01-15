package com.example.carrent.repositories;

import com.example.carrent.models.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findAllBySessionIdOrderByIdAsc(String sessionId);

    @Query("SELECT DISTINCT new com.example.carrent.models.Chat(c.sessionId, c.from) FROM Chat c WHERE c.sessionId IS NOT NULL AND c.from <> 'ADMIN'")
    List<Chat> findUniqueSessions();

    @Query("SELECT DISTINCT new com.example.carrent.models.Chat(c.sessionId, c.from) FROM Chat c WHERE c.sessionId IS NOT NULL AND c.from <> 'ADMIN' AND c.createdAt > :date")
    List<Chat> findUniqueSessionsAfter(@Param("date") LocalDateTime date);

    List<Chat> findAllByEmailOrderByIdAsc(String email);

    // Yalnız mesajı olan unikal email-ləri gətirmək üçün
    @Query("SELECT DISTINCT c.email FROM Chat c WHERE c.email IS NOT NULL")
    List<String> findUniqueActiveEmails();
}