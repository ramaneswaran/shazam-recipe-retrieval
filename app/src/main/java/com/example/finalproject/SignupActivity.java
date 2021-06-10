package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button loginBtn;

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private RecipeSearchService recipeService = retrofit.create(RecipeSearchService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        email = (EditText) findViewById(R.id.emailSignup);
        password = (EditText) findViewById(R.id.passwordSignup);
        loginBtn = (Button) findViewById(R.id.signupButton);



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailString = email.getText().toString();
                String passwordString = password.getText().toString();

                UserModel user = new UserModel(emailString, passwordString);

                signupUser(user);

            }
        });


    }

    private void signupUser(UserModel user){

        Call<AuthResultModel> call = recipeService.signup(user);
        call.enqueue(new Callback<AuthResultModel>() {
            @Override
            public void onResponse(Call<AuthResultModel> call, Response<AuthResultModel> response) {
                if(response.code() != 200){
                    Toast.makeText(getApplicationContext(), "Server Error", Toast.LENGTH_SHORT).show();
                    return;
                }

                String valid = response.body().getValid();

                if(valid.equals("true")){

                    Intent homeIntent = new Intent(getApplicationContext(), SearchActivity.class);
                    startActivity(homeIntent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Email already exists", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AuthResultModel> call, Throwable t) {
                Log.d("Failed", "Something failed", t);
                Toast.makeText(getApplicationContext(), "Action Failed", Toast.LENGTH_SHORT).show();

            }
        });

    }
}