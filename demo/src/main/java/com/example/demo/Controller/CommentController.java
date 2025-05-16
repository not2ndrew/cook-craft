package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.Entity.Recipe;
import com.example.demo.Entity.User;
import com.example.demo.Request.CommentRequest;
import com.example.demo.Service.CommentService;
import com.example.demo.Service.RecipeService;
import com.example.demo.Service.UserServiceImpl;

import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class CommentController {
    private final UserServiceImpl userService;
    private final RecipeService recipeService;
    private final CommentService commentService;


    @PostMapping("/comment/{id}")
    public String createComment(Model model, @PathVariable int id, @ModelAttribute CommentRequest commentRequest) {
        try {
            Recipe recipe = recipeService.getRecipeById(id);
            User user = userService.getLoggedInUser();

            commentService.createComment(user, recipe, commentRequest);

            return "redirect:/recipe/id=" + id;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return "redirect:/recipe/id=" + id;
        }
    }
}
