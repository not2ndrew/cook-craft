package com.example.demo.Mapper;

import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.example.demo.Entity.Recipe;
import com.example.demo.Request.RecipeRequest;

@Service
public class RecipeRequestMapper implements Function<Recipe, RecipeRequest> {

    @Override
    public RecipeRequest apply(Recipe recipe) {
        return new RecipeRequest(
            recipe.getTitle(), 
            recipe.getDescription(), 
            recipe.getRecipeType()
        );
    }

}
