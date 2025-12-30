package com.example.carrent.models;

import com.example.carrent.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//* String firstName
    private String firstName;
//* String lastName
    private String lastName;
//* String email
    private String email;
//* String password
    private String password;
//* String phone
    private String phone;
//* @ElementCollection roles (ADMIN, USER)
    private Role role;
//* @OneToMany List<Booking> bookings
    @OneToMany
    List<Booking> bookings;
}
