package com.example.carrent.services;


public interface GeminiService {
    String generate(String systemPrompt, String inventoryJson, String userMessage);
    String extractText(String geminiRawJson);
}

