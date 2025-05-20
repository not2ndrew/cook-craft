package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Dto.UserDto;
import com.example.demo.Entity.Role;
import com.example.demo.Entity.User;
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


    @GetMapping("/home/getAllUsers")
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
            UserDto currentUser = userService.getLoggedInUserDto();
            boolean isManager = currentUser
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

            model.addAttribute("currentUser", currentUser);
        } catch (RuntimeException e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
        }

        model.addAttribute("heading", heading);
        model.addAttribute("users", users);

        return "ControlPanel/userList";
    }

    @DeleteMapping("/home/deleteUser/{id}")
    @ResponseBody
    public ResponseEntity<Void> deleteUser(@PathVariable int id, Model model) {
        try {
            UserDto currentUser = userService.getLoggedInUserDto();
            UserDto userToDelete = userService.findUserById(id);

            if (currentUser == null) {
                return ResponseEntity.notFound().build();
            }

            if (currentUser.getEmail().equals(userToDelete.getEmail())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            userService.deleteUserById(id);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
