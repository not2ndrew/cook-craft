package com.example.demo.Controller;

import java.util.ArrayList;
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

import com.example.demo.CustomClass.Ingredient;
import com.example.demo.Enum.MetricUnit;
import com.example.demo.Enum.RecipeType;
import com.example.demo.Request.RecipeRequest;
import com.example.demo.Service.RecipeService;


@Controller
public class RecipeCreationController {
    @Autowired
    private RecipeService recipeService;

    @GetMapping("/home/recipe")
    public String recipe(Model model) {
        model.addAttribute("recipeRequest", new RecipeRequest());
        model.addAttribute("recipeType", RecipeType.values());
        model.addAttribute("units", MetricUnit.values());
        return "recipe/createRecipe";
    }

    @PostMapping("/recipe")
    public String createRecipe(@ModelAttribute RecipeRequest recipeRequest, 
                                @RequestParam("ingredientName") List<String> names, 
                                @RequestParam("ingredientUnit") List<String> units, 
                                @RequestParam("ingredientAmount") List<Double> amounts, 
                                @RequestParam("instruction") List<String> instructions, 
                                Model model) {

        try {
            String email = getCurrentEmail();
            List<Ingredient> ingredients = new ArrayList<>();

            for (int i = 0; i < names.size(); i++) {
                Ingredient ingredient = new Ingredient(
                    names.get(i), 
                    MetricUnit.valueOf(units.get(i)), 
                    amounts.get(i)
                );

                ingredients.add(ingredient);
            }

            recipeService.save(email, recipeRequest, ingredients, instructions);
            return "home";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Something went wrong: " + e.getMessage());
            e.printStackTrace();
            return "recipe/createRecipe";
        }

    }

    private String getCurrentEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }
    
}
