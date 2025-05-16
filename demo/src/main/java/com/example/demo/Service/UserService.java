package com.example.demo.Service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.example.demo.Request.UserRequest;

public interface UserService extends UserDetailsService {
    void save(UserRequest userRequest);
    boolean userExistByEmail(String email);
}
