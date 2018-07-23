package com.ae.andriod.bakingapp.View;


import android.content.Intent;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import com.ae.andriod.bakingapp.R;
import com.ae.andriod.bakingapp.model.Recipe;

import java.util.ArrayList;
import java.util.List;


public class RecipeListActivity extends SingleFragmentActivity implements RecipeListFragment.Callbacks{

    public static final String TAG = RecipeListActivity.class.getSimpleName();

    @Override
    protected Fragment createFragment() {

        return new RecipeListFragment();
    }

    @Override
    protected int getLayoutResId() {

        //if change layout to master_detail, get master_detail look
        //on tablet
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

    }


    @Override
    public void onRecipeSelected(Recipe recipe, List<Recipe> recipes) {
        if(findViewById(R.id.detail_fragment_container) == null){
            Intent i = new Intent(this, RecipeActivity.class);
            i.putParcelableArrayListExtra(RecipeListFragment.EXTRA_RECIPE_LIST, (ArrayList<? extends Parcelable>) recipes);
            i.putExtra(RecipeListFragment.EXTRA_RECIPE, recipe);
            startActivity(i);
        }
    }
}
