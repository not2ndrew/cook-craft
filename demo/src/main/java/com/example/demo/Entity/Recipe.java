package com.example.demo.Entity;

import java.util.List;

import com.example.demo.Enum.RecipeType;

import jakarta.persistence.CascadeType;
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
    private int ratings;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private RecipeType recipeType;
    
    private List<String> listOfSteps;
    private List<String> listOfIngredients;

    @OneToMany(mappedBy = "recipe", cascade = CascadeType.ALL)
    private List<Comment> listOfComments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Recipe(int id, int ratings, String title, String description, RecipeType recipeType, List<String> listOfSteps, List<String> listOfIngredients, List<Comment> listOfComments) {
        this.id = id;
        this.ratings = ratings;
        this.title = title;
        this.description = description;
        this.recipeType = recipeType;
        this.listOfSteps = listOfSteps;
        this.listOfIngredients = listOfIngredients;
        this.listOfComments = listOfComments;
    }

    public Recipe(int ratings, String title, String description, RecipeType recipeType, List<String> listOfSteps, List<String> listOfIngredients, List<Comment> listOfComments) {
        this.ratings = ratings;
        this.title = title;
        this.description = description;
        this.recipeType = recipeType;
        this.listOfSteps = listOfSteps;
        this.listOfIngredients = listOfIngredients;
        this.listOfComments = listOfComments;
    }
}
