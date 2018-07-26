package com.ae.andriod.bakingapp.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.ae.andriod.bakingapp.BakingAppWidget;
import com.ae.andriod.bakingapp.R;
import com.ae.andriod.bakingapp.ViewModel.RecipeViewModel;
import com.ae.andriod.bakingapp.model.Ingredient;
import com.ae.andriod.bakingapp.model.Recipe;
import com.ae.andriod.bakingapp.model.Step;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends SingleFragmentActivity implements RecipeFragment.Callbacks{

    //action String used to changed the Title of the action bar to the Recipe title
    protected static final String EXTRA_RECIPE_TITLE = "com.ae.andriod.bakingapp.recipe.title";

    private Recipe mRecipe;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_RECIPE_TITLE, mRecipe);

    }

    @Override
    protected Fragment createFragment() {


            mRecipe = getIntent().getParcelableExtra(RecipeListFragment.EXTRA_RECIPE);
//        RecipeViewModel r = ViewModelProviders.of(this).get(RecipeViewModel.class);
//        int id = getIntent().getIntExtra(BakingAppWidget.EXTRA_WIDGET_ID, 1);
//        mRecipe = r.getRecipeFromDB(id);




        return RecipeFragment.newInstance(mRecipe);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        if(savedInstanceState != null){
            mRecipe = savedInstanceState.getParcelable(EXTRA_RECIPE_TITLE);
            setTitle(mRecipe.getName());
        }else{
            setTitle(mRecipe.getName());
        }

    }


    @Override
    public void onIngredientSelected(Recipe recipe) {
        if(findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = new Intent(this, IngredientListActivity.class);
            intent.putExtra(RecipeListFragment.EXTRA_RECIPE, recipe);
            startActivity(intent);
        }else{
            Fragment newDetail = IngredientListFragment.newInstance(recipe);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }

    @Override
    public void onStepSelected(Recipe recipe, int itemId) {
        if(findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = new Intent(this, StepActivity.class);
            intent.putExtra(RecipeListFragment.EXTRA_RECIPE, recipe);
            intent.putExtra(RecipeListFragment.EXTRA_LIST_STEPS_ITEM, itemId );
            startActivity(intent);
        }else{
            Fragment newDetail = StepFragment.newInstance(recipe, itemId);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }

    }
}
