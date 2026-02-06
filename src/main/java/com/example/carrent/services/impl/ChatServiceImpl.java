package com.example.carrent.services.impl;


import com.example.carrent.dtos.chat.ChatDto;
import com.example.carrent.models.AiPrompts;
import com.example.carrent.models.Car;
import com.example.carrent.models.Chat;
import com.example.carrent.repositories.CarRepository;
import com.example.carrent.repositories.ChatRepository;
import com.example.carrent.services.ChatService;
import com.example.carrent.services.GeminiService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final CarRepository carRepository;
    private final GeminiService geminiService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String AI_SYSTEM_PROMPT = """
Sən car-rent saytının peşəkar və mehriban AI köməkçisisən.
Sənə backend tərəfindən INVENTORY_JSON veriləcək. Yalnız həmin siyahıdakı maşınları tövsiyə et.
Uydurma maşın, qiymət, endirim, stok demə.

Cavabların məntiqli, səlis və köməkçi olsun.
Əgər istifadəçi sadəcə salamlayırsa, onu salamla və necə kömək edə biləcəyini soruş.
Əgər istifadəçi maşın istəyirsə amma detalları (gün, nəfər sayı) deməyibsə, bu formatda soruş:
"Zəhmət olmasa, neçə günlük və neçə nəfərlik (Məsələn: 1 gün və 1 nəfər) avtomobil axtardığınızı qeyd edin."

Cavab strukturu:
- Top 3 tövsiyə ver (mümkünsə), hər biri üçün qısa səbəb yaz.
- Əgər məlumat çatmırsa, dəqiqləşdirici suallar ver.
- Şəxsi həssas məlumat (kart, şifrə) əsla istəmə.

Sonda yalnız bu JSON formatında cavab qaytar (başqa heç nə yazma):
{
  "reply": "string",
  "recommended_car_ids": [1,2,3],
  "follow_up_questions": ["string", "string"]
}
""";


    @Override
    public boolean saveChat(ChatDto chatDto) {
        if(chatDto == null) return false;
        Chat chat = mapToEntity(chatDto);
        chatRepository.save(chat);
        return true;
    }

    @Override
    public List<ChatDto> getHistoryBySessionId(String sessionId) {
        return chatRepository.findAllBySessionIdOrderByIdAsc(sessionId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatDto> getUniqueChatSessionsAfter(LocalDateTime date) {
        return chatRepository.findUniqueSessionsAfter(date)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public ChatDto generateAiReply(ChatDto userMsg) {

        // 1) DB-dən real maşınlar (available=true)
        List<Car> cars = carRepository.findTop15ByAvailableTrueOrderByDailyPriceAsc();

        // 2) Inventory JSON (null-safe, Java 8 friendly)
        List<Map<String, Object>> inv = new ArrayList<>();
        for (Car c : cars) {
            Map<String, Object> m = new LinkedHashMap<>();
            m.put("id", c.getId());
            m.put("brand", c.getBrand());
            m.put("model", c.getModel());
            m.put("dailyPrice", c.getDailyPrice()); // BigDecimal
            m.put("year", c.getYear());
            m.put("transmission", c.getTransmission());
            m.put("fuelType", c.getFuelType());
            m.put("doorCount", c.getDoorCount());
            m.put("passengerCount", c.getPassengerCount());
            m.put("luggageCapacity", c.getLuggageCapacity());

            // Boolean null ola bilər → false default
            m.put("roadAssistance", Boolean.TRUE.equals(c.getRoadAssistance()));
            m.put("insuranceIncluded", Boolean.TRUE.equals(c.getInsuranceIncluded()));
            m.put("freeCancellation", Boolean.TRUE.equals(c.getFreeCancellation()));

            // UI üçün şəkil URL (əgər varsa)
            m.put("imageUrl", c.getImageUrl());

            inv.add(m);
        }

        String inventoryJson;
        try {
            inventoryJson = objectMapper.writeValueAsString(Collections.singletonMap("inventory", inv));
        } catch (Exception e) {
            inventoryJson = "{\"inventory\":[]}";
        }

        // 3) Gemini call (DTO-da content var!)
        String raw = geminiService.generate(
                AI_SYSTEM_PROMPT,
                inventoryJson,
                safe(userMsg.getContent())
        );

        String text = geminiService.extractText(raw);

        // 4) AI JSON parse (fallback ilə)
        String reply = (text == null || text.isBlank()) ? "Bağışlayın, hazırda cavab verə bilmirəm." : text;

        // Clean up markdown code blocks if present
        if (reply.startsWith("```json")) {
            reply = reply.substring(7);
        } else if (reply.startsWith("```")) {
            reply = reply.substring(3);
        }
        if (reply.endsWith("```")) {
            reply = reply.substring(0, reply.length() - 3);
        }
        reply = reply.trim();

        List<Long> recommendedIds = new ArrayList<>();

        try {
            JsonNode j = objectMapper.readTree(reply);
            String extracted = j.path("reply").asText(null);
            if (extracted != null && !extracted.isBlank()) {
                reply = extracted;
            }
            
            // Extract recommended car IDs
            if (j.has("recommended_car_ids")) {
                JsonNode idsNode = j.get("recommended_car_ids");
                if (idsNode.isArray()) {
                    for (JsonNode id : idsNode) {
                        recommendedIds.add(id.asLong());
                    }
                }
            }
        } catch (Exception ignore) {
            // reply plain text qaldı
        }

        // 5) ChatDto kimi geri qaytar
        ChatDto ai = new ChatDto();
        ai.setFrom("AI");
        ai.setTo(userMsg.getFrom());               // user-ə gedir
        ai.setEmail(userMsg.getEmail());
        ai.setSessionId(userMsg.getSessionId());
        ai.setCreatedAt(LocalDateTime.now());
        ai.setContent(reply);
        ai.setRecommendedCarIds(recommendedIds); // Set the IDs

        return ai;
    }


    private String safe(String s) {
        return s == null ? "" : s.trim();
    }


    private ChatDto mapToDto(Chat chat) {
        return new ChatDto(
                chat.getId(),
                chat.getFrom(),
                chat.getTo(),
                chat.getEmail(),
                chat.getContent(),
                chat.getCreatedAt(),
                chat.getSessionId(),
                null // recommendedCarIds is transient, not stored in DB
        );
    }

    private Chat mapToEntity(ChatDto dto) {
        Chat chat = new Chat();
        chat.setId(dto.getId());
        chat.setFrom(dto.getFrom());
        chat.setTo(dto.getTo());
        chat.setEmail(dto.getEmail());
        chat.setContent(dto.getContent());
        if (dto.getCreatedAt() != null) {
            chat.setCreatedAt(dto.getCreatedAt());
        } else {
            chat.setCreatedAt(LocalDateTime.now());
        }
        chat.setSessionId(dto.getSessionId());
        return chat;
    }
}