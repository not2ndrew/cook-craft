package com.example.demo.Controller;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Request.UserRequest;
import com.example.demo.Service.UserServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final UserServiceImpl userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String logout, 
                            @RequestParam(required = false) String error, 
                            Model model) {
        if (logout != null) {
            model.addAttribute("logout", true);
        }

        if (error != null) {
            model.addAttribute("error", true);
        }
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute UserRequest userRequest, RedirectAttributes redirect) {
        try {
            // We want to check if the email is correct or incorrect
            UserDetails user = userService.loadUserByUsername(userRequest.getEmail());

            if (!passwordEncoder.matches(userRequest.getPassword(), user.getPassword())) {
                redirect.addAttribute("error", true);
                return "redirect:/login";
            }  
        } catch (RuntimeException e) {
            redirect.addAttribute("error", true);
            return "redirect:/login";
        }

        return "redirect:/main";
    }
}
