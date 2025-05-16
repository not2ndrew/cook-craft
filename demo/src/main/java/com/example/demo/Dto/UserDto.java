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
    private int id;
    private String username;
    private String email;
    private String fName;
    private String lName;
    private Set<Role> roles; 
}
