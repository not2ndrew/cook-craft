package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.Dto.UserDto;
import com.example.demo.Entity.Role;
import com.example.demo.Enum.RoleName;
import com.example.demo.Repository.RoleRepository;
import com.example.demo.Service.UserServiceImpl;

import lombok.RequiredArgsConstructor;

import static com.example.demo.Enum.RoleName.*;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;
    private final RoleRepository roleRepository;


    @GetMapping("/home/users")
    public String getUserDtos(Model model) {
        List<UserDto> users = new ArrayList<>();
        List<String> heading = new ArrayList<>();

        heading.add("ID");
        heading.add("Username");
        heading.add("Email");
        heading.add("First Name");
        heading.add("Last Name");
        heading.add("Roles");

        try {
            boolean isManager = userService.getLoggedInUser()
                .getRoles()
                .stream()
                .anyMatch(role -> ROLE_MANAGER.equals(role.getName()));

            if (isManager) {
                users = userService.getAllUsersAndAdmins();
            } else {
                Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Role: " + ROLE_USER + " does not exist."));
                users = userService.getAllUsersByRole(userRole);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
        }

        model.addAttribute("heading", heading);
        model.addAttribute("users", users);

        return "ControlPanel/userList";
    }

}
