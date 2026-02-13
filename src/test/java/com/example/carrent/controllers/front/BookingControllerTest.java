package com.example.carrent.controllers.front;

import com.example.carrent.dtos.booking.BookingCompleteDto;
import com.example.carrent.services.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.mockito.Mockito.*;

class BookingControllerTest {

    @InjectMocks
    private BookingController bookingController;

    @Mock
    private BookingService bookingService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCompleteBooking_Success() {
        // Arrange
        BookingCompleteDto bookingDto = new BookingCompleteDto();
        bookingDto.setCarId(1L);
        MockMultipartFile licenseFile = new MockMultipartFile("licenseFile", "license.jpg", "image/jpeg", "test data".getBytes());

        when(bookingService.completeBooking(any(BookingCompleteDto.class), any(MockMultipartFile.class))).thenReturn(1L);

        // Act
//        String result = bookingController.completeBooking(bookingDto, licenseFile, model);

        // Assert
//        assertEquals("redirect:/booking/success", result);
        verify(bookingService, times(1)).completeBooking(any(BookingCompleteDto.class), any(MockMultipartFile.class));
    }

    @Test
    void testCompleteBooking_Failure() {
        // Arrange
        BookingCompleteDto bookingDto = new BookingCompleteDto();
        bookingDto.setCarId(1L);
        MockMultipartFile licenseFile = new MockMultipartFile("licenseFile", "license.jpg", "image/jpeg", "test data".getBytes());

        when(bookingService.completeBooking(any(BookingCompleteDto.class), any(MockMultipartFile.class))).thenReturn(0L);

        // Act
//        String result = bookingController.completeBooking(bookingDto, licenseFile, model);

        // Assert
//        assertEquals("redirect:/booking/save?carId=1", result);
        verify(bookingService, times(1)).completeBooking(any(BookingCompleteDto.class), any(MockMultipartFile.class));
    }

    @Test
    void testCompleteBooking_Exception() {
        // Arrange
        BookingCompleteDto bookingDto = new BookingCompleteDto();
        bookingDto.setCarId(1L);
        MockMultipartFile licenseFile = new MockMultipartFile("licenseFile", "license.jpg", "image/jpeg", "test data".getBytes());

        when(bookingService.completeBooking(any(BookingCompleteDto.class), any(MockMultipartFile.class))).thenThrow(new RuntimeException("Test Exception"));

        // Act
//        String result = bookingController.completeBooking(bookingDto, licenseFile, model);

        // Assert
//        assertEquals("redirect:/booking/save?carId=1", result);
        verify(bookingService, times(1)).completeBooking(any(BookingCompleteDto.class), any(MockMultipartFile.class));
    }
}