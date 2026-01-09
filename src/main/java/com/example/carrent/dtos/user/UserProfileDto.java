package com.example.carrent.dtos.user;

import com.example.carrent.dtos.car.CarDto;
import com.example.carrent.enums.Role;
import com.example.carrent.models.Booking;
import lombok.Data;

import java.util.List;

@Data
public class UserProfileDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private String phoneNumber;
    private List<Booking> bookings;
    private CarDto car;
}
