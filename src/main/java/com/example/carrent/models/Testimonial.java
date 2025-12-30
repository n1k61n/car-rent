package com.example.carrent.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "testimonials")
public class Testimonial {
    //
//### 5. *Testimonial* (Rəylər)
//    Saytın aşağı hissəsində müştərilərin yazdığı rəylər üçün.

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientName;

    private String content;

    private Integer rating;

}
