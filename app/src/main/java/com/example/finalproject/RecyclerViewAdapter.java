package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<String> mImageIds = new ArrayList<>();
    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private Context mContext;


    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private RecipeSearchService recipeService = retrofit.create(RecipeSearchService.class);

    public RecyclerViewAdapter(Context context, ArrayList<String> imageIds, ArrayList<String> imageNames, ArrayList<String> images){
        this.mImageIds = imageIds;
        this.mImageNames = imageNames;
        this.mImages = images;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {

        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(holder.image);

        holder.imageName.setText(mImageNames.get(position));

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipeId = mImageIds.get(position);


                Call<RecipeModel> call = recipeService.getRecipe(recipeId);
                call.enqueue(new Callback<RecipeModel>() {
                    @Override
                    public void onResponse(Call<RecipeModel> call, Response<RecipeModel> response) {
                        if(response.code() != 200){
                            Toast.makeText(mContext, "Search failed", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Intent recipeIntent = new Intent(mContext, RecipeActivity.class);
                        recipeIntent.putExtra("name", response.body().getName());
                        recipeIntent.putExtra("url", response.body().getImage());
                        recipeIntent.putExtra("totalTime", response.body().getTotalTime());
                        recipeIntent.putExtra("numOfServings", response.body().getNumberOfServings());
                        recipeIntent.putExtra("yield", response.body().getYield());
                        recipeIntent.putStringArrayListExtra("ingredientLines", response.body().getIngredientLines());
                        recipeIntent.putExtra("rating", response.body().getRating());
                        recipeIntent.putExtra("recipeUrl", response.body().getUrl());

                        mContext.startActivity(recipeIntent);


                    }

                    @Override
                    public void onFailure(Call<RecipeModel> call, Throwable t) {
                        Log.d("Failed", "Something failed", t);
                        Toast.makeText(mContext, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });

                Toast.makeText(mContext, recipeId, Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView imageName;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            this.image = itemView.findViewById(R.id.image);
            this.imageName = itemView.findViewById(R.id.imageName);
            this.parentLayout = itemView.findViewById(R.id.parent_layout);


        }
    }

}
