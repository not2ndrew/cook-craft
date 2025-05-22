package com.example.demo.Mapper;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.example.demo.Dto.RecipeDto;
import com.example.demo.Entity.Recipe;

@Service
public class RecipeDtoMapper implements Function<Recipe, RecipeDto> {

    @Override
    public RecipeDto apply(Recipe recipe) {
        String username = recipe.getUser() != null ? recipe.getUser().getUsername() : null;

        return new RecipeDto(
            recipe.getId(), 
            recipe.getRatingSum(), 
            recipe.getNumOfRating(), 
            recipe.getTitle(), 
            recipe.getDescription(), 
            recipe.getRecipeType(), 
            username
        );
    }

}
