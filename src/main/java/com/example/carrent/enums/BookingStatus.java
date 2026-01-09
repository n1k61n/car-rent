package com.example.carrent.enums;

public enum BookingStatus {
    PENDING,    // Sifariş verilib, təsdiq gözləyir
    APPROVED,
    REJECTED,
    COMPLETED,   // Maşın geri qaytarılıb və proses bitib
    CONFIRMED,  // Ödəniş edilib və ya admin təsdiqləyib
    CANCELLED,  // İptal edilib
    ACTIVE,
}
