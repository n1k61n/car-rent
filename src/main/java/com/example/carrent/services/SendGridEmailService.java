package com.example.carrent.services;

public interface SendGridEmailService {
    void sendOtpEmail(String email, String subject, String otp);
    void sendBookingConfirmationEmail(Long bookingId);
}
