package com.ae.andriod.bakingapp.View;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.ae.andriod.bakingapp.IdlingResource.SimpleIdlingResource;
import com.ae.andriod.bakingapp.ListViewWidgetService;
import com.ae.andriod.bakingapp.R;
import com.ae.andriod.bakingapp.Util.IngredientListSharedPreference;
import com.ae.andriod.bakingapp.ViewModel.RecipeViewModel;
import com.ae.andriod.bakingapp.model.Recipe;

public class RecipeActivity extends SingleFragmentActivity implements RecipeFragment.Callbacks {

    //action String used to changed the Title of the action bar to the Recipe title
    static final String EXTRA_RECIPE_TITLE = "com.ae.andriod.bakingapp.recipe.title";
    private static final String TAG = RecipeActivity.class.getSimpleName();

    // The Idling Resource which will be null in production.
    @Nullable
    private SimpleIdlingResource mIdlingResource;

    private Recipe mRecipe;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_RECIPE_TITLE, mRecipe);

    }

    @Override
    protected Fragment createFragment() {

        if (getIntent().hasExtra(RecipeListFragment.EXTRA_RECIPE)) {

            mRecipe = getIntent().
                    getParcelableExtra(RecipeListFragment.EXTRA_RECIPE);
        } else if (getIntent().hasExtra(ListViewWidgetService.EXTRA_WIDGET_RECIPE_ID)) {

            //using Recipe ID coming from Widget and then getting corresponding
            //data from DB
            RecipeViewModel recipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
            int movieIdFromWidget = getIntent().getIntExtra(ListViewWidgetService.EXTRA_WIDGET_RECIPE_ID, 1);
            mRecipe = recipeViewModel.getRecipeFromDB(movieIdFromWidget);
        } else {

            //Get corresponding Data from DB
            RecipeViewModel vm = ViewModelProviders.of(this).get(RecipeViewModel.class);
            mRecipe = vm.getRecipeFromDB(IngredientListSharedPreference.getPrefRecipeId(this));
        }

        return RecipeFragment.newInstance(mRecipe);
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
    private IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
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

            /*Place values into sharePreferences
             * to be used by the widget ListView or In other Fragments */
//            IngredientListSharedPreference.setPrefIngredientsQuery(this, mRecipe.getIngredients());

            IngredientListSharedPreference.setPrefRecipeName(this, mRecipe.getName());
            IngredientListSharedPreference.setPrefRecipeId(this, mRecipe.getId());

//            Log.i(TAG, "SharedPreference Ingredient List: " + IngredientListSharedPreference.getPrefIngredientsQuery(this) );
            Log.i(TAG, "SharedPreference Recipe Name: " + IngredientListSharedPreference.getPrefRecipeName(this));
            Log.i(TAG, "SharedPreference Recipe Id: " + IngredientListSharedPreference.getPrefRecipeId(this));

        }

        getIdlingResource();

    }


    @Override
    public void onIngredientSelected(Recipe recipe) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = new Intent(this, IngredientListActivity.class);
            intent.putExtra(RecipeListFragment.EXTRA_RECIPE, recipe);
            startActivity(intent);
        } else {
            Fragment newDetail = IngredientListFragment.newInstance(recipe);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }
    }

    @Override
    public void onStepSelected(Recipe recipe, int itemId) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            //store Step Id in shared Preferences
            IngredientListSharedPreference.setPrefStepId(this, itemId);
            Log.i(TAG, "SharedPreference Step Id: " + IngredientListSharedPreference.getPrefStepId(this));


            Intent intent = new Intent(this, StepActivity.class);
            intent.putExtra(RecipeListFragment.EXTRA_RECIPE, recipe);
            intent.putExtra(RecipeListFragment.EXTRA_LIST_STEPS_ITEM, itemId);
            startActivity(intent);
        } else {
            Fragment newDetail = StepFragment.newInstance(recipe, itemId);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, newDetail)
                    .commit();
        }

    }
}
