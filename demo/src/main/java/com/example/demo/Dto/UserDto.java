package com.example.demo.Dto;

import java.util.Collection;

import com.example.demo.Enum.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Collection<Role> role;
    private String email;
    private String password;
}
