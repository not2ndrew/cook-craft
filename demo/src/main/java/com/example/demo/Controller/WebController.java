package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.Request.UserRequest;

@Controller
public class WebController {
    @GetMapping("/")
    public String main() {
        return "main";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("userRequest", new UserRequest());
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
