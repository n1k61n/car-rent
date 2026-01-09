package com.example.carrent.dtos.user;

import com.example.carrent.enums.Role;
import lombok.Data;

import java.util.List;

@Data
public class UsersDashboardDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String password;
    private Role role;
    private boolean enabled;
}
