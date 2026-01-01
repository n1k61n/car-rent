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

//    @Query("SELECT c FROM Car c WHERE " +
//            "(:brand IS NULL OR c.brand = :brand) AND " +
//            "NOT EXISTS (SELECT r FROM Rental r WHERE r.car = c AND " +
//            "(:pickupDate < r.dropoffDate AND :dropoffDate > r.pickupDate))")
//    List<Car> findAvailableCars(@Param("brand") String brand,
//                                @Param("pickupDate") LocalDate pickupDate,
//                                @Param("dropoffDate") LocalDate dropoffDate);

    @Query("SELECT c FROM Car c WHERE (:brand IS NULL OR c.brand = :brand) " +
            "AND c.id NOT IN (SELECT r.car.id FROM Rental r WHERE " +
            "(:pickupDate < r.dropoffDate AND :dropoffDate > r.pickupDate))")
    List<Car> findAvailableCars(String brand, LocalDate pickupDate, LocalDate dropoffDate);

    // Bütün maşınların markalarını unikal (təkrarlanmadan) gətirir
    @Query("SELECT DISTINCT c.brand FROM Car c ORDER BY c.brand ASC")
    Set<String> findAllBrandsOrderByName();

    @Query("SELECT c FROM Car c WHERE " +
            "(:brand IS NULL OR c.brand = :brand) AND " +
            "(:pickupDate IS NULL OR :dropoffDate IS NULL OR NOT EXISTS (" +
            "    SELECT r FROM Rental r WHERE r.car = c AND " +
            "    (:pickupDate < r.dropoffDate AND :dropoffDate > r.pickupDate)" +
            "))")
    Page<Car> findAvailableCarsPageable(
            @Param("brand") String brand,
            @Param("pickupDate") LocalDate pickupDate,
            @Param("dropoffDate") LocalDate dropoffDate,
            Pageable pageable);
}


