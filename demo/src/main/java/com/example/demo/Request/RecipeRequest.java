package com.example.demo.Request;

import com.example.demo.Enum.RecipeType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeRequest {
    private String title;
    private String description;
    
    @Enumerated(EnumType.STRING)
    private RecipeType recipeType;

    // The List of Ingredients and List of Instructions are both handled separately by Javascript (see createTextInput.js)
}
