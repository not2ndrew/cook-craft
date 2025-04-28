package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Entity.Comment;
import com.example.demo.Entity.Recipe;
import com.example.demo.Entity.User;
import com.example.demo.Repository.RecipeRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Request.RecipeRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RecipeService {
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    private final String emailError = "Email is not found.";

    public void save(String email, RecipeRequest recipeRequest, List<String> listOfIngredients, List<String> listOfInstructions) {
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> 
            new RuntimeException(emailError)
        );

        Recipe recipe = new Recipe(
            0, 
            recipeRequest.getTitle(), 
            recipeRequest.getDescription(), 
            recipeRequest.getRecipeType(), 
            listOfIngredients, 
            listOfInstructions, 
            new ArrayList<Comment>()
        );

        recipe.setUser(user);

        user.getUserRecipes().add(recipe);

        recipeRepository.save(recipe);
    }
}
