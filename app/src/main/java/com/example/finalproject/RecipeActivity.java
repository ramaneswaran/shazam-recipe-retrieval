package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity {

    private String name;
    private String imageUrl;
    private String numberOfServings;
    private ArrayList<String> ingredientLines;
    private float rating;
    private String totalTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        // Get the views
        TextView nameText = (TextView) findViewById(R.id.recipeName);
        TextView servingsText = (TextView) findViewById(R.id.numOfServings);
        TextView totalTimeText = (TextView) findViewById(R.id.totalTime);
        ImageView imageView = (ImageView) findViewById(R.id.recipeImage);
        TextView ingredientLinesText = (TextView) findViewById(R.id.ingredientLines);

        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        // Set recipe image
        imageUrl = getIntent().getStringExtra("url");

        Glide.with(getApplicationContext())
                .asBitmap()
                .load(imageUrl)
                .into(imageView);

        // Set the name
        nameText.setText(getIntent().getStringExtra("name"));

        // Set number of servings
        numberOfServings = "Number of Servings: "+getIntent().getStringExtra("numOfServings");
        servingsText.setText(numberOfServings);
        totalTime = "Total Time: "+getIntent().getStringExtra("totalTime");
        totalTimeText.setText(totalTime);

        // Set rating bar
        rating = Float.parseFloat(getIntent().getStringExtra("rating"));
        ratingBar.setRating(rating);


        // Ingredients
        ingredientLines = getIntent().getStringArrayListExtra("ingredientLines");
        StringBuilder ingredients = new StringBuilder("");

        for(String ingredient: ingredientLines) {
            ingredients.append(ingredient);
            ingredients.append("\n");
        }


        ingredientLinesText.setText(ingredients);

        // Recipe link
        Button recipeBtn = (Button) findViewById(R.id.recipeLink);

        recipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipeUrl = getIntent().getStringExtra("recipeUrl");

                Intent browseIntent = new Intent(Intent.ACTION_VIEW);
                browseIntent.setData(Uri.parse(recipeUrl));
                startActivity(browseIntent);
            }
        });


    }
}