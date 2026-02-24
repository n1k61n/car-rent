package com.example.carrent.services.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class KeepAliveServiceImpl {

    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 600000)
    public void keepServerAlive() {
        try {
            // Render-dəki saytınızın URL-ni bura yazın
            String url = "https://car-rent-bb57.onrender.com/actuator/health";
            String response = restTemplate.getForObject(url, String.class);
            System.out.println("Ping uğurlu oldu, server oyaqdır!");
        } catch (Exception e) {
            System.err.println("Ping xətası: " + e.getMessage());
        }
    }
}