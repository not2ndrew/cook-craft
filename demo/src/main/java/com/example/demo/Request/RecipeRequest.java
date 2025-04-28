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
}
