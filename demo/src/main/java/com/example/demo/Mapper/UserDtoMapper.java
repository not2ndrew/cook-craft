package com.example.demo.Mapper;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.example.demo.Dto.UserDto;
import com.example.demo.Entity.User;

@Service
public class UserDtoMapper implements Function<User, UserDto>{

    @Override
    public UserDto apply(User user) {
        return new UserDto(
            user.getRoles(), 
            user.getEmail(), 
            user.getPassword()
        );
    }

}
