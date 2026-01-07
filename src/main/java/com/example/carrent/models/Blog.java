package com.example.carrent.models;

import com.example.carrent.dtos.comment.CommentDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "blogs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    private String imageUrl; // car image url
    private String author;
    private LocalDate createdAt;
    private String categoryName;
    private String authorImageUrl;
    private String authorBio;
    private String authorDescription;
    @Column(columnDefinition = "TEXT")
    private String post;
    @OneToMany(mappedBy = "blog", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
    @ManyToOne
    private Car car;
}