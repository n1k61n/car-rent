package com.example.carrent.repositories;

import com.example.carrent.enums.BookingStatus;
import com.example.carrent.models.Booking;
import com.example.carrent.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BookingRepository  extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b LEFT JOIN FETCH b.car LEFT JOIN FETCH b.user")
    List<Booking> findAllWithDetails();


    long countByStatus(BookingStatus status);

    void deleteByCarId(Long id);

    List<Booking> findTop5ByOrderByCreatedAtDesc();

    @Query("SELECT b FROM Booking b JOIN FETCH b.user JOIN FETCH b.car WHERE b.user = :user")
    List<Booking> findByUser(@Param("user") User user);

    long countByUser(User user);

    long countByUserAndStatus(User user, BookingStatus bookingStatus);

    @Query("""
        SELECT COALESCE(SUM(b.totalPrice), 0.0)
        FROM Booking b
        WHERE b.user = :user
    """)
    BigDecimal sumTotalPriceByUser(@Param("user") User user);

    @Query("SELECT COALESCE(SUM(b.totalPrice), 0.0) FROM Booking b WHERE EXTRACT(YEAR FROM b.createdAt) = EXTRACT(YEAR FROM CURRENT_DATE) AND EXTRACT(MONTH FROM b.createdAt) = EXTRACT(MONTH FROM CURRENT_DATE)")
    double getMonthEarnings();

    @Query("SELECT EXTRACT(MONTH FROM b.createdAt), SUM(b.totalPrice) FROM Booking b WHERE EXTRACT(YEAR FROM b.createdAt) = EXTRACT(YEAR FROM CURRENT_DATE) GROUP BY EXTRACT(MONTH FROM b.createdAt)")
    List<Object[]> getMonthlyEarnings();

    @Query("SELECT b.status, COUNT(b) FROM Booking b GROUP BY b.status")
    List<Object[]> getBookingStatusDistribution();

    @Query("""
    SELECT b FROM Booking b
    JOIN FETCH b.user
    WHERE b.startDate <= CURRENT_DATE
    AND b.endDate >= CURRENT_DATE
""")
    List<Booking> findAllActiveBookings();

}
