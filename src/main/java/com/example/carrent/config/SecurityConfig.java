package com.example.carrent.config;

import com.example.carrent.handler.CustomAuthenticationSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
                    auth.requestMatchers("/", "/front/**", "/blog/**", "/listing/**", "/forgot-password", "/verify-otp", "/auth/**", "/login/**", "/oauth2/**", "/register/**").permitAll();
                    auth.anyRequest().authenticated();
                })
                .formLogin(form -> {
                    form.loginPage("/login");
                    form.loginProcessingUrl("/login");
                    form.defaultSuccessUrl("/", false);
                    form.failureUrl("/login?error=true");
                    form.usernameParameter("email");
                    form.passwordParameter("password");
                    form.successHandler(customAuthenticationSuccessHandler);
                    form.permitAll();
                    form.failureHandler((request, response, exception) -> {
                        response.sendRedirect("/login?error=true");
                    });
                })
                .oauth2Login(oauth2 -> {
                    oauth2.loginPage("/login");
                    oauth2.defaultSuccessUrl("/", true);
                    oauth2.successHandler(customAuthenticationSuccessHandler);
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