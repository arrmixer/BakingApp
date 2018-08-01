package com.ae.andriod.bakingapp.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.ae.andriod.bakingapp.model.Recipe;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class RecipeRepository {

    public static final String TAG = RecipeRepository.class.getSimpleName();

    private final RecipeDao mRecipeDao;
    private final LiveData<List<Recipe>> mAllFavoriteRecipes;
    private LiveData<Recipe> mRecipeLiveData;

    public RecipeRepository(Application application) {
        AppDatabase db = AppDatabase.getInstance(application);
        mRecipeDao = db.mRecipeDao();
        mAllFavoriteRecipes = db.mRecipeDao().loadAllRecipes();

    }

    public LiveData<List<Recipe>> getAllFavoriteRecipes() {
        return mAllFavoriteRecipes;
    }

    public void insertRecipeDB(Recipe recipe) {

        new insertAsyncTask(mRecipeDao).execute(recipe);
    }

    public void deleteRecipeDB(Recipe recipe) {
        new deleteAsyncTask(mRecipeDao).execute(recipe);
    }


    public Recipe getRecipeFromDB(int recipeId) {

        Recipe recipe = null;

        try {
            recipe = new getRecipeWithIdAsyncTask(mRecipeDao).execute(recipeId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        return recipe;
    }


    //all AysncTasks for the recipe queries

    private static class insertAsyncTask extends AsyncTask<Recipe, Void, Void> {

        private final RecipeDao mAsyncRecipeDao;

        insertAsyncTask(RecipeDao dao) {
            mAsyncRecipeDao = dao;
        }

        @Override
        protected Void doInBackground(final Recipe... params) {
            mAsyncRecipeDao.insertRecipe(params[0]);
            return null;
        }

    }

    private static class deleteAsyncTask extends AsyncTask<Recipe, Void, Void> {

        private final RecipeDao mAsyncRecipeDao;

        deleteAsyncTask(RecipeDao dao) {
            mAsyncRecipeDao = dao;
        }

        @Override
        protected Void doInBackground(final Recipe... params) {

            mAsyncRecipeDao.deleteRecipe(params[0]);
            return null;
        }
    }

    private static class getRecipeWithIdAsyncTask extends AsyncTask<Integer, Void, Recipe> {

        private final RecipeDao mAsyncRecipeDao;

        getRecipeWithIdAsyncTask(RecipeDao dao) {
            mAsyncRecipeDao = dao;
        }

        @Override
        protected Recipe doInBackground(final Integer... integers) {
            return mAsyncRecipeDao.loadRecipeById(integers[0]);
        }

    }

}
