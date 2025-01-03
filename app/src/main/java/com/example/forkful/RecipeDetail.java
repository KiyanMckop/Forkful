package com.example.forkful;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class RecipeDetail extends AppCompatActivity {

    private TextView recipeTitle, recipeDescription;
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

        // Get the recipe data from the Intent
        Intent intent = getIntent();
        String name = intent.getStringExtra("recipe_name");
        String description = intent.getStringExtra("recipe_description");
        String imageUrl = intent.getStringExtra("recipe_image");
        boolean isFavorite = intent.getBooleanExtra("is_favorite", false);

        // Set the data to the views
        recipeTitle.setText(name);
        recipeDescription.setText(description);
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
