package com.ae.andriod.bakingapp.View;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.ae.andriod.bakingapp.R;
import com.ae.andriod.bakingapp.model.Recipe;

import static com.ae.andriod.bakingapp.View.RecipeActivity.EXTRA_RECIPE_TITLE;

public class StepActivity extends SingleFragmentActivity{

    public static final String TAG = StepActivity.class.getSimpleName();
    private Recipe mRecipe;
    private int itemId;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_RECIPE_TITLE, mRecipe);
    }

    @Override
    protected Fragment createFragment() {
        mRecipe = getIntent().getParcelableExtra(RecipeListFragment.EXTRA_RECIPE);
        itemId = getIntent().getIntExtra(RecipeListFragment.EXTRA_LIST_STEPS_ITEM, 1);
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

        if(savedInstanceState != null){
            mRecipe = savedInstanceState.getParcelable(EXTRA_RECIPE_TITLE);
            setTitle(mRecipe.getName());
        }else{
            setTitle(mRecipe.getName());
        }
    }
}
