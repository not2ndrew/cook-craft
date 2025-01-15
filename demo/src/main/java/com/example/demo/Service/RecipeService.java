package com.example.demo.Service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.demo.Dto.RecipeDto;
import com.example.demo.Dto.UserDto;
import com.example.demo.Entity.Recipe;
import com.example.demo.Repository.RecipeRepository;
import com.example.demo.Request.RecipeRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public void addRecipeById(UserDto userDto, RecipeRequest recipeRequest) {
        int id = recipeRequest.getId();
        boolean exist = recipeRepository.existsById(id);

        if (exist) {
            throw new RuntimeException("ID: " + id + " already exist.");
        }

        Recipe recipe = new Recipe(
            id, 
            recipeRequest.getTitle(), 
            0, 
            0, 
            new ArrayList<String>(), 
            new ArrayList<String>()
        );

        /* Recipe is also added onto the User's List */
        /* THIS CAUSES A STACKOVERFLOW ERROR!!! 
         * Make sure to use Lombok's ToString(exclude) feature in the Entity classes 
         * OR override ToString and get the Id of the variable's list. 
        */

        // userDto.getUserRecipes().add(recipe);
        recipeRepository.save(recipe);
    }

    public void deleteRecipeById(RecipeDto recipeDto) {
        int id = recipeDto.getId();
        boolean exist = recipeRepository.existsById(id);

        if (exist) {
            recipeRepository.deleteById(id);
        } else {
            throw new RuntimeException("ID: " + id + " does not exist.");
        }
    }
}
