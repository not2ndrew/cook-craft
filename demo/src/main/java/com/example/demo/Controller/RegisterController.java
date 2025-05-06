package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Request.UserRequest;
import com.example.demo.Service.UserServiceImpl;

@Controller
public class RegisterController {
    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userRequest", new UserRequest());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserRequest userRequest, Model model) {
        try {
            if (userService.userExist(userRequest)) {
                model.addAttribute("error", "An account with this email already exists");
                return "register";
            }

            userService.save(userRequest);
            model.addAttribute("success", "Account Successfully created. You may now log in");
            return "register";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Something went wrong: " + e.getMessage());
            return "register";
        }
    }
}
