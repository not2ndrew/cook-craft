package com.example.demo.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int ratings;
    private String title;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    private Recipe recipe;

    public Comment(int id, int ratings, String title, String description) {
        this.id = id;
        this.ratings = ratings;
        this.title = title;
        this.description = description;
    }
}
