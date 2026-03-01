package com.example.carrent.config;

import com.example.carrent.enums.Role;
import com.example.carrent.models.User;
import com.example.carrent.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Fix for sequence out of sync when manually inserting records
        try {
            entityManager.createNativeQuery(
                    "SELECT setval(pg_get_serial_sequence('categories', 'id'), coalesce(max(id), 1), max(id) IS NOT null) FROM categories;"
            ).getSingleResult();
        } catch (Exception e) {
            System.err.println("Could not sync categories sequence: " + e.getMessage());
        }

        if (!userRepository.existsByEmail("admin@example.com")) {
            User admin = new User();
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole(Role.ADMIN);
            admin.setEnabled(true);
            admin.setAccountNonExpired(true);
            admin.setAccountNonLocked(true);
            admin.setCredentialsNonExpired(true);
            
            userRepository.save(admin);
        }
    }
}
