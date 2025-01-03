package com.example.forkful;

public class Recipe {
    private String name;
    private String description;
    private String imageUrl;

    // Constructor, getters and setters
    public Recipe(String name, String description, String imageUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
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
}
