package com.example.carrent.repositories;

import com.example.carrent.models.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {


    @Query("SELECT DISTINCT c.brand FROM Car c ORDER BY c.brand ASC")
    Set<String> findAllBrandsOrderByName();

    @Query("SELECT c FROM Car c WHERE " +
            "c.available = true AND " +
            "(:brand IS NULL OR LOWER(CAST(c.brand AS string)) LIKE LOWER(CONCAT('%', CAST(:brand AS string), '%'))) AND " +
            "(:pickupDate IS NULL OR :dropoffDate IS NULL OR NOT EXISTS (" +
            "    SELECT b FROM Booking b WHERE b.car = c AND " +
            "    b.status NOT IN (com.example.carrent.enums.BookingStatus.CANCELLED, com.example.carrent.enums.BookingStatus.REJECTED) AND " +
            "    (:pickupDate < b.endDate AND :dropoffDate > b.startDate)" +
            "))")
    Page<Car> findAvailableCarsPageable(
            @Param("brand") String brand,
            @Param("pickupDate") LocalDate pickupDate,
            @Param("dropoffDate") LocalDate dropoffDate,
            Pageable pageable);

    Page<Car> findByBrandContainingIgnoreCaseOrModelContainingIgnoreCase(String keyword, String keyword1, Pageable pageable);
}
