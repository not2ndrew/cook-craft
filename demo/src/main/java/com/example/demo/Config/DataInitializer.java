package com.example.demo.Config;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.demo.Entity.Role;
import com.example.demo.Request.UserRequest;
import com.example.demo.Service.UserService;

import lombok.RequiredArgsConstructor;

import static com.example.demo.Enum.RoleName.*;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {
    private final UserService userService;

    @Bean
    public CommandLineRunner createAdmin() {
        List<UserRequest> list = new ArrayList<>();
        String adminEmail = "admin@email.com";
        String managerEmail = "manager@email.com";

        return args -> {
            UserRequest admin = new UserRequest(
                "testAdmin", 
                "Andrew", 
                "Ho", 
                adminEmail, 
                "1234", 
                LocalDate.of(2003, 07, 15), 
                new HashSet<Role>()

            );

            UserRequest manager = new UserRequest(
                "testManager", 
                "Brandon", 
                "Ho", 
                managerEmail, 
                "1234", 
                LocalDate.of(2002, 02, 24), 
                new HashSet<Role>()
            );

            admin.getRole().add(new Role(ROLE_ADMIN));
            manager.getRole().add(new Role(ROLE_MANAGER));

            list.add(admin);
            list.add(manager);

            for (UserRequest user : list) {
                if (!userService.userExistByEmail(user.getEmail())) {
                    userService.save(user);
                    System.out.println(user.getEmail() + " created");
                } else {
                    System.out.println(user.getEmail() + " already existed");
                }
            }
        };
    }
}
