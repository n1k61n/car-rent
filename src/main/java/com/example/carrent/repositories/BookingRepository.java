package com.example.carrent.repositories;

import com.example.carrent.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository  extends JpaRepository<Booking, Long> {
    @Query("SELECT (COUNT(b) > 0) FROM Booking b WHERE b.car.id = :carId " +
            "AND b.status <> com.example.carrent.enums.BookingStatus.CANCELLED " +
            "AND (:startDate < b.endDate AND :endDate > b.startDate)")
    boolean existsOverlapping(@Param("carId") Long carId,
                              @Param("startDate") LocalDate startDate,
                              @Param("endDate") LocalDate endDate);

    List<Booking> findAllByOrderByIdDesc();

    @Query("SELECT b FROM Booking b LEFT JOIN FETCH b.car LEFT JOIN FETCH b.user")
    List<Booking> findAllWithDetails();
}
