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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Override
    public String generate(String systemPrompt, String inventoryJson, String userMessage) {
        if (apiKey == null || apiKey.isBlank()) {
            return wrapReplyJson("GOOGLE_API_KEY konfiqurasiya olunmayıb. Zəhmət olmasa adminlə əlaqə saxlayın.");
        }

        String url = String.format("%s/%s:generateContent?key=%s", baseUrl, model, apiKey);

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
            return restTemplate.postForObject(url, entity, String.class);
        } catch (HttpClientErrorException.TooManyRequests e) {
            // 429: sleep etmə, dərhal fallback qaytar
            return wrapReplyJson("Hazırda sistem çox yüklüdür. 20-30 saniyə sonra yenidən cəhd edin və ya adminin cavabını gözləyin.");
        } catch (Exception e) {
            return wrapReplyJson("Xəta baş verdi. Zəhmət olmasa bir az sonra yenidən cəhd edin.");
        }
    }


    @Override
    public String extractText(String geminiRawJson) {
        try {
            JsonNode root = objectMapper.readTree(geminiRawJson);
            return root.path("candidates").path(0).path("content").path("parts").path(0).path("text").asText("");
        } catch (Exception e) {
            return "";
        }
    }

    private String wrapReplyJson(String reply) {
        // elə formatla ki, extractText() içindən JSON kimi gəlsin
        String safe = reply.replace("\"", "\\\"");
        return "{\"candidates\":[{\"content\":{\"parts\":[{\"text\":\"{\\\"reply\\\":\\\"" + safe + "\\\",\\\"recommended_car_ids\\\":[],\\\"follow_up_questions\\\":[]}\"}]}}]}";
    }

    private long extractRetryDelayMs(String body, long defaultMs) {
        if (body == null) return defaultMs;
        // body-də "retryDelay": "19s" kimi olur
        Pattern p = Pattern.compile("\"retryDelay\"\\s*:\\s*\"(\\d+)s\"");
        Matcher m = p.matcher(body);
        if (m.find()) {
            long seconds = Long.parseLong(m.group(1));
            return Math.min(60000, Math.max(1000, seconds * 1000)); // 1s-60s arası
        }
        return defaultMs;
    }
}
