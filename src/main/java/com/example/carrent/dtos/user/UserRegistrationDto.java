package com.example.carrent.dtos.user;

import lombok.Data;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRegistrationDto {
    @NotBlank(message = "Ad boş ola bilməz")
    @Size(min = 2, max = 50, message = "Ad 2-50 simvol arası olmalıdır")
    private String firstName;

    @NotBlank(message = "Soyad boş ola bilməz")
    @Size(min = 2, max = 50, message = "Soyad 2-50 simvol arası olmalıdır")
    private String lastName;

    @NotBlank(message = "Email boş ola bilməz")
    @Email(message = "Düzgün bir email ünvanı daxil edin")
    private String email;

    @NotBlank(message = "Telefon nömrəsi mütləqdir")
    @Pattern(regexp = "^(\\+994|0)(50|51|55|70|77|99)[2-9]\\d{6}$",
            message = "Düzgün Azərbaycan nömrəsi daxil edin (məs: +99450XXXXXXX)")
    private String phoneNumber;

    @NotBlank(message = "Şifrə boş ola bilməz")
    @Size(min = 8, message = "Şifrə ən az 8 simvol olmalıdır")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()\\-_=+]).{8,}$",
            message = "Şifrədə ən az bir rəqəm, bir kiçik hərf, bir böyük hərf və bir xüsusi simvol olmalıdır")
    private String password;

    @NotBlank(message = "Şifrə təkrarı mütləqdir")
    private String confirmPassword;
}