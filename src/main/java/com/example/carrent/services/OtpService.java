package com.example.carrent.services;

public interface OtpService {
    boolean verifyOtp(String email, String code);
}
