package com.example.demo.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Dto.RecipeDto;
import com.example.demo.Entity.Comment;
import com.example.demo.Entity.Ingredient;
import com.example.demo.Entity.Recipe;
import com.example.demo.Entity.User;
import com.example.demo.Enum.RecipeType;
import com.example.demo.Mapper.RecipeDtoMapper;
import com.example.demo.Repository.RecipeRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Request.RecipeRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final RecipeDtoMapper recipeDtoMapper;

    public Recipe getRecipeById(int id) {
        Recipe recipe = recipeRepository.findById(id)
            .orElseThrow(() -> 
                new RuntimeException("Recipe ID: " + id + " was not found.")
            );
        return recipe;
    }

    public void save(User user, RecipeRequest recipeRequest, List<Ingredient> listOfIngredients, List<String> listOfInstructions) {
        Recipe recipe = new Recipe(
            0, 
            0, 
            recipeRequest.getTitle(), 
            recipeRequest.getDescription(), 
            recipeRequest.getRecipeType(), 
            listOfInstructions, 
            new ArrayList<Ingredient>(), 
            new ArrayList<Comment>()
        );

        for (Ingredient ingredient : listOfIngredients) {
            ingredient.setRecipe(recipe);
            recipe.getIngredients().add(ingredient);
        }

        recipe.setUser(user);
        user.getUserRecipes().add(recipe);
        
        recipeRepository.save(recipe);
    }

    public List<RecipeDto> getAllRecipeByType(RecipeType type) {
        return recipeRepository.findByRecipeType(type)
            .stream()
            .map(recipeDtoMapper)
            .toList();
    }

    public User getUserByRecipeId(int id) {
        Recipe recipe = recipeRepository.findById(id)
            .orElseThrow(() -> 
                new RuntimeException("recipe ID: " + id + " is not be found.")
            );
        int userId = recipe.getUser().getId();

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User ID: " + userId + " is not found."));
        
            return user;
    }

    public List<RecipeDto> getRecipeList(User user) {
        return recipeRepository.findByUser(user)
            .stream()
            .map(recipeDtoMapper)
            .toList();
    }

    public void deleteRecipeById(int id) {
        Recipe recipe = recipeRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("recipe ID: " + id + "is not found."));
        
        getUserByRecipeId(id).getUserRecipes().remove(recipe);
        recipeRepository.delete(recipe);
    }
}
