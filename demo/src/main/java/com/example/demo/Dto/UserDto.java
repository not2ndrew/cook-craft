package com.example.demo.Dto;

import java.util.Set;

import com.example.demo.Entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Set<Role> role;
    private String email;
    private String password;
}
