package com.example.forkful;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private String name;
    private String description;
    private String category;
    private int duration;
    private int servingSize;
    private int calories;
    private String difficulty;
    private String imageUrl;

    private ArrayList<String> ingredients;

    private ArrayList<String> directions;

    // Constructor, getters and setters
    public Recipe(String name, String description, String imageUrl, String category, int duration,
                  int servingSize, int calories, String difficulty, ArrayList<String> ingredients,
                  ArrayList<String> directions) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
        this.duration = duration;
        this.servingSize = servingSize;
        this.calories = calories;
        this.difficulty = difficulty;
        this.ingredients = ingredients;
        this.directions = directions;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public int getDuration() {
        return duration;
    }

    public int getServingSize() {
        return servingSize;
    }

    public int getCalories() {
        return calories;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }
    public ArrayList<String> getDirections() {
        return directions;
    }
}
