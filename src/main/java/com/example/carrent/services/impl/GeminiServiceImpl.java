package com.example.carrent.services.impl;

import com.example.carrent.services.GeminiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GeminiServiceImpl implements GeminiService {
    @Value("${google.api.key:}")
    private String apiKey;



    @Value("${gemini.base-url:https://generativelanguage.googleapis.com/v1/models}")
    private String baseUrl;

    @Value("${gemini.model:gemini-2.5-flash}")
    private String model;



    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public String generate(String systemPrompt, String inventoryJson, String userMessage) {
        if (apiKey == null || apiKey.isBlank()) {
            throw new IllegalStateException("GOOGLE_API_KEY is not configured");
        }

        String url = String.format("%s/%s:generateContent?key=%s", baseUrl, model, apiKey);

        // Gemini contents: system + context + user
        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of("role", "user", "parts", List.of(Map.of("text", "SYSTEM:\n" + systemPrompt))),
                        Map.of("role", "user", "parts", List.of(Map.of("text", "INVENTORY_JSON:\n" + inventoryJson))),
                        Map.of("role", "user", "parts", List.of(Map.of("text", "USER:\n" + userMessage)))
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            String resp = restTemplate.postForObject(url, entity, String.class);
            return resp;
        } catch (HttpClientErrorException.TooManyRequests e) {
            // Fallback JSON response when quota is exceeded
            return "{\"candidates\": [{\"content\": {\"parts\": [{\"text\": \"{\\\"reply\\\": \\\"Hazırda sistem çox yüklüdür. Zəhmət olmasa adminin cavabini gozleyin və yenidən cəhd edin.\\\"}\"}]}}]}";
        } catch (Exception e) {
            // General error fallback
            return "{\"candidates\": [{\"content\": {\"parts\": [{\"text\": \"{\\\"reply\\\": \\\"Xəta baş verdi. Zəhmət olmasa bir az sonra yenidən cəhd edin.\\\"}\"}]}}]}";
        }
    }

    public String extractText(String geminiRawJson) {
        try {
            JsonNode root = objectMapper.readTree(geminiRawJson);
            return root.path("candidates").path(0).path("content").path("parts").path(0).path("text").asText("");
        } catch (Exception e) {
            return "";
        }
    }
}
