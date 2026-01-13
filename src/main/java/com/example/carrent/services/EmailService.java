package com.example.carrent.services;

public interface EmailService {
    void sendOtpEmail(String toEmail, String otp);
    void sendNewPasswordEmail(String toEmail, String newPassword);
}
