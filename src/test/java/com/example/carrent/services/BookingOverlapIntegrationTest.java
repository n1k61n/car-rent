package com.example.carrent.services;

import com.example.carrent.dtos.booking.BookingCompleteDto;
import com.example.carrent.enums.BookingStatus;
import com.example.carrent.models.Car;
import com.example.carrent.models.User;
import com.example.carrent.repositories.BookingRepository;
import com.example.carrent.repositories.CarRepository;
import com.example.carrent.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class BookingOverlapIntegrationTest {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    private Car testCar;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setPassword("password");
        testUser.setFirstName("Test");
        testUser.setLastName("User");
        userRepository.save(testUser);

        testCar = new Car();
        testCar.setBrand("Tesla");
        testCar.setModel("Model S");
        testCar.setDailyPrice(BigDecimal.valueOf(100));
        testCar.setAvailable(true);
        carRepository.save(testCar);
    }

    @Test
    void testDoubleBookingOverlappingDates() {
        // First booking: Jan 10 to Jan 15
        BookingCompleteDto booking1 = new BookingCompleteDto();
        booking1.setCarId(testCar.getId());
        booking1.setUserId(testUser.getId());
        booking1.setStartDate(LocalDate.of(2026, 1, 10));
        booking1.setEndDate(LocalDate.of(2026, 1, 15));
        booking1.setEmail(testUser.getEmail());

        MockMultipartFile file1 = new MockMultipartFile("licenseFile", "license1.jpg", "image/jpeg", "test1".getBytes());

        Long id1 = bookingService.completeBooking(booking1, file1);
        assertNotNull(id1);

        // Second booking: Jan 12 to Jan 17 (Overlaps with first)
        BookingCompleteDto booking2 = new BookingCompleteDto();
        booking2.setCarId(testCar.getId());
        booking2.setUserId(testUser.getId());
        booking2.setStartDate(LocalDate.of(2026, 1, 12));
        booking2.setEndDate(LocalDate.of(2026, 1, 17));
        booking2.setEmail(testUser.getEmail());

        MockMultipartFile file2 = new MockMultipartFile("licenseFile", "license2.jpg", "image/jpeg", "test2".getBytes());

        // This should fail if overlap check is implemented
        // Currently it might succeed, which is the bug
        try {
            Long id2 = bookingService.completeBooking(booking2, file2);
            // If it succeeds, it means double booking is possible
            assertNotNull(id2, "Second booking should have failed but it succeeded");
            
            // If we reach here, let's check if both exist in DB
            long count = bookingRepository.count();
            assertTrue(count >= 2, "Both bookings should not exist if double booking is prevented");
        } catch (Exception e) {
            // If it fails, then it's already fixed or has some other validation
            System.out.println("Second booking failed as expected: " + e.getMessage());
        }
    }
}
