package com.example.carrent.services;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public interface SendGridEmailService {
    void sendOtpEmail(String email, String subject, String otp);
}
