package com.example.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.Service.UserService;

import lombok.RequiredArgsConstructor;

/* Use this as a point of reference from video 31 - 34 */
// https://www.youtube.com/watch?v=8QDORHQvdu8

/* I have used this website for security */
// https://www.geeksforgeeks.org/spring-security-role-based-authentication/

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(request -> {
                request.requestMatchers("/home/getAllUsers").hasAnyRole("MANAGER", "ADMIN");
                request.requestMatchers("/home/recipe").hasAnyRole("USER", "ADMIN", "MANAGER");
                request.anyRequest().permitAll();
            })
            .formLogin(formLogin -> {
                formLogin.loginPage("/login");
                formLogin.defaultSuccessUrl("/home");
                formLogin.permitAll();
            })
            .logout(logout -> {
                logout.logoutUrl("/logout");
                logout.logoutSuccessUrl("/login?logout");
            })
            .exceptionHandling(ex -> ex.accessDeniedPage("/error/403"))
            .sessionManagement(session -> session.maximumSessions(1))
            .build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }
}
