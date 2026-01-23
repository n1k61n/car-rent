package com.example.carrent.handler;


import com.example.carrent.enums.Role;
import com.example.carrent.models.User;
import com.example.carrent.repositories.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        if (authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            String email = oAuth2User.getAttribute("email");
            String name = oAuth2User.getAttribute("name");

            Optional<User> existingUser = userRepository.findByEmail(email);
            if (existingUser.isEmpty()) {
                // Yeni istifadəçi yaradılır
                User newUser = new User();
                newUser.setEmail(email);
                newUser.setFirstName(name);
                newUser.setPassword("");
                newUser.setRole(Role.USER);
                newUser.setEnabled(true);
                userRepository.save(newUser);
            }
        }

        var authorities = authentication.getAuthorities();

        boolean isAdmin = authorities.stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            response.sendRedirect("/dashboard");
            return;
        }

        HttpSession session = request.getSession(false);
        if (session != null) {
            Object savedRequest = session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
            if (savedRequest instanceof SavedRequest sr) {
                response.sendRedirect(sr.getRedirectUrl());
                return;
            }
        }

        response.sendRedirect("/");
    }
}
