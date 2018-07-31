package com.ae.andriod.bakingapp.Util;

import com.ae.andriod.bakingapp.model.Ingredient;
import com.ae.andriod.bakingapp.model.Step;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeResponse {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("servings")
    private int servings;

    @SerializedName("image")
    private String image;

    @SerializedName("ingredients")
    List<Ingredient> mIngredient;

    @SerializedName("steps")
    List<Step> step;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getServings() {
        return servings;
    }

    public String getImage() {
        return image;
    }

    public List<Ingredient> getIngredient() {
        return mIngredient;
    }

    public List<Step> getStep() {
        return step;
    }
}
