package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.Entity.Recipe;
import com.example.demo.Enum.RecipeType;
import com.example.demo.Service.RecipeService;


import java.util.List;


@Controller
public class FoodController {
    @Autowired
    private RecipeService recipeService;

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
        List<Recipe> recipes = recipeService.getAllRecipeByType(recipeType);

        if (recipes.isEmpty()) {
            model.addAttribute("empty", "There are no recipes in " + recipeType);
        } else {
            model.addAttribute("recipes", recipes);
        }

        return "recipe/genericRecipePage";
    }

    @GetMapping("/recipe/{id}")
    public String recipePage(@PathVariable int id, Model model) {
        Recipe recipe = recipeService.getRecipeById(id);

        model.addAttribute("empty", recipe);
        return "recipe/genericRecipe";
    }
    
    
}
