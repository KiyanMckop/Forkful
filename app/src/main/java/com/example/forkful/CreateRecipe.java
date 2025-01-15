package com.example.forkful;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.Node;

public class CreateRecipe extends AppCompatActivity {

    EditText dialogDuration, dialogPeople, dialogCalories;
    Spinner dialogDifficulty, createCategories;
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

        createCategories = findViewById(R.id.createCategories);

        String[] foodCategories = {"Breakfast", "Lunch", "Dinner", "Dessert", "Snack", "Beverage"};

        ArrayAdapter<CharSequence> foodCategoryAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                foodCategories);
        foodCategoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        createCategories.setAdapter(foodCategoryAdapter);


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

    private void saveRecipe(){
        System.out.println("save");

        //handle storing all content to firebase storage
    }

    private void createListComponent(LinearLayout parent, String hintTag){

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