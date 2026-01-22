package com.example.carrent.dtos.testimonial;

import jakarta.validation.constraints.*;
import lombok.Data;
@Data
public class TestimonialUpdateDto {
    @NotNull(message = "ID boş ola bilməz") // Yeniləmə zamanı ID mütləqdir
    private Long id;

    @NotBlank(message = "Müəllif adı boş ola bilməz")
    @Size(min = 2, max = 100, message = "Ad 2 ilə 100 simvol arasında olmalıdır")
    private String authorName;

    @NotBlank(message = "Müəllif rolu boş ola bilməz")
    @Size(max = 100, message = "Rol 100 simvoldan çox olmamalıdır")
    private String authorRole;

    @NotBlank(message = "Rəy mətni boş ola bilməz")
    @Size(max = 255, message = "Rəy mətni 255 simvoldan çox olmamalıdır")
    private String content;

    private String imageUrl; // URL adətən opsional olur, amma istəsəniz @URL əlavə edə bilərsiniz

    @Min(value = 1, message = "Reytinq minimum 1 olmalıdır")
    @Max(value = 5, message = "Reytinq maksimum 5 olmalıdır")
    private int rating;

    private boolean isApproved;
}