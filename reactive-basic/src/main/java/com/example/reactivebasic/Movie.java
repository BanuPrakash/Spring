package com.example.reactivebasic;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Movie {
    @Id
    private String id;
    private String title;
    private int year;
    private String genre;
    private double imdbRating;
}
