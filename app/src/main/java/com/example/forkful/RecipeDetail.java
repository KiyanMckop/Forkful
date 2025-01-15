package com.example.forkful;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetail extends AppCompatActivity {

    private TextView recipeTitle, recipeDescription,
            recipeIngredients, recipeDirections,
            recipeDuration, recipeServingSize,
            recipeCalories, recipeDifficulty;
    private ImageView recipeImage, recipeFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe_detail);

        // Initialize views
        recipeTitle = findViewById(R.id.recipeTitle);
        recipeDescription = findViewById(R.id.recipeDescription);
        recipeImage = findViewById(R.id.recipeImage);
        recipeFavorite = findViewById(R.id.recipeFavorite);
        recipeDuration = findViewById(R.id.recipeDuration);
        recipeServingSize = findViewById(R.id.recipeServingSize);
        recipeCalories = findViewById(R.id.recipeCalories);
        recipeDifficulty = findViewById(R.id.recipeDifficulty);
        recipeIngredients = findViewById(R.id.recipeIngredients);
        recipeDirections = findViewById(R.id.recipeDirections);


        // Get the recipe data from the Intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("recipe_name");
        String description = intent.getStringExtra("recipe_description");
        String imageUrl = intent.getStringExtra("recipe_image");
        String category = intent.getStringExtra("recipe_category");
        String duration = Integer.toString(intent.getIntExtra("recipe_duration", 0));
        String servingSize = Integer.toString(intent.getIntExtra("recipe_serving_size", 0));
        String calories = Integer.toString(intent.getIntExtra("recipe_calories", 0));
        String difficulty = intent.getStringExtra("recipe_difficulty");
        boolean isFavorite = intent.getBooleanExtra("is_favorite", false);

        ArrayList<String> ingredients = intent.getStringArrayListExtra("recipe_ingredients");
        ArrayList<String> directions = intent.getStringArrayListExtra("recipe_directions");

        for (String ingredient : ingredients) {
            recipeIngredients.append("&#8226;" + ingredient + "\n");
        }
        for (String direction : directions) {
            recipeDirections.append("&#8226;" + direction + "\n");
        }


        // Set the data to the views
        recipeTitle.setText(name);
        recipeDescription.setText(description);
        recipeDuration.setText(duration);
        recipeServingSize.setText(servingSize);
        recipeCalories.setText(calories);
        recipeDifficulty.setText(difficulty);
        toggleFavorite(isFavorite);

        // Use Glide or any image loading library to load the image
//        Glide.with(this)
//                .load(imageUrl)
//                .into(recipeImage);
    }

    private void toggleFavorite(boolean isFavorite) {
        if (isFavorite) {
            recipeFavorite.setImageResource(R.drawable.ic_favorite);  // Empty heart
        } else {
            recipeFavorite.setImageResource(R.drawable.ic_favorite_filled);  // Filled heart
        }
    }
}
