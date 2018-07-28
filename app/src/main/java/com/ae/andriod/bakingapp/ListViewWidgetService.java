package com.ae.andriod.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.ae.andriod.bakingapp.Util.IngredientListSharedPreference;
import com.ae.andriod.bakingapp.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class ListViewWidgetService extends RemoteViewsService {

    public static final String EXTRA_WIDGET_RECIPE_ID = "com.ae.andriod.bakingapp.extra.widget.recipe.id";


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new AppWidgetListView(this.getApplicationContext(),
                IngredientListSharedPreference.getPrefIngredientsQuery(getApplicationContext()),
                IngredientListSharedPreference.getPrefRecipeName(getApplicationContext()),
                IngredientListSharedPreference.getPrefRecipeId(getApplicationContext()));
    }




}

class AppWidgetListView implements RemoteViewsService.RemoteViewsFactory {

    private List<Ingredient> ingredientArrayList;
    private Context context;
    private String recipeName;


    //for use to open RecipeActivity
    private int recipeId;

    public AppWidgetListView(Context context, List<Ingredient> ingredientArrayList, String recipeName, int recipeId) {
        this.ingredientArrayList = ingredientArrayList;
        this.context = context;
        this.recipeName = recipeName;
        this.recipeId = recipeId;

    }





    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {
        ingredientArrayList.clear();
    }

    @Override
    public int getCount() {
        return ingredientArrayList.size();
    }



    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.list_item_widget);

        //get place values
        String quantity = Double.toString(ingredientArrayList.get(position).getQuantity());
        String measure = ingredientArrayList.get(position).getMeasure();
        String title = ingredientArrayList.get(position).getIngredient();

        views.setTextViewText(R.id.titleTextView, recipeName);
        views.setTextViewText(R.id.widget_ingredient_quantity,  quantity);
        views.setTextViewText(R.id.widget_ingredient_measure, measure);
        views.setTextViewText(R.id.widget_ingredient_title,title);

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
