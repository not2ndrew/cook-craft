package com.example.demo.Dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeDto {
    private int id;
    private int likeNum;
    private int dislikeNum;
    private List<String> listOfIngredients;
    private List<String> listOfComments;
}
