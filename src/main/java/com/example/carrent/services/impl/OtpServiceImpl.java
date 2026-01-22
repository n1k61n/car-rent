package com.example.carrent.services.impl;

import com.example.carrent.models.Otp;
import com.example.carrent.repositories.OtpRepository;
import com.example.carrent.services.EmailService;
import com.example.carrent.services.OtpService;
import com.example.carrent.services.SendGridEmailService;
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
    private final SendGridEmailService sendGridEmailService;


    public boolean verifyOtp(String email, String code) {
        Otp otp = otpRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("INVALID_CODE"));

        if (!otp.getOtpCode().equals(code)) {
            throw new RuntimeException("INVALID_CODE"); // The code does not match
        }

        if (otp.getExpiresAt().isBefore(LocalDateTime.now())) {
            otpRepository.delete(otp); // Remove expired OTP
            throw new RuntimeException("EXPIRED_CODE"); // The code has expired
        }

        otpRepository.delete(otp); // Delete OTP after successful verification
        return true;
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
    @Transactional
    public void createAndSendOtp(String email) {
        String code = generateOTP();
        Optional<Otp> existingOtp = otpRepository.findByEmail(email);
        Otp otp;
        if (existingOtp.isPresent()) {
            otp = existingOtp.get();
        } else {
            otp = new Otp();
            otp.setEmail(email);
        }
        otp.setOtpCode(code);
        otp.setExpiresAt(LocalDateTime.now().plusMinutes(2));
        otpRepository.save(otp);
        String subject = "Doğrulama Kodu (OTP)";
        sendGridEmailService.sendOtpEmail(email, subject, code);
    }
}