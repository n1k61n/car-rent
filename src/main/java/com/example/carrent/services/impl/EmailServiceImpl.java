package com.example.carrent.services.impl;
import com.example.carrent.exceptions.ResourceNotFoundException;
import com.example.carrent.models.Booking;
import com.example.carrent.repositories.BookingRepository;
import com.example.carrent.services.EmailService;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender mailSender;
    private final BookingRepository bookingRepository;

    public EmailServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }


    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();

        String htmlBody = "<html><body style='font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px;'>" +
                "<div style='max-width: 600px; margin: 0 auto; background-color: #ffffff; border-radius: 8px; overflow: hidden; border: 1px solid #e0e0e0;'>" +
                // Header (Brend rəngi - tünd göy/qara)
                "<div style='background-color: #1a1a1a; padding: 20px; text-align: center;'>" +
                "<h2 style='color: #ffffff; margin: 0; text-transform: uppercase; letter-spacing: 2px;'>Car Rent Service</h2>" +
                "</div>" +
                // Content
                "<div style='padding: 30px; text-align: center;'>" +
                "<img src='https://cdn-icons-png.flaticon.com/512/2983/2983787.png' width='50' style='margin-bottom: 20px;' alt='icon'>" +
                "<h3 style='color: #333; margin-bottom: 15px;'>" + subject + "</h3>" +
                "<div style='background-color: #f4f4f4; padding: 20px; border-radius: 5px; border-left: 4px solid #1a1a1a; margin-bottom: 20px;'>" +
                "<p style='font-size: 18px; color: #333; margin: 0; font-weight: bold; line-height: 1.5;'>" + body + "</p>" +
                "</div>" +
                "<p style='color: #666; font-size: 14px;'>Hər hansı bir sualınız yaranarsa, dəstək komandamızla əlaqə saxlaya bilərsiniz.</p>" +
                "</div>" +
                // Footer
                "<div style='background-color: #f1f1f1; padding: 15px; text-align: center; border-top: 1px solid #eeeeee;'>" +
                "<p style='color: #999; font-size: 12px; margin: 0;'>Bu avtomatik göndərilən bildirişdir. Zəhmət olmasa cavab verməyin.</p>" +
                "</div>" +
                "</div>" +
                "</body></html>";
        Content content = new Content("text/html", htmlBody);

        message.setFrom("noreply@carrent.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content.toString());
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

    @Async("taskExecutor")
    @Transactional(readOnly = true)
    @Override
    public void sendBookingConfirmationEmail(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with id: " + bookingId));

        if (booking.getUser() == null) {
            log.error("User information is missing for booking id: {}. Email could not be sent.", bookingId);
            return;
        }

        Email to = new Email(booking.getUser().getEmail());
        String subject = "Sifariş Təsdiqi - " + booking.getCar().getBrand() + " " + booking.getCar().getModel();

        String htmlBody = "<html><body>" +
                "<h2>Hörmətli " + booking.getUser().getFirstName() + ",</h2>" +
                "<p>Sifarişiniz uğurla qəbul edildi. Detallar aşağıdadır:</p>" +
                "<ul>" +
                "<li><strong>Avtomobil:</strong> " + booking.getCar().getBrand() + " " + booking.getCar().getModel() + "</li>" +
                "<li><strong>Götürmə tarixi:</strong> " + booking.getStartDate() + "</li>" +
                "<li><strong>Qaytarma tarixi:</strong> " + booking.getEndDate() + "</li>" +
                "<li><strong>Götürmə yeri:</strong> " + booking.getPickupLocation() + "</li>" +
                "<li><strong>Ümumi məbləğ:</strong> " + booking.getTotalPrice() + " AZN</li>" +
                "</ul>" +
                "<p>Tezliklə sizinlə əlaqə saxlanılacaq. Bizi seçdiyiniz üçün təşəkkür edirik!</p>" +
                "</body></html>";

        Content content = new Content("text/html", htmlBody);
        sendEmail(String.valueOf(to), subject, content.toString());
    }
}
