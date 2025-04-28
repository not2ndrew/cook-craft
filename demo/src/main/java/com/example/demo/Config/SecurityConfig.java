package com.example.demo.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.Service.UserService;

/* Use this as a point of reference from video 31 - 34 */
// https://www.youtube.com/watch?v=8QDORHQvdu8

/* I have used this website for security */
// https://www.geeksforgeeks.org/spring-security-role-based-authentication/

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(request -> {
                request.requestMatchers("/users").hasRole("ADMIN");
                request.requestMatchers("/home/**").hasAnyRole("USER", "ADMIN", "MANAGER");
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
            .sessionManagement(session -> session.maximumSessions(1))
            .build();
    }

    // https://www.youtube.com/watch?v=L9oWG6aj_U8
    // Timestamp: 1:10:00
    // I need to override the current DetailsService to use my repository instead of UserDetails.
    // https://www.geeksforgeeks.org/spring-security-authentication-providers/
    // https://www.youtube.com/watch?v=qs91V059u68

    // Debating on if I should separate the User from UserDetails.
    // https://www.youtube.com/watch?v=tDZPdovCH4I

    // TO DO: Create an Error 403 Page
    // This is a fantastic page. Use that, or grab some meme on google images.
    // https://codepen.io/marianab/pen/EedpEb


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(userService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
}
