package com.ae.andriod.bakingapp.Util;

import android.content.Context;
import android.preference.PreferenceManager;

import com.ae.andriod.bakingapp.DB.ConverterIngredient;
import com.ae.andriod.bakingapp.model.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class IngredientListSharedPreference {

    private static final String PREF_INGREDIENT_LIST = "com.ae.andriod.bakingapp.Util.IngredientListSharedPreference.pref.ingredient.list";

    public static final String PREF_RECIPE_NAME = "com.ae.andriod.bakingapp.Util.IngredientListSharedPreference.pref.recipe.name";

    public static final String PREF_RECIPE_ID = "com.ae.andriod.bakingapp.Util.IngredientListSharedPreference.pref.recipe.id";

    public static List<Ingredient> getPrefIngredientsQuery(Context context) {
        List<Ingredient> ingredients = ConverterIngredient
                .fromStringIngredient(PreferenceManager.getDefaultSharedPreferences(context)
                        .getString(PREF_INGREDIENT_LIST, null));
        return ingredients;
    }

    public static void setPrefIngredientsQuery(Context context, List<Ingredient> ingredientList) {
        String query = ConverterIngredient.fromArrayListIngredient(ingredientList);
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_INGREDIENT_LIST, query)
                .apply();
    }

    public static String getPrefRecipeName(Context context) {

        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_RECIPE_NAME, null);

    }

    public static void setPrefRecipeName(Context context, String query) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_RECIPE_NAME, query)
                .apply();
    }

    public static int getPrefRecipeId(Context context) {

        return PreferenceManager.getDefaultSharedPreferences(context)
                .getInt(PREF_RECIPE_ID, 1);

    }

    public static void setPrefRecipeId(Context context, int id) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putInt(PREF_RECIPE_ID, id)
                .apply();
    }


}
