package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Entity.Comment;
import com.example.demo.CustomClass.Ingredient;
import com.example.demo.Entity.Recipe;
import com.example.demo.Entity.User;
import com.example.demo.Enum.RecipeType;
import com.example.demo.Repository.RecipeRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Request.RecipeRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RecipeService {
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    private final String emailError = "Email is Not Found.";
    private final String recipeError = "Recipe is Not Found";

    public Recipe getRecipeById(int id) {
        Recipe recipe = recipeRepository.findById(id)
            .orElseThrow(() -> 
                new RuntimeException(recipeError)
            );
        return recipe;
    }

    public void save(String email, RecipeRequest recipeRequest, List<Ingredient> listOfIngredients, List<String> listOfInstructions) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> 
            new RuntimeException(emailError)
        );

        Recipe recipe = new Recipe(
            0, 
            0, 
            recipeRequest.getTitle(), 
            recipeRequest.getDescription(), 
            recipeRequest.getRecipeType(), 
            listOfInstructions, 
            listOfIngredients, 
            new ArrayList<Comment>(0)
        );

        recipe.setUser(user);

        user.getUserRecipes().add(recipe);

        recipeRepository.save(recipe);
    }

    public List<Recipe> getAllRecipeByType(RecipeType type) {
        List<Recipe> output = new ArrayList<>();
        List<Recipe> listOfRecipes = recipeRepository.findAll();

        for (Recipe recipe : listOfRecipes) {
            if (recipe.getRecipeType().equals(type)) {
                output.add(recipe);
            }
        }

        return output;
    }
}
