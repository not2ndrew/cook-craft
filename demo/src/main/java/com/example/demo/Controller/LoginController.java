package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Request.UserRequest;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(@RequestParam(value = "logout", required = false) String logout, Model model) {
        if (logout != null) {
            model.addAttribute("logout", true);
        }
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute UserRequest userRequest) {
        return "main";
    }
}
