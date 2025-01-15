package com.example.forkful;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private List<Recipe> recipeList;
    private Context context;

    public RecipeAdapter(List<Recipe> recipeList, Context context) {
        this.recipeList = recipeList;
        this.context = context;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        //get drawable sandwich.png
        int imageResource = context.getResources().getIdentifier("sandwich", "drawable", context.getPackageName());

        Recipe recipe = recipeList.get(position);
        holder.recipeName.setText(recipe.getName());
        //holder.recipeDescription.setText(recipe.getDescription());
        holder.recipeImage.setImageResource(imageResource);


        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RecipeDetail.class);
            intent.putExtra("recipe_name", recipe.getName());
            intent.putExtra("recipe_description", recipe.getDescription());
            intent.putExtra("recipe_image", recipe.getImageUrl());
            intent.putExtra("is_favorite", holder.isFavorite);
            intent.putExtra("recipe_duration", recipe.getDuration());
            intent.putExtra("recipe_serving_size", recipe.getServingSize());
            intent.putExtra("recipe_calories", recipe.getCalories());
            intent.putExtra("recipe_difficulty", recipe.getDifficulty());

            ArrayList<String> ingredients = recipe.getIngredients();
            ArrayList<String> directions = recipe.getDirections();
//
            intent.putStringArrayListExtra("recipe_ingredients", ingredients);
            intent.putStringArrayListExtra("recipe_directions", directions);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView recipeName, recipeDescription, recipeDuration, recipeServingSize, recipeCalories, recipeDifficulty;
        ImageView recipeImage, recipeFavorite;
        boolean isFavorite = false;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            recipeName = itemView.findViewById(R.id.recipeName);
            recipeImage = itemView.findViewById(R.id.recipeImage);
            recipeDescription = itemView.findViewById(R.id.recipeDescription);
            recipeFavorite = itemView.findViewById(R.id.recipeFavorite);
            recipeFavorite.setOnClickListener(v -> toggleFavorite());
            recipeDuration = itemView.findViewById(R.id.recipeDuration);
            recipeServingSize = itemView.findViewById(R.id.recipeServingSize);
            recipeCalories = itemView.findViewById(R.id.recipeCalories);
            recipeDifficulty = itemView.findViewById(R.id.recipeDifficulty);
        }

        private void toggleFavorite() {
            if (isFavorite) {
                recipeFavorite.setImageResource(R.drawable.ic_favorite_filled);  // Empty heart
            } else {
                recipeFavorite.setImageResource(R.drawable.ic_favorite);  // Filled heart
            }
            isFavorite = !isFavorite;  // Toggle the state
        }
    }
}

