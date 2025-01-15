package com.example.forkful;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateRecipe extends AppCompatActivity {

    //dialog variables
    EditText dialogDuration, dialogPeople, dialogCalories;

    //recipe variables
    EditText edtRecipeName, edtRecipeDescription;
    Spinner dialogDifficulty, spRecipeCategory;
    Button cancel, done, btnCancelRecipe, btnSaveRecipe, btnAddDirection, btnAddIngredient;

    ImageButton ibDuration, ibServing, ibCalories, ibDifficulty;

    LinearLayout llIngredients, llDirections;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_recipe);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_general, null);
        builder.setView(dialogView);

        String[] difficultyLevel = {"Easy", "Beginner", "Normal", "Intermediate", "Hard", "Expert"};

        dialogDuration = dialogView.findViewById(R.id.dialogDuration);
        dialogPeople = dialogView.findViewById(R.id.dialogPeople);
        dialogCalories = dialogView.findViewById(R.id.dialogCalories);
        dialogDifficulty = dialogView.findViewById(R.id.dialogDifficulty);

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                difficultyLevel);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dialogDifficulty.setAdapter(adapter);

        AlertDialog dialog = builder.create();

        ibDuration = findViewById(R.id.ibDuration);
        ibDuration.setOnClickListener(view -> dialog.show());

        ibServing = findViewById(R.id.ibServing);
        ibServing.setOnClickListener(view -> dialog.show());

        ibCalories = findViewById(R.id.ibCalories);
        ibCalories.setOnClickListener(view -> dialog.show());

        ibDifficulty = findViewById(R.id.ibDifficulty);
        ibDifficulty.setOnClickListener(view -> dialog.show());


        cancel = dialogView.findViewById(R.id.dialogCancel);
        done = dialogView.findViewById(R.id.dialogDone);

        cancel.setOnClickListener(view -> dialog.dismiss());
        done.setOnClickListener(view -> {
            dialog.dismiss();
            //handle storing all content to variables
        });

        spRecipeCategory = findViewById(R.id.spRecipeCategory);

        String[] foodCategories = {"Breakfast", "Lunch", "Dinner", "Dessert", "Snack", "Beverage"};

        ArrayAdapter<CharSequence> foodCategoryAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                foodCategories);
        foodCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spRecipeCategory.setAdapter(foodCategoryAdapter);


        //general components
        edtRecipeName = findViewById(R.id.edtRecipeTitle);
        edtRecipeDescription = findViewById(R.id.edtRecipeDescription);

        //handle saving and canceling recipes
        btnCancelRecipe = findViewById(R.id.btnCancelRecipe);
        btnCancelRecipe.setOnClickListener(view -> clearFields());

        btnSaveRecipe = findViewById(R.id.btnSaveRecipe);
        btnSaveRecipe.setOnClickListener(view -> saveRecipe());

        //handle adding ingredients and direction components
        llIngredients = findViewById(R.id.ingredientContainer);
        llDirections = findViewById(R.id.directionsContainer);

        btnAddDirection = findViewById(R.id.addDirectionButton);
        btnAddDirection.setOnClickListener(view -> createListComponent(llDirections, "Step"));

        btnAddIngredient = findViewById(R.id.addIngredientButton);
        btnAddIngredient.setOnClickListener(view -> createListComponent(llIngredients, "Ingredient"));

    }

    private void clearFields(){
        System.out.println("clearing fields");
    }

    public void saveRecipe(){
        if (!validateInputs()){
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Create a Map to store the recipe data
        Map<String, Object> recipeData = new HashMap<>();

        // store recipe component values to variables
        String recipeName = edtRecipeName.getText().toString();
        String recipeDescription = edtRecipeDescription.getText().toString();
        String recipeCategory = spRecipeCategory.getSelectedItem().toString();
        String recipeDuration = dialogDuration.getText().toString();
        String recipeServingSize = dialogPeople.getText().toString();
        String recipeCalories = dialogCalories.getText().toString();
        String recipeDifficulty = dialogDifficulty.getSelectedItem().toString();

        //add the recipe info to the map
        recipeData.put("name", recipeName);
        recipeData.put("description", recipeDescription);
        recipeData.put("category", recipeCategory);
        recipeData.put("duration", recipeDuration);
        recipeData.put("servingSize", recipeServingSize);
        recipeData.put("calories", recipeCalories);
        recipeData.put("difficulty", recipeDifficulty);

        // get ingredients and directions from dynamically created EditTexts
        ArrayList<String> ingredients = new ArrayList<>();
        for (int i = 0; i < llIngredients.getChildCount(); i++) {
            EditText ingredientEditText = (EditText) llIngredients.getChildAt(i);
            String ingredient = ingredientEditText.getText().toString();
            if (!ingredient.isEmpty()) {
                ingredients.add(ingredient);
            }
        }

        ArrayList<String> directions = new ArrayList<>();
        for (int i = 0; i < llDirections.getChildCount(); i++) {
            EditText directionEditText = (EditText) llDirections.getChildAt(i);
            String direction = directionEditText.getText().toString();
            if (!direction.isEmpty()) {
                directions.add(direction);
            }
        }

        //add ingredients and directions to the map
        recipeData.put("ingredients", ingredients);
        recipeData.put("directions", directions);

        //add recipe to firestore (uses unique id)
        db.collection("recipes")
                .add(recipeData) //unique id
                .addOnSuccessListener(documentReference -> {
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                    System.out.println("Recipe saved successfully with ID: " + documentReference.getId());
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error saving recipe", Toast.LENGTH_SHORT).show();
                    System.err.println("Error saving recipe: " + e.getMessage());
                });
    }

    private boolean validateInputs(){

        //validate dialog inputs
        if (dialogDuration.getText().toString().isEmpty() ||
            dialogPeople.getText().toString().isEmpty() ||
            dialogCalories.getText().toString().isEmpty() ||
            dialogDifficulty.getSelectedItem().toString().isEmpty()){
            return false;
        }

        //validate editText inputs
        if (edtRecipeDescription.getText().toString().isEmpty() ||
            edtRecipeName.getText().toString().isEmpty()){
            return false;
        }

        //validate spinner inputs
        if (spRecipeCategory.getSelectedItem().toString().isEmpty()){
            return false;
        }

        //validate list inputs
        if (llIngredients.getChildCount() == 0 || llDirections.getChildCount() == 0){
            return false;
        }

        return true;

    }

    private void createListComponent(LinearLayout parent, String hintTag){

        //create a new list component for the ingredients and directions sections

        EditText edtTextComponent = new EditText(this);
        edtTextComponent.setHint(hintTag + " " + (parent.getChildCount() + 1));
        edtTextComponent.setBackgroundResource(R.drawable.edit_background);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 20, 0, 20);
        edtTextComponent.setLayoutParams(params);

        parent.addView(edtTextComponent);

    }
}