package com.example.demo.Entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private String title;
    /* LIKE is a reserved word in MySQL, use another word instead */
    // https://stackoverflow.com/questions/54504230/how-to-fix-error-executing-ddl-alter-table-events-drop-foreign-key-fkg0mkvgsqn
    private int likeNum;
    private int dislikeNum;

    private List<String> listOfIngredients;
    private List<String> listOfComments;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Recipe(int id, String title, int likeNum, int dislikeNum, List<String> listOfIngredients, List<String> listOfComments) {
        this.id = id;
        this.title = title;
        this.likeNum = likeNum;
        this.dislikeNum = dislikeNum;
        this.listOfIngredients = listOfIngredients;
        this.listOfComments = listOfComments;
    }
}
