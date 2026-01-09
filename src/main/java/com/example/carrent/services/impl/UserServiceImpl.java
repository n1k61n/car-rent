package com.example.carrent.services.impl;

import com.example.carrent.dtos.user.UserDto;
import com.example.carrent.dtos.user.UserProfileDto;
import com.example.carrent.dtos.user.UserRegistrationDto;
import com.example.carrent.dtos.user.UsersDashboardDto;
import com.example.carrent.enums.Role;
import com.example.carrent.models.User;
import com.example.carrent.repositories.UserRepository;
import com.example.carrent.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public List<UsersDashboardDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            return List.of();
        }
        return users.stream().map(user -> modelMapper.map(user, UsersDashboardDto.class)).toList();
    }

    @Override
    public Page<UsersDashboardDto> findPaginated(int pageNo, int pageSize, String keyword) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        if (keyword != null && !keyword.isEmpty()) {
            return userRepository.findAllByKeyword(keyword, pageable).map(user -> modelMapper.map(user, UsersDashboardDto.class));
        }
        return userRepository.findAll(pageable).map(user -> modelMapper.map(user, UsersDashboardDto.class));
    }

    @Override
    public long countAll() {
        return userRepository.count();
    }


    @Override
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}