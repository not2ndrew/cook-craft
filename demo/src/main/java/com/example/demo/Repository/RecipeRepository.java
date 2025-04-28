package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Entity.Recipe;

public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
}
