package com.example.carrent.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "testimonials")
public class Testimonial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String authorName;
    private String authorRole;
    private String content;
    private String imageUrl;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDate createdAt;
    private int rating;
    @Column(name = "is_approved", nullable = false, columnDefinition = "boolean default false")
    private boolean isApproved = false;
}
