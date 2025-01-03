package com.example.forkful;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forkful.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recipeRecyclerView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, CreateRecipe.class);
        startActivity(intent);

        // Initialize RecyclerView
        recipeRecyclerView = findViewById(R.id.recipeRecyclerView);

        // Calculate number of columns based on screen width
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int screenWidth = displayMetrics.widthPixels;
        int columnWidth = getResources().getDimensionPixelSize(R.dimen.recipe_item_width);
        int numberOfColumns = screenWidth / columnWidth;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, numberOfColumns);
        recipeRecyclerView.setLayoutManager(gridLayoutManager);


// Initialize Bottom Navigation
        bottomNavigationView = findViewById(R.id.navigationBar);

        // Updated Listener for BottomNavigationView
//        bottomNavigationView.setOnItemSelectedListener(menuItem -> {
//            switch (menuItem.getItemId()) {
//                case R.id.navigation_home:
//                    // Handle Home action
//                    // For example: display a Home fragment
//                    return true;
//
//                case R.id.navigation_favorites:
//                    // Handle Favorites action
//                    // For example: display a Favorites fragment
//                    return true;
//
//                case R.id.navigation_categories:
//                    // Handle Categories action
//                    // For example: display Categories fragment
//                    return true;
//
//                default:
//                    return false;
//            }
//        });

        // Initialize SearchView
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter recipes based on search text
                return true;
            }
        });

        // Set up RecyclerView with adapter
        List<Recipe> recipeList = new ArrayList<>();
        recipeList.add(new Recipe("Recipe 1", "A delicious sandwich with layers of fresh ingredients.", "image_url_1"));
        recipeList.add(new Recipe("Recipe 2", "A delicious sandwich with layers of fresh ingredients.", "image_url_2"));
        recipeList.add(new Recipe("Recipe 3", "A delicious sandwich with layers of fresh ingredients.", "image_url_3"));
        recipeList.add(new Recipe("Recipe 4", "A delicious sandwich with layers of fresh ingredients.", "image_url_4")); // Sample data
        RecipeAdapter recipeAdapter = new RecipeAdapter(recipeList, this);
        recipeRecyclerView.setAdapter(recipeAdapter);
    }
}
