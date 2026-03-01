package com.example.carrent.services.impl;
import com.example.carrent.services.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@carrent.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }

    @Async
    public void sendOtpEmail(String toEmail, String otp) {
        log.info("Sending OTP email to: {}", toEmail);
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("eminelxanoglu@gmail.com");
            message.setTo(toEmail);
            message.setSubject("Doğrulama Kodu (OTP)");
            message.setText("Sizin təhlükəsizlik kodunuz: " + otp + "\n\nBu kodu heç kimlə bölüşməyin.");
            mailSender.send(message);
            log.info("OTP email sent successfully to: {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send OTP email to: {}", toEmail, e);
        }
    }

    @Override
    public void sendNewPasswordEmail(String toEmail, String newPassword) {
        log.info("Sending new password email to: {}", toEmail);
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("eminelxanoglu@gmail.com");
            message.setTo(toEmail);
            message.setSubject("Yeni Şifrəniz");
            message.setText("Sizin yeni şifrəniz: " + newPassword + "\n\nZəhmət olmasa giriş etdikdən sonra şifrənizi dəyişin.");
            mailSender.send(message);
            log.info("New password email sent successfully to: {}", toEmail);
        } catch (Exception e) {
            log.error("Failed to send new password email to: {}", toEmail, e);
        }
    }
}
