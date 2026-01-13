package com.example.carrent.dtos.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserProfileUpdateDto {
    @NotBlank(message = "Ad boş ola bilməz.")
    @Size(max = 50)
    private String firstName;

    @NotBlank(message = "Soyad boş ola bilməz.")
    @Size(max = 50)
    private String lastName;

    @Size(max = 15)
    private String phoneNumber;

    // Şifrə boş ola bilər (dəyişdirmək istəmirsə)
    @Size(min = 6, max = 100, message = "Şifrə 6 ilə 100 simvol arasında olmalıdır.")
    private String password;
}
