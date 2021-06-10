package com.example.finalproject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RecipeSearchService {

    @GET("api/search/{recipe}")
    Call<ResultModel> searchRecipes(@Path("recipe") String recipeName);

    @GET("api/recipe/{recipeId}")
    Call<RecipeModel> getRecipe(@Path("recipeId") String recipeId);

    @POST("api/login")
    Call<AuthResultModel> login(@Body UserModel user);

    @POST("api/signup")
    Call<AuthResultModel> signup(@Body UserModel user);

}
