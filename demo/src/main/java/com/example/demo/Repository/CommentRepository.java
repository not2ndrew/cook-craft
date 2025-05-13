package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Entity.Comment;
import com.example.demo.Entity.Recipe;
import com.example.demo.Entity.User;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    boolean existsByUserAndRecipe(User user, Recipe recipe);
}
