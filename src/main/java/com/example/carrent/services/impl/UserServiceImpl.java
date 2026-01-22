package com.example.carrent.services.impl;

import com.example.carrent.dtos.user.*;
import com.example.carrent.enums.Role;
import com.example.carrent.models.Otp;
import com.example.carrent.models.User;
import com.example.carrent.repositories.UserRepository;
import com.example.carrent.services.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final OtpService otpService;
    private final EmailService emailService;
    private final NotificationService notificationService;
    private final SendGridEmailService sendGridEmailService;


    @Override
    @Transactional
    public boolean registerNewUser(UserRegistrationDto registrationDto) {
        String normalizedEmail = registrationDto.getEmail().toLowerCase();
        log.info("Attempting to register new user with email: {}", normalizedEmail);
        try {
            User newUser = modelMapper.map(registrationDto, User.class);
            newUser.setEmail(normalizedEmail); // Use normalized email
            newUser.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

            Role userRole = userRepository.count() == 0 ? Role.ADMIN : Role.USER;
            newUser.setRole(userRole);

            String otp = otpService.generateOTP();
            sendGridEmailService.sendOtpEmail(normalizedEmail, "Doğrulama Kodu (OTP)", otp);
            log.info("OTP sent to email: {}", normalizedEmail);

            Otp otpEntity = new Otp(normalizedEmail, otp);
            otpService.saved(otpEntity);
            newUser.setVerificationCode(otp);
            User savedUser = userRepository.save(newUser);

            notificationService.createNotification(
                    "New user registered: " + savedUser.getFirstName(),
                    "/dashboard/user/index",
                    "USER"
            );
            log.info("User registered successfully: {}", savedUser.getEmail());
            return true;
        } catch (Exception e) {
            log.error("Error registering user: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public UserProfileDto findByEmail(String email) {
        String normalizedEmail = email.toLowerCase();
        log.debug("Finding user by email: {}", normalizedEmail);
        User user = userRepository.findByEmail(normalizedEmail).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return modelMapper.map(user, UserProfileDto.class);
    }

    @Override
    public Page<UsersDashboardDto> findPaginated(int pageNo, int pageSize, String keyword) {
        log.debug("Finding users paginated. Page: {}, Size: {}, Keyword: {}", pageNo, pageSize, keyword);
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
    @Transactional
    public boolean updateProfile(String currentEmail, UserProfileUpdateDto dto) {
        String normalizedEmail = currentEmail.toLowerCase();
        log.info("Updating profile for user: {}", normalizedEmail);
        User user = userRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new RuntimeException("İstifadəçi tapılmadı!"));

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(dto.getPassword());
            user.setPassword(encodedPassword);
        }

        userRepository.save(user);
        log.info("Profile updated successfully for user: {}", normalizedEmail);
        return true;
    }

    @Override
    public UsersDashboardDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return modelMapper.map(user, UsersDashboardDto.class);
    }

    @Override
    public void toggleUserStatus(Long id) {
        log.info("Toggling status for user ID: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
        log.info("User status toggled. New status: {}", user.isEnabled());
    }

    @Override
    public void enableUser(String email) {
        String normalizedEmail = email.toLowerCase();
        log.info("Enabling user: {}", normalizedEmail);
        User user = userRepository.findByEmail(normalizedEmail).orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        userRepository.save(user);
        log.info("User enabled: {}", normalizedEmail);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email.toLowerCase()).isPresent();
    }

    @Override
    @Transactional
    public void resetPasswordToRandom(String email) {
        log.info("Resetting password for user: {}", email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("İstifadəçi tapılmadı"));

        String newPassword = UUID.randomUUID().toString().substring(0, 8);

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        sendGridEmailService.sendOtpEmail(email, "Yeni Şifrəniz", newPassword);
        log.info("Password reset email sent to: {} - New password: {}", email, newPassword);
    }

    @Override
    public List<User> getRecentUsers() {
        return userRepository.findTop5ByOrderByCreatedAtDesc();
    }

    @Override
    @Transactional
    public void assignAdminRole(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("İstifadəçi tapılmadı"));

        if (user.getRole() != Role.ADMIN) {
            user.setRole(Role.ADMIN);
        } else {
            user.setRole(Role.USER);
        }

        userRepository.save(user);
    }
}