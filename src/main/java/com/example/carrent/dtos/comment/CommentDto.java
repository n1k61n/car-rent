package com.example.carrent.dtos.comment;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    @NotBlank(message = "Ad boş ola bilməz.")
    @Size(max = 50, message = "Ad 50 simvoldan çox ola bilməz.")
    private String name;

    @NotBlank(message = "Email boş ola bilməz.")
    @Email(message = "Düzgün email formatı daxil edin.")
    private String email;

    @NotBlank(message = "Mesaj boş ola bilməz.")
    @Size(max = 500, message = "Mesaj 500 simvoldan çox ola bilməz.")
    private String message;

    @NotNull(message = "Blog ID boş ola bilməz.")
    private Long blogId;

    private LocalDateTime createdAt;
}