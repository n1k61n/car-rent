package com.example.carrent.services.impl;

import com.example.carrent.models.Otp;
import com.example.carrent.repositories.OtpRepository;
import com.example.carrent.services.EmailService;
import com.example.carrent.services.OtpService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final OtpRepository otpRepository;
    private final EmailService emailService;


    public boolean verifyOtp(String email, String code) {
        Optional<Otp> otpOpt = otpRepository.findByEmail(email);

        if (otpOpt.isPresent()) {
            Otp otp = otpOpt.get();
            if (otp.getExpiresAt().isAfter(LocalDateTime.now()) && otp.getOtpCode().equals(code)) {
                otpRepository.delete(otp);
                return true;
            }
        }
        return false;
    }

    @Override
    public String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    @Override
    public boolean saved(Otp otpEntity) {
        if(otpEntity == null) return false;
        otpRepository.save(otpEntity);
        return true;
    }

    @Override
    public void createAndSendOtp(String email) {
        // 1. Əgər bu email üçün əvvəlki kod varsa, onu silirik
        otpRepository.deleteByEmail(email);

        // 2. Yeni OTP yaradılır
        String code = String.valueOf(100000 + new Random().nextInt(900000));
        Otp otp = new Otp(email, code);
        otpRepository.save(otp);

        // 3. Email göndərilir
        emailService.sendOtpEmail(email, code);
    }
}
