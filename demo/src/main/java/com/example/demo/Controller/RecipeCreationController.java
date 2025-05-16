package com.example.demo.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Entity.Ingredient;
import com.example.demo.Entity.User;
import com.example.demo.Enum.RecipeType;
import com.example.demo.Request.RecipeRequest;
import com.example.demo.Service.RecipeService;
import com.example.demo.Service.UserServiceImpl;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class RecipeCreationController {
    private final UserServiceImpl userService;
    private final RecipeService recipeService;

    
    @GetMapping("/home/recipe")
    public String recipe(Model model) {
        List<String> metricUnit = new ArrayList<>();
        metricUnit.add("Milliliters");
        metricUnit.add("Liters");
        metricUnit.add("Milligrams");
        metricUnit.add("Grams");

        model.addAttribute("recipeRequest", new RecipeRequest());
        model.addAttribute("recipeType", RecipeType.values());
        model.addAttribute("units", metricUnit);
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
            User user = userService.getLoggedInUser();
            List<Ingredient> ingredients = new ArrayList<>();

            for (int i = 0; i < names.size(); i++) {
                Ingredient ingredient = new Ingredient(
                    names.get(i), 
                    units.get(i), 
                    amounts.get(i)
                );
    
                ingredients.add(ingredient);
            }

            recipeService.save(user, recipeRequest, ingredients, instructions);
            return "home";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Something went wrong: " + e.getMessage());
            e.printStackTrace();
            return "recipe/createRecipe";
        }

    }
}
