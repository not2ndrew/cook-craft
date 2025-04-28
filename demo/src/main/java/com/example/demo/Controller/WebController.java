package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    @GetMapping("/")
    public String main() {
        return "main";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }
}
