package com.example.carrent.services.impl;
import com.example.carrent.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendOtpEmail(String toEmail, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("rentcar@gmail.com");
        message.setTo(toEmail);
        message.setSubject("Doğrulama Kodu (OTP)");
        message.setText("Sizin təhlükəsizlik kodunuz: " + otp + "\n\nBu kodu heç kimlə bölüşməyin.");

        mailSender.send(message);
    }
}