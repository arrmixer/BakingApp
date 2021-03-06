package com.ae.andriod.bakingapp.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import com.ae.andriod.bakingapp.R;
import com.ae.andriod.bakingapp.util.IngredientListSharedPreference;
import com.ae.andriod.bakingapp.viewmodel.RecipeViewModel;
import com.ae.andriod.bakingapp.model.Recipe;

import java.util.ArrayList;

import static com.ae.andriod.bakingapp.view.RecipeActivity.EXTRA_RECIPE_TITLE;

public class StepActivity extends SingleFragmentActivity {

    public static final String TAG = StepActivity.class.getSimpleName();
    private Recipe mRecipe;
    private int itemId;


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_RECIPE_TITLE, mRecipe);
        outState.putParcelableArrayList(RecipeListFragment.EXTRA_LIST_STEPS, (ArrayList<? extends Parcelable>) mRecipe.getSteps());
    }

    @Override
    protected Fragment createFragment() {

        if(getIntent().hasExtra(RecipeListFragment.EXTRA_RECIPE)) {
            mRecipe = getIntent().
                    getParcelableExtra(RecipeListFragment.EXTRA_RECIPE);
            itemId = getIntent().getIntExtra(RecipeListFragment.EXTRA_LIST_STEPS_ITEM, 1);
        }else{
            //Get corresponding Data from DB
            RecipeViewModel vm = ViewModelProviders.of(this).get(RecipeViewModel.class);
            mRecipe = vm.getRecipeFromDB(IngredientListSharedPreference.getPrefRecipeId(this));
            itemId = IngredientListSharedPreference.getPrefStepId(this);
        }
        mRecipe = getIntent().getParcelableExtra(RecipeListFragment.EXTRA_RECIPE);

        return StepFragment.newInstance(mRecipe, itemId);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());


        if (savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable(EXTRA_RECIPE_TITLE);
            setTitle(mRecipe.getName());
        } else {
            setTitle(mRecipe.getName());
        }


    }


}
