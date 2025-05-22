package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Recipe;
import com.example.demo.Entity.User;
import com.example.demo.Enum.RecipeType;


public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    List<Recipe> findByUser(User user);
    List<Recipe> findByRecipeType(RecipeType recipeType);
}
