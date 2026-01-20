package com.example.carrent.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
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
