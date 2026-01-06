package com.example.carrent.dtos.team;

import lombok.Data;

@Data
public class TeamMemberUpdateDto {
    private Long id;
    private String name;
    private String role;
    private String bio;
    private String imageUrl;
}
