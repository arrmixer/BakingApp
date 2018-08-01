package com.ae.andriod.bakingapp.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;

import com.ae.andriod.bakingapp.idlingresource.SimpleIdlingResource;
import com.ae.andriod.bakingapp.R;
import com.ae.andriod.bakingapp.util.IngredientListSharedPreference;
import com.ae.andriod.bakingapp.viewmodel.RecipeViewModel;
import com.ae.andriod.bakingapp.model.Recipe;


import static com.ae.andriod.bakingapp.view.RecipeActivity.EXTRA_RECIPE_TITLE;

public class IngredientListActivity extends SingleFragmentActivity {

    public static final String TAG = IngredientListActivity.class.getSimpleName();
    private Recipe mRecipe;

    // The Idling Resource which will be null in production.
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_RECIPE_TITLE, mRecipe);

    }


    @Override
    protected Fragment createFragment() {
        if(getIntent().hasExtra(RecipeListFragment.EXTRA_RECIPE)) {
            mRecipe = getIntent().
                    getParcelableExtra(RecipeListFragment.EXTRA_RECIPE);
        }else{
            //Get corresponding Data from DB
            RecipeViewModel vm = ViewModelProviders.of(this).get(RecipeViewModel.class);
            mRecipe = vm.getRecipeFromDB(IngredientListSharedPreference.getPrefRecipeId(this));
        }

        return IngredientListFragment.newInstance(mRecipe);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    /**
     * Only called from test, creates and returns a new {@link SimpleIdlingResource}.
     */
    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
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

        getIdlingResource();

    }

}
