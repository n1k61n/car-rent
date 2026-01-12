package com.example.carrent.services.impl;

import com.example.carrent.dtos.user.*;
import com.example.carrent.enums.Role;
import com.example.carrent.models.Booking;
import com.example.carrent.models.Car;
import com.example.carrent.models.Otp;
import com.example.carrent.models.User;
import com.example.carrent.repositories.BookingRepository;
import com.example.carrent.repositories.CarRepository;
import com.example.carrent.repositories.UserRepository;
import com.example.carrent.services.EmailService;
import com.example.carrent.services.OtpService;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final BookingRepository bookingRepository;
    private final CarRepository carRepository;
    private final OtpService otpService;
    private final EmailService emailService;


    @Override
    @Transactional
    public boolean registerNewUser(UserRegistrationDto registrationDto) {
        try {

            User user = modelMapper.map(registrationDto, User.class);
            user.setPassword(passwordEncoder.encode(registrationDto.getPassword()));

            Role userRole = userRepository.count() == 0 ? Role.ADMIN : Role.USER;
            user.setRole(userRole);

            String otp = otpService.generateOTP();

            emailService.sendOtpEmail(registrationDto.getEmail(), otp);
            System.out.println("OTP: " + otp);

            Otp otpEntity = new Otp(registrationDto.getEmail(), otp);
            boolean saved = otpService.saved(otpEntity);
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
    @Transactional
    public boolean updateProfile(String currentEmail, UserProfileUpdateDto dto) {
        User user = userRepository.findByEmail(currentEmail)
                .orElseThrow(() -> new RuntimeException("İstifadəçi tapılmadı!"));

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhoneNumber());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(dto.getPassword());
            user.setPassword(encodedPassword);
        }

        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional
    public boolean deleteBooking(Long id, String email) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sifariş tapılmadı!"));

        if (!booking.getUser().getEmail().equals(email)) {
            throw new RuntimeException("Bu sifarişi silmək icazəniz yoxdur!");
        }

        Car car = booking.getCar();
        if (car != null) {
            car.setAvailable(true);
            carRepository.save(car);
        }

        bookingRepository.delete(booking);

        return true;
    }

    @Override
    public UsersDashboardDto findById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return modelMapper.map(user, UsersDashboardDto.class);
    }

    @Override
    public void toggleUserStatus(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(!user.isEnabled());
        userRepository.save(user);
    }


    @Override
    public void enableUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(true);
        user.setCredentialsNonExpired(true);
        user.setAccountNonExpired(true);
        user.setAccountNonLocked(true);
        userRepository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
