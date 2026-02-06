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


    @Query("SELECT DISTINCT c.brand FROM Car c WHERE c.available = true ORDER BY c.brand ASC")
    Set<String> findAllBrandsOrderByName();

    @Query("SELECT c FROM Car c WHERE " +
            "c.available = true AND " +
            "(:brand IS NULL OR c.brand = :brand) AND " +
            "(:category IS NULL OR c.category.name = :category) AND " +
            "(:passengerCount IS NULL OR c.passengerCount >= :passengerCount)")
    Page<Car> findAvailableCars(
            @Param("brand") String brand,
            @Param("category") String category,
            @Param("passengerCount") Integer passengerCount,
            Pageable pageable);

    Page<Car> findByBrandContainingIgnoreCaseOrModelContainingIgnoreCase(String keyword, String keyword1, Pageable pageable);

    Page<Car> findByAvailableTrue(Pageable pageable);

    List<Car> findTop15ByAvailableTrueOrderByDailyPriceAsc();
}
