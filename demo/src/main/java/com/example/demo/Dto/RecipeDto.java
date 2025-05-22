package com.example.demo.Dto;

import com.example.demo.Enum.RecipeType;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {
    private int id;
    private double ratingSum;
    private int numOfRating;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private RecipeType recipeType;

    private String username;
}
