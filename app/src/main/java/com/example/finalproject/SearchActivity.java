package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;
import android.widget.ViewFlipper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    private EditText query;
    private Button searchBtn;

    private ViewFlipper vFlipper;

    private int images[] = {R.drawable.breakfast, R.drawable.fastfood, R.drawable.dinner};

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private RecipeSearchService recipeService = retrofit.create(RecipeSearchService.class);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        query = (EditText) findViewById(R.id.query);
        searchBtn = (Button) findViewById(R.id.search);

        vFlipper  = (ViewFlipper) findViewById(R.id.v_flipper);

        for(int image: images){
            flipperImages(image);
        }

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String recipeName = query.getText().toString();

                Call<ResultModel> call = recipeService.searchRecipes(recipeName);
                call.enqueue(new Callback<ResultModel>() {
                    @Override
                    public void onResponse(Call<ResultModel> call, Response<ResultModel> response) {
                        if(response.code() != 200){
                            Toast.makeText(getApplicationContext(), "Search failed", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

                        Intent resultIntent = new Intent(getApplicationContext(), ResultActivity.class);

                        resultIntent.putStringArrayListExtra("id", response.body().getId());
                        resultIntent.putStringArrayListExtra("name", response.body().getName());
                        resultIntent.putStringArrayListExtra("url", response.body().getUrl());

                        startActivity(resultIntent);
                    }

                    @Override
                    public void onFailure(Call<ResultModel> call, Throwable t) {
                        Log.d("Failed", "Something failed", t);
                        Toast.makeText(getApplicationContext(), "Fail", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

    }

    public void flipperImages(int image){
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setBackgroundResource(image);

        vFlipper.addView(imageView);
        vFlipper.setFlipInterval(4000);
        vFlipper.setAutoStart(true);

        // Animation
        vFlipper.setInAnimation(getApplicationContext(), android.R.anim.slide_in_left);
        vFlipper.setOutAnimation(getApplicationContext(), android.R.anim.slide_out_right);

    }
}