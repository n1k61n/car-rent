package com.example.carrent.services;


public interface GeminiService {
    String generate(String systemPrompt, String inventoryJson, String userMessage);
    String extractText(String geminiRawJson);
    
    // OCR for extracting data from uploaded licenses/IDs
    String extractDataFromImage(byte[] imageBytes, String mimeType, String prompt);
    
    // Dynamic Pricing Suggestion
    String suggestPrice(String carDetailsJson, String marketConditions);
}

