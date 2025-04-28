package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Request.RecipeRequest;
import com.example.demo.Service.RecipeService;


@Controller
public class RecipeCreationController {
    @Autowired
    private RecipeService recipeService;

    @GetMapping("/home/recipe")
    public String recipe(Model model) {
        model.addAttribute("recipeRequest", new RecipeRequest());
        return "recipe/recipe";
    }

    @PostMapping("/recipe")
    public String createRecipe(@ModelAttribute RecipeRequest recipeRequest, @RequestParam("ingredient") List<String> listOfIngredients, 
                                @RequestParam("instruction") List<String> listOfInstructions) {
        String email = getCurrentEmail();
        recipeService.save(email, recipeRequest, listOfIngredients, listOfInstructions);
        return "home";
    }

    private String getCurrentEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
    
}
