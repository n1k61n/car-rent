package com.example.carrent.repositories;

import com.example.carrent.enums.BookingStatus;
import com.example.carrent.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository  extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b LEFT JOIN FETCH b.car LEFT JOIN FETCH b.user")
    List<Booking> findAllWithDetails();


    long countByStatus(BookingStatus status);

    void deleteByCarId(Long id);

    List<Booking> findTop5ByOrderByCreatedAtDesc();

    @Query("""
        SELECT COALESCE(SUM(b.totalPrice), 0)
        FROM Booking b
        WHERE b.status = 'APPROVED'
          AND MONTH(b.createdAt) = MONTH(CURRENT_DATE)
          AND YEAR(b.createdAt) = YEAR(CURRENT_DATE)
    """)
    double getMonthEarnings();
}
