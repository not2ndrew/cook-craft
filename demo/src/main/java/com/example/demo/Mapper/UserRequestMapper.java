package com.example.demo.Mapper;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.example.demo.Entity.User;
import com.example.demo.Request.UserRequest;

@Service
public class UserRequestMapper implements Function<User, UserRequest> {

    @Override
    public UserRequest apply(User user) {
        return new UserRequest(
            user.getUsername(), 
            user.getFName(), 
            user.getLName(), 
            user.getEmail(), 
            user.getPassword(), 
            user.getDob(), 
            user.getRoles()
        );
    }

}
