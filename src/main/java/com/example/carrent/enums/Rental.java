package com.example.carrent.enums;

public enum Rental {
    //Rental (İcarə): Müştəri ofisə gəlir, açarları götürür və maşını sürməyə başlayır.
    // Bu artıq aktiv bir icarə prosesidir. (Status: ACTIVE)

    PENDING , //    Müştəri müraciət edib, amma hələ təsdiqlənməyib.
    CONFIRMED, // Menecer tərəfində arenda təsdiqlənib.
    ACTIVE,  // Müştəri maşını artıq götürüb və hazırda sürür.
    COMPLETED, // Maşın geri qaytarılıb, proses uğurla bitib.
    CANCELLED, // Arenda ləğv edilib.
    OVERDUE; // Maşın qaytarılmalı olan vaxtda qaytarılmayıb (gecikmə).
}
