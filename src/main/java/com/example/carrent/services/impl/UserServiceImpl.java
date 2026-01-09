package com.example.carrent.services.impl;

import com.example.carrent.dtos.user.UserProfileDto;
import com.example.carrent.dtos.user.UserRegistrationDto;
import com.example.carrent.enums.Role;
import com.example.carrent.models.User;
import com.example.carrent.repositories.UserRepository;
import com.example.carrent.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public boolean registerNewUser(UserRegistrationDto registrationDto) {
        try {

            User user = modelMapper.map(registrationDto, User.class);
            user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

            Role userRole = userRepository.count() == 0 ? Role.ADMIN : Role.USER;
            user.setRole(userRole);
            user.setEnabled(true);
            user.setAccountNonLocked(true);
            user.setAccountNonExpired(true);
            user.setCredentialsNonExpired(true);
            userRepository.save(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileDto findByEmail(String name) {
        User user = userRepository.findByEmail(name).orElseThrow(()->new UsernameNotFoundException("User not found"));
        return modelMapper.map(user, UserProfileDto.class);
    }


    @Override
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}