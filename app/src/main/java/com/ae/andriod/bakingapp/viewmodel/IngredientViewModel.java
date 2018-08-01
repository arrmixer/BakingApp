package com.ae.andriod.bakingapp.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.ae.andriod.bakingapp.model.Ingredient;

import java.util.List;

public class IngredientViewModel  extends AndroidViewModel {

    private Ingredient mIngredient;
    private final MutableLiveData<List<Ingredient>> mLiveIngredientsData;

    public IngredientViewModel(@NonNull Application application) {
        super(application);
        mLiveIngredientsData = new MutableLiveData<>();
    }

    public void setIngredient(Ingredient ingredient){
        mIngredient = ingredient;
    }

    public MutableLiveData<List<Ingredient>> getLiveIngredientsData(List<Ingredient> ingredients){
        mLiveIngredientsData.setValue(ingredients);
        return  mLiveIngredientsData;
    }

    public double getQuantity(){
        return mIngredient.getQuantity();
    }

    public String getMeasure(){
        return mIngredient.getMeasure();
    }

    public String getIngredient(){
        return mIngredient.getIngredient();
    }


}
