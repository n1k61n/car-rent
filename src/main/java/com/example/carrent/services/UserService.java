package com.example.carrent.services;

import com.example.carrent.dtos.user.UserProfileDto;
import com.example.carrent.dtos.user.UserProfileUpdateDto;
import com.example.carrent.dtos.user.UserRegistrationDto;
import com.example.carrent.dtos.user.UsersDashboardDto;
import com.example.carrent.models.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    boolean existsByEmail(String email);

    boolean registerNewUser(UserRegistrationDto registrationDto);

    UserProfileDto findByEmail(String name);


    Page<UsersDashboardDto> findPaginated(int page, int size, String keyword);

    long countAll();

    boolean updateProfile(String email, UserProfileUpdateDto userProfileUpdateDto);

    UsersDashboardDto findById(Long id);

    void toggleUserStatus(Long id);

    void enableUser(String email);
    
    void resetPasswordToRandom(String email);

    List<User> getRecentUsers();
}
