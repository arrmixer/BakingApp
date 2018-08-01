package com.ae.andriod.bakingapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;


import com.ae.andriod.bakingapp.db.RecipeRepository;
import com.ae.andriod.bakingapp.model.Ingredient;
import com.ae.andriod.bakingapp.model.Recipe;
import com.ae.andriod.bakingapp.model.Step;

import java.util.List;

public class RecipeViewModel extends AndroidViewModel {

    private Recipe mRecipe;

    private final MutableLiveData<List<Recipe>> mLiveRecipeListData;
    private final MutableLiveData<Recipe> mLiveRecipeData;

    private final RecipeRepository mRecipeRepository;



    public RecipeViewModel(@NonNull Application application) {
        super(application);
        mRecipeRepository = new RecipeRepository(application);
        mLiveRecipeListData = new MutableLiveData<>();
        mLiveRecipeData = new MutableLiveData<>();

    }

    public void setLiveRecipeData(Recipe recipeData){
        mLiveRecipeData.setValue(recipeData);
    }

    public MutableLiveData<Recipe> getLiveRecipeData() {
        return mLiveRecipeData;
    }

    public void setLiveRecipeListData(List<Recipe> recipes){
        mLiveRecipeListData.setValue(recipes);
    }

    public MutableLiveData<List<Recipe>> getLiveRecipeListData() {
        return mLiveRecipeListData;
    }


    public void setRecipe(Recipe recipe) {
        mRecipe = recipe;
    }

    public String getName() {
        return mRecipe.getName();
    }

    public int getId() {
        return mRecipe.getId();
    }

    public int getServings() {
        return mRecipe.getServings();
    }

    //as of now JSON data is blank
    public String getImage() {
        return mRecipe.getImage();
    }

    public List<Ingredient> getIngredients() {
        return mRecipe.getIngredients();
    }

    public List<Step> getSteps() {
        return mRecipe.getSteps();
    }

    //DB Operations
    public void insertMovieDB(Recipe movie){

        mRecipeRepository.insertRecipeDB(movie);
    }

    public LiveData<List<Recipe>> getAllRecipes(){

        return mRecipeRepository.getAllFavoriteRecipes();
    }

    public void deleteRecipeDB(Recipe movie){
        mRecipeRepository.deleteRecipeDB(movie);
    }

    public Recipe getRecipeFromDB(int movieId){
        return mRecipeRepository.getRecipeFromDB(movieId);
    }

}
