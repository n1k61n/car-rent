package com.example.carrent.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        // Spring Security-nin daxili handler-indən istifadə edirik
        SavedRequestAwareAuthenticationSuccessHandler delegate = new SavedRequestAwareAuthenticationSuccessHandler();
        delegate.setDefaultTargetUrl("/");

        return (request, response, authentication) -> {
            var authorities = authentication.getAuthorities();

            // 1. ADMIN yoxlaması
            boolean isAdmin = authorities.stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

            if (isAdmin) {
                response.sendRedirect("/dashboard");
            } else {
                // 2. SavedRequest və ya Default URL-ə yönləndirməni Spring özü həll edir
                delegate.onAuthenticationSuccess(request, response, authentication);
            }
        };
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/dashboard/chat/history/**", "/dashboard/chat/active-sessions").permitAll();
                    auth.requestMatchers("/ws-chat/**").permitAll();
                    auth.requestMatchers("/dashboard/**").hasRole("ADMIN");
                    auth.requestMatchers("/*", "/front/**", "/blog/**", "/listing/**", "/forgot-password", "/verify-otp", "/auth/**").permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin(form -> {
                    form.loginPage("/login");
                    form.loginProcessingUrl("/login");
                    form.defaultSuccessUrl("/", false);
                    form.failureUrl("/login?error=true");
                    form.usernameParameter("email");
                    form.passwordParameter("password");
                    form.successHandler(customSuccessHandler());
                    form.permitAll();
                    form.failureHandler((request, response, exception) -> {
                        response.sendRedirect("/login?error=true");
                    });
                })
                .rememberMe(remember -> remember
                        .key("uniqueAndSecretKey")
                        .tokenValiditySeconds(604800) // 7 gün
                        .userDetailsService(userDetailsService)
                        .rememberMeParameter("remember-me")
                        .alwaysRemember(false)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID", "remember-me")
                        .permitAll()
                );
        return http.build();
    }

}