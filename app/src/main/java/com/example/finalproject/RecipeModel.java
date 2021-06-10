package com.example.finalproject;

import java.util.ArrayList;

public class RecipeModel {

    private String totalTime;
    private ArrayList<String> ingredientLines;
//    private String attribution;
    private String name;
    private String rating;
    private String numberOfServings;
    private String yield;
    private String image;
    private String url;

    public String getUrl() {
        return url;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public ArrayList<String> getIngredientLines() {
        return ingredientLines;
    }

//    public String getAttribution() {
//        return attribution;
//    }

    public String getName() {
        return name;
    }

    public String getRating() {
        return rating;
    }

    public String getNumberOfServings() {
        return numberOfServings;
    }

    public String getYield() {
        return yield;
    }

    public String getImage() {
        return image;
    }

}
