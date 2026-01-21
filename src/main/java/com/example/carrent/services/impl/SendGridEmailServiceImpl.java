package com.example.carrent.services.impl;

import com.example.carrent.services.SendGridEmailService;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Slf4j
@Service
public class SendGridEmailServiceImpl implements SendGridEmailService {

    @Value("${sendgrid.api.key}")
    private String apiKey;


    @Async("taskExecutor")
    @Override
    public void sendOtpEmail(String toEmail, String otp) {
        Email from = new Email("eminelxanoglu@gmail.com");
        Email to = new Email(toEmail);
        String subject = "Doğrulama Kodu (OTP)";
        String htmlBody = "<html><body>" +
                "<h2 style='color: #2e6da4;'>Doğrulama Kodu</h2>" +
                "<p>Sizin təhlükəsizlik kodunuz:</p>" +
                "<h1 style='letter-spacing: 5px; background: #f4f4f4; padding: 10px; display: inline-block;'>" + otp + "</h1>" +
                "<p style='color: gray; font-size: 12px;'>Bu kodu heç kimlə bölüşməyin.</p>" +
                "</body></html>";
        Content content = new Content("text/html", htmlBody);

        Mail mail = new Mail(from, subject, to, content);
        System.out.println("API Key starts with: " + apiKey.substring(0, 5));
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
            ex.printStackTrace();
        }
    }
}