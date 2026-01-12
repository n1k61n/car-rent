package com.example.carrent.services.impl;

import com.example.carrent.models.Otp;
import com.example.carrent.repositories.OtpRepository;
import com.example.carrent.services.OtpService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {

    private final OtpRepository otpRepository;


    @Transactional
    public boolean verifyOtp(String email, String code) {
        Optional<Otp> otpOpt = otpRepository.findByEmail(email);

        if (otpOpt.isPresent()) {
            Otp otp = otpOpt.get();
            if (otp.getOtpCode().equals(code) && otp.getExpiresAt().isAfter(LocalDateTime.now())) {
                otpRepository.deleteByEmail(email);
                return true;
            }
        }
        return false;
    }
}
