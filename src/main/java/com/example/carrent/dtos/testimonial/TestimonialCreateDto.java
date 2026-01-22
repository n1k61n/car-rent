package com.example.carrent.dtos.testimonial;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TestimonialCreateDto {

    @NotBlank(message = "Müəllif adı boş ola bilməz")
    @Size(min = 2, max = 100, message = "Ad 2 ilə 100 simvol arasında olmalıdır")
    private String authorName;

    @NotBlank(message = "Müəllif rolu boş ola bilməz")
    private String authorRole;

    @NotBlank(message = "Rəy mətni boş ola bilməz")
    @Size(max = 255, message = "Rəy mətni 255 simvoldan çox olmamalıdır")
    private String content;

    private String imageUrl;

    @Min(value = 1, message = "Reytinq minimum 1 olmalıdır")
    @Max(value = 5, message = "Reytinq maksimum 5 olmalıdır")
    private int rating;

    private boolean isApproved;
}