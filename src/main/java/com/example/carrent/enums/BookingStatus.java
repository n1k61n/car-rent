package com.example.carrent.enums;

public enum BookingStatus {
    PENDING,    // Sifariş verilib, təsdiq gözləyir
    CONFIRMED,  // Ödəniş edilib və ya admin təsdiqləyib
    CANCELLED,  // İptal edilib
    COMPLETED   // Maşın geri qaytarılıb və proses bitib
}
