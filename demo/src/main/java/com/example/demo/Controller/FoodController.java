package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.Dto.RecipeDto;
import com.example.demo.Entity.Recipe;
import com.example.demo.Entity.User;
import com.example.demo.Enum.RecipeType;
import com.example.demo.Request.CommentRequest;
import com.example.demo.Service.CommentService;
import com.example.demo.Service.RecipeService;
import com.example.demo.Service.UserServiceImpl;

import java.util.List;



@Controller
public class FoodController {
    private final UserServiceImpl userService;
    private final RecipeService recipeService;
    private final CommentService commentService;


    public FoodController(UserServiceImpl userService, RecipeService recipeService, CommentService commentService) {
        this.userService = userService;
        this.recipeService = recipeService;
        this.commentService = commentService;
    }

    
    @GetMapping("/home/{type}")
    public String foodPage(@PathVariable String type, Model model) {
        RecipeType recipeType;

        try {
            recipeType = RecipeType.valueOf(type.toUpperCase());
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "home";
        }

        model.addAttribute("title", type.toUpperCase());


        List<RecipeDto> recipes = recipeService.getAllRecipeByType(recipeType);

        if (recipes.isEmpty()) {
            model.addAttribute("empty", "There are no recipes in " + recipeType);
        } else {
            model.addAttribute("recipes", recipes);
        }


        return "recipe/genericRecipePage";
    }

    @GetMapping("/recipe/id={id}")
    public String recipePage(@PathVariable int id, Model model) {
        User user = null;
        
        try {
            user = userService.getLoggedInUser();
        } catch (RuntimeException e) {
            e.getMessage();
        }

        
        model.addAttribute("commentRequest", new CommentRequest());


        try {
            Recipe recipe = recipeService.getRecipeById(id);
            model.addAttribute("recipe", recipe);

            if (recipe.getComments().isEmpty()) {
                model.addAttribute("empty", true);
            }

            if (user != null) {
                boolean hasComment = commentService.hasUserCommentOnRecipe(user, recipe);
                boolean isAuthor = recipe.getUser().equals(user);
                model.addAttribute("hasComment", hasComment);
                model.addAttribute("isAuthor", isAuthor);
            } else {
                model.addAttribute("hasComment", false);
                model.addAttribute("isAuthor", false);
            }


            return "recipe/genericRecipe";

        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            e.printStackTrace();
            return "recipe/genericRecipe";
        }
    }
}
