package com.example.carrent.services;

import com.example.carrent.dtos.user.UserDto;
import com.example.carrent.dtos.user.UserProfileDto;
import com.example.carrent.dtos.user.UserRegistrationDto;
import com.example.carrent.dtos.user.UsersDashboardDto;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    boolean existsByEmail(String email);

    boolean registerNewUser(UserRegistrationDto registrationDto);

    UserProfileDto findByEmail(String name);

    List<UsersDashboardDto> getAllUsers();

    Page<UsersDashboardDto> findPaginated(int page, int size, String keyword);

    long countAll();
}
