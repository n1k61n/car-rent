package com.example.carrent.services;

import com.example.carrent.models.Otp;

public interface OtpService {
    void verifyOtp(String email, String code);

    String generateOTP();

    boolean saved(Otp otpEntity);

    void createAndSendOtp(String email);
}
