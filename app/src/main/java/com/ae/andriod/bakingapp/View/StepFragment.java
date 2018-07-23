package com.ae.andriod.bakingapp.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ae.andriod.bakingapp.R;
import com.ae.andriod.bakingapp.ViewModel.RecipeViewModel;
import com.ae.andriod.bakingapp.databinding.FragmentRecipeStepBinding;
import com.ae.andriod.bakingapp.model.Recipe;
import com.ae.andriod.bakingapp.model.Step;


public class StepFragment extends Fragment {

    //Constant for Logging
    private static final String TAG = StepFragment.class.getSimpleName();

    //placeholders
    private Recipe mRecipe;
    private RecipeViewModel mRecipeViewModel;
    private Step mStep;
    private int itemId;

    private FragmentRecipeStepBinding mFragmentRecipeStepBinding;


    /*Following of adding a static method to the Fragment Class
     * This method will put arguments in the Bundle and then
     * attached it to the fragment. In addition, this allows for
     * more modularity so that the fragment is not dependant on its
     * container activity*/
    public static StepFragment newInstance(Recipe recipe, int itemId){

        //get data from intent of parent activity
        Bundle bundle = new Bundle();
        bundle.putParcelable(RecipeListFragment.EXTRA_RECIPE, recipe);
        bundle.putInt(RecipeListFragment.EXTRA_LIST_STEPS_ITEM, itemId);

        //place data inside fragment
        StepFragment stepFragment = new StepFragment();
        stepFragment.setArguments(bundle);

        return stepFragment;

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RecipeListFragment.EXTRA_RECIPE, mRecipe );
        outState.putInt(RecipeListFragment.EXTRA_LIST_STEPS_ITEM, itemId);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        if (savedInstanceState != null){
            mRecipe = savedInstanceState.getParcelable(RecipeListFragment.EXTRA_RECIPE);
            itemId = savedInstanceState.getInt(RecipeListFragment.EXTRA_LIST_STEPS_ITEM);
            mStep = mRecipe.getSteps().get(itemId);

        }else{
            mRecipe = getArguments().getParcelable(RecipeListFragment.EXTRA_RECIPE);
            itemId = getArguments().getInt(RecipeListFragment.EXTRA_LIST_STEPS_ITEM);
            mStep = mRecipe.getSteps().get(itemId);

        }

        mRecipeViewModel.setRecipe(mRecipe);
        mRecipeViewModel.setLiveRecipeData(mRecipe);
        mRecipeViewModel.getLiveRecipeData().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable Recipe recipe) {
                Log.i(TAG, "OnChanged called!");
                mRecipe = recipe;
                mStep = mRecipe.getSteps().get(itemId);

                mFragmentRecipeStepBinding.txtRecipeDescription.setText(mStep.getDescription());


            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragmentRecipeStepBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_step, container, false);

        mFragmentRecipeStepBinding.setLifecycleOwner(this);

        return mFragmentRecipeStepBinding.getRoot();
    }
}
