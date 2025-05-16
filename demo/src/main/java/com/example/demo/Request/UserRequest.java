package com.example.demo.Request;

import java.time.LocalDate;
import java.util.Set;

import com.example.demo.Entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String username;
    private String fName;
    private String lName;
    private String email;
    private String password;
    private LocalDate dob;
    private Set<Role> role;
}
