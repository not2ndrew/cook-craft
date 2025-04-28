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
        System.out.println(userRequest);

        // For now, go to main html. I will create a message in the register page if the message is successful
        // Don't add anything to the database (Since it can only application.properties can only update. Not create-drop)

        if (userService.userExist(userRequest)) {
            model.addAttribute("error", true);
            return "register";
        } else {
            // TO DO: Add a success message if the User successfully created an account.
            userService.save(userRequest);
            return "main";
        }
    }
}
