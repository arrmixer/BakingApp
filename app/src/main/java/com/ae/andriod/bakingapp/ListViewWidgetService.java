package com.ae.andriod.bakingapp;

import android.app.Application;
import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.app.AppLaunchChecker;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.ae.andriod.bakingapp.DB.AppDatabase;
import com.ae.andriod.bakingapp.DB.RecipeRepository;
import com.ae.andriod.bakingapp.Util.IngredientListSharedPreference;
import com.ae.andriod.bakingapp.ViewModel.RecipeViewModel;
import com.ae.andriod.bakingapp.model.Ingredient;
import com.ae.andriod.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;

import static com.ae.andriod.bakingapp.Util.IngredientListSharedPreference.PREF_RECIPE_ID;

public class ListViewWidgetService extends RemoteViewsService {

    public static final String EXTRA_WIDGET_RECIPE_ID = "com.ae.andriod.bakingapp.extra.widget.recipe.id";
    public static final String TAG = ListViewWidgetService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(TAG, "Ingredients list is " + IngredientListSharedPreference.getPrefIngredientsQuery(getApplicationContext()));
        return new AppWidgetListView(getApplication(), getApplicationContext());


    }


}

class AppWidgetListView implements RemoteViewsService.RemoteViewsFactory {
    public static final String TAG = ListViewWidgetService.class.getSimpleName();

    private List<Ingredient> ingredientArrayList;
    private Application application;
    private Context context;
    private String recipeName;


    //for use to open RecipeActivity
    private int recipeId;

    public AppWidgetListView(Application application, Context context) {
        this.application = application;
        this.context = context;
    }


    @Override
    public void onCreate() {
        Log.i(TAG, "OnCreate in ListViewWidgetService");
    }

    @Override
    public void onDataSetChanged() {

//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//
//        ingredientArrayList = IngredientListSharedPreference.getPrefIngredientsQuery(context);
//        recipeName = IngredientListSharedPreference.getPrefRecipeName(context);
//        recipeId = IngredientListSharedPreference.getPrefRecipeId(context);

        RecipeRepository rp = new RecipeRepository(application);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        int recipeId = preferences.getInt(PREF_RECIPE_ID, -1);


        Recipe recipe = rp.getRecipeFromDB(recipeId);

        ingredientArrayList = recipe.getIngredients();
        recipeName = recipe.getName();
//        recipeId = 1;











        Log.i(TAG, "Recipe Name: " + recipeName);
        Log.i(TAG, "Recipe ID: " + recipeId);
        Log.i(TAG, "Ingredients size: " + ingredientArrayList.size());
    }

    @Override
    public void onDestroy() {
        ingredientArrayList.clear();
    }

    @Override
    public int getCount() {
        Log.i(TAG, "getCount in ListViewWidgetService called " + ingredientArrayList.size());
        return ingredientArrayList.size();
    }


    @Override
    public RemoteViews getViewAt(int position) {

        Log.i(TAG, "getViewAt in ListViewWidgetService called " + position);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.list_item_widget);

        //get place values
        String quantity = Double.toString(ingredientArrayList.get(position).getQuantity());
        String measure = ingredientArrayList.get(position).getMeasure();
        String title = ingredientArrayList.get(position).getIngredient();

        views.setTextViewText(R.id.titleTextView, recipeName);
        views.setTextViewText(R.id.widget_ingredient_quantity, quantity);
        views.setTextViewText(R.id.widget_ingredient_measure, measure);
        views.setTextViewText(R.id.widget_ingredient_title, title);

        Intent recipeIdIntent = new Intent();
        recipeIdIntent.putExtra(ListViewWidgetService.EXTRA_WIDGET_RECIPE_ID, recipeId);
        views.setOnClickFillInIntent(R.id.parentView, recipeIdIntent);
        return views;


    }

    @Override
    public RemoteViews getLoadingView() {

        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
