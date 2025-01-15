package com.example.forkful;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.forkful.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recipeRecyclerView;
    public BottomNavigationView navigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

//        Intent intent = new Intent(this, CreateRecipe.class);
//        startActivity(intent);


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
//        navigationBar = findViewById(R.id.navigationBar);
//
//         //Updated Listener for BottomNavigationView
//        navigationBar.setOnItemSelectedListener(menuItem -> {
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


        // get recipes from Firestore
        // add recipes to recipe adapter
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("recipes")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        ArrayList<Recipe> recipeList = new ArrayList<>();

                        for (DocumentSnapshot document : task.getResult()) {
                            if (document.exists()) {
                                // Map document to a Recipe object
                                Recipe recipe = new Recipe();
                                recipe.setName(document.getString("name"));
                                recipe.setDescription(document.getString("description"));
                                recipe.setCategory(document.getString("category"));
                                recipe.setDuration(Integer.parseInt(document.getString("duration")));
                                recipe.setServingSize(Integer.parseInt(document.getString("servingSize")));
                                recipe.setCalories(Integer.parseInt(document.getString("calories")));
                                recipe.setDifficulty(document.getString("difficulty"));

                                // Retrieve lists for ingredients and directions
                                ArrayList<String> ingredients = (ArrayList<String>) document.get("ingredients");
                                ArrayList<String> directions = (ArrayList<String>) document.get("directions");

                                recipe.setIngredients(ingredients != null ? ingredients : new ArrayList<>());
                                recipe.setDirections(directions != null ? directions : new ArrayList<>());

                                recipeList.add(recipe);
                            }
                        }

                        // populate the adapter with the list of recipes
                        RecipeAdapter recipeAdapter = new RecipeAdapter(recipeList, this);
                        recipeRecyclerView.setAdapter(recipeAdapter);
                    } else {
                        Log.e("Firestore", "Error fetching recipes", task.getException());
                    }
                });

    }
}
