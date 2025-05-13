package com.example.demo.Entity;

import java.util.List;

import com.example.demo.Enum.RecipeType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private double ratingSum;
    private int numOfRating;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private RecipeType recipeType;
    
    @ElementCollection
    @CollectionTable(name = "recipe_instructions", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "instruction")
    private List<String> instructions;
    
    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ingredient> ingredients;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Recipe(double ratingSum, int numOfRating, String title, String description, RecipeType recipeType, List<String> instructions, List<Ingredient> ingredients, List<Comment> comments) {
        this.ratingSum = ratingSum;
        this.numOfRating = numOfRating;
        this.title = title;
        this.description = description;
        this.recipeType = recipeType;
        this.instructions = instructions;
        this.ingredients = ingredients;
        this.comments = comments;
    }
}
