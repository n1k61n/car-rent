package com.example.carrent.services;

import com.example.carrent.dtos.user.*;
import org.jspecify.annotations.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {
    boolean existsByEmail(String email);

    boolean registerNewUser(UserRegistrationDto registrationDto);

    UserProfileDto findByEmail(String name);


    Page<UsersDashboardDto> findPaginated(int page, int size, String keyword);

    long countAll();

    boolean updateProfile(String email, UserProfileUpdateDto userProfileUpdateDto);

    boolean deleteBooking(Long id, String name);

    UsersDashboardDto findById(Long id);

    void toggleUserStatus(Long id);

    void enableUser(String email);
    
    void resetPasswordToRandom(String email);
}
