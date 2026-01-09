package com.example.carrent.services;

import com.example.carrent.dtos.user.UserDto;
import com.example.carrent.dtos.user.UserProfileDto;
import com.example.carrent.dtos.user.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {
    boolean existsByEmail(String email);

    boolean registerNewUser(UserRegistrationDto registrationDto);

    UserProfileDto findByEmail(String name);
}
