package com.example.carrent.dtos.user;

import lombok.Data;

@Data
public class UserProfileDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
