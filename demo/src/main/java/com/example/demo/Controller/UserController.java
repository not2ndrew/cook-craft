package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Request.UserRequest;
import com.example.demo.Service.UserServiceImpl;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserRequest userRequest) {
        System.out.println(userRequest);

        // For now, go to main html. I will create a message in the register page if the message is successful
        // Don't add anything to the database (Since it can only application.properties can only update. Not create-drop)

        if (userService.userExist(userRequest)) {
            // return "redirect:/register?error";
            System.out.println("Test");
        }
        userService.save(userRequest);
        return "main";
        // return "redirect://register?success";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute UserRequest userRequest) {
        System.out.println("Home Page");
        return "main";
    }
}
