package com.example.demo.Service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.demo.Entity.Comment;
import com.example.demo.Entity.Recipe;
import com.example.demo.Entity.User;

import com.example.demo.Repository.CommentRepository;
import com.example.demo.Request.CommentRequest;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository comRes;

    public void createComment(User user, Recipe recipe, CommentRequest commentRequest) {
        Comment comment = new Comment(
            0, 
            0, 
            commentRequest.getRatings(), 
            commentRequest.getTitle(), 
            commentRequest.getDescription(), 
            LocalDate.now()
        );

        adjustRating(recipe, comment);
        recipe.getComments().add(comment);
        comment.setUser(user);
        comment.setRecipe(recipe);
        comRes.save(comment);
    }

    private void adjustRating(Recipe recipe, Comment comment) {
        double newRatingSum = roundTo1Decimal(recipe.getRatingSum() + comment.getRatings());
        recipe.setRatingSum(newRatingSum);
        recipe.setNumOfRating(recipe.getNumOfRating() + 1);
    }

    private double roundTo1Decimal(double value) {
        return Math.round(value * 10.0) / 10.0;
    }

    public boolean hasUserCommentOnRecipe(User user, Recipe recipe) {
        return comRes.existsByUserAndRecipe(user, recipe);
    }
}
