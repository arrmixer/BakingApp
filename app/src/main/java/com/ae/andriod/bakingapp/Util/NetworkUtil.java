package com.ae.andriod.bakingapp.Util;


import android.util.Log;

import com.ae.andriod.bakingapp.model.Ingredient;
import com.ae.andriod.bakingapp.model.Recipe;
import com.ae.andriod.bakingapp.model.Step;


import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtil {

    public static final String TAG = NetworkUtil.class.getSimpleName();

    public static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    public static  List<Recipe> fetchRecipes(){

         final List<Recipe> recipes = new ArrayList<>();


        OkHttpClient client = new OkHttpClient();

        Retrofit mRetrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeService service = mRetrofit.create(RecipeService.class);

        Call<List<RecipeResponse>> call = service.listRecipes();

        call.enqueue(new Callback<List<RecipeResponse>>() {
            @Override
            public void onResponse(Call<List<RecipeResponse>> call, Response<List<RecipeResponse>> response) {
                List<RecipeResponse> recipeResponses = response.body();

//                Log.i(TAG, recipeResponses.get(0).mIngredient.toString());
//                Log.i(TAG, "" + recipeResponses.get(0).mIngredient.size());
//                Log.i(TAG, recipeResponses.get(0).step.toString());
//                Log.i(TAG, "" + recipeResponses.get(0).step.size());

                for(RecipeResponse r : recipeResponses){
                  Recipe recipe =  makeRecipe(r);

                  recipes.add(recipe);
                }





                Log.i(TAG, "Recipes total: first " + recipes.size());


            }

            @Override
            public void onFailure(Call<List<RecipeResponse>> call, Throwable t) {

                Log.i(TAG, "Didn't work: " + t.getMessage());
            }
        });

        Log.i(TAG, "Recipes total: second" + recipes.size());
        return recipes;
    }

    //helper method to transfer response data to Recipe model
    private static Recipe makeRecipe( RecipeResponse recipeResponse){
        int id = recipeResponse.getId();
        String name = recipeResponse.getName();
        int servings = recipeResponse.getServings();
        String image = recipeResponse.getImage();

        List<Ingredient> ingredients = recipeResponse.mIngredient;
        List<Step> steps = recipeResponse.step;

        Recipe recipe = new Recipe(id, name, servings, image, ingredients, steps);


        return recipe;
    }



}
