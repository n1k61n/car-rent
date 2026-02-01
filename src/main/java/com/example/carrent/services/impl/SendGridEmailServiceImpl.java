package com.example.carrent.services.impl;

import com.example.carrent.exceptions.ResourceNotFoundException;
import com.example.carrent.models.Booking;
import com.example.carrent.repositories.BookingRepository;
import com.example.carrent.services.SendGridEmailService;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SendGridEmailServiceImpl implements SendGridEmailService {

    private final BookingRepository bookingRepository;

    @Value("${sendgrid.api.key}")
    private String apiKey;

    @Value("${sendgrid.from.email}")
    private String fromEmail;

    @Async("taskExecutor")
    @Override
    public void sendOtpEmail(String toEmail, String subject, String messageText) {
        Email from = new Email(fromEmail);
        Email to = new Email(toEmail);
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
                "<p style='font-size: 18px; color: #333; margin: 0; font-weight: bold; line-height: 1.5;'>" + messageText + "</p>" +
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

        Mail mail = new Mail(from, subject, to, content);
        sendEmail(mail);
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

        Email from = new Email(fromEmail);
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
        Mail mail = new Mail(from, subject, to, content);
        sendEmail(mail);
    }

    private void sendEmail(Mail mail) {
        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);

            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                log.info("Email uğurla göndərildi: {}", response.getStatusCode());
            } else {
                log.error("Email göndərmə xətası: {}. Detal: {}", response.getStatusCode(), response.getBody());
            }
        } catch (IOException ex) {
            log.error("Email göndərmə zamanı IO xətası.", ex);
        }
    }
}
