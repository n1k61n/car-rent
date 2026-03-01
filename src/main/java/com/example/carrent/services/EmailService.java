package com.example.carrent.services;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
    void sendOtpEmail(String toEmail, String otp);
    void sendNewPasswordEmail(String toEmail, String newPassword);
}
