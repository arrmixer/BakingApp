package com.ae.andriod.bakingapp.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ae.andriod.bakingapp.R;
import com.ae.andriod.bakingapp.ViewModel.IngredientAdapter;
import com.ae.andriod.bakingapp.ViewModel.RecipeViewModel;
import com.ae.andriod.bakingapp.model.Ingredient;
import com.ae.andriod.bakingapp.model.Recipe;
import com.ae.andriod.bakingapp.databinding.FragmentRecipeIngredientsBinding;

import java.util.List;

public class IngredientListFragment extends Fragment {

    //Constant for Logging
    private static final String TAG = IngredientListFragment.class.getSimpleName();

    //placeholders
    private Recipe mRecipe;
    List<Ingredient> mIngredientList;

    private RecyclerView mRecyclerViewIngredients;
    private IngredientAdapter mIngredientAdapter;

    //instance of ViewModel
    private RecipeViewModel mRecipeViewModel;

    //DataBinding instance
    FragmentRecipeIngredientsBinding mFragmentRecipeIngredientsBinding;





    /*Following of adding a static method to the Fragment Class
     * This method will put arguments in the Bundle and then
     * attached it to the fragment. In addition, this allows for
     * more modularity so that the fragment is not dependant on its
     * container activity*/
    public static IngredientListFragment newInstance(Recipe recipe){

        //get data from intent of parent activity
        Bundle bundle = new Bundle();
        bundle.putParcelable(RecipeListFragment.EXTRA_RECIPE, recipe);

        //place data inside fragment
        IngredientListFragment ingredientListFragment = new IngredientListFragment();
        ingredientListFragment.setArguments(bundle);
        return ingredientListFragment;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RecipeListFragment.EXTRA_RECIPE, mRecipe );
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);

        if (savedInstanceState != null){
            mRecipe = savedInstanceState.getParcelable(RecipeListFragment.EXTRA_RECIPE);
            mIngredientList = mRecipe.getIngredients();
        }else{
            mRecipe = getArguments().getParcelable(RecipeListFragment.EXTRA_RECIPE);
            mIngredientList = mRecipe.getIngredients();
        }

        mRecipeViewModel.setRecipe(mRecipe);
        mRecipeViewModel.setLiveRecipeData(mRecipe);
        mRecipeViewModel.getLiveRecipeData().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable Recipe recipe) {
                Log.i(TAG, "OnChanged called!");
                mRecipe = recipe;
                setupAdapter();
            }
        });



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mFragmentRecipeIngredientsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_ingredients,container, false);

        mFragmentRecipeIngredientsBinding.setLifecycleOwner(this);

        mRecyclerViewIngredients = mFragmentRecipeIngredientsBinding.recyclerViewIngredients;

        mRecyclerViewIngredients.setLayoutManager(new GridLayoutManager(getActivity(), 2));


        return mFragmentRecipeIngredientsBinding.getRoot();
    }

    private void setupAdapter() {
        if(isAdded() && mIngredientList != null){
            mIngredientAdapter = new IngredientAdapter(getContext(), mRecipeViewModel);
            mRecyclerViewIngredients.setAdapter(mIngredientAdapter);
            mIngredientAdapter.notifyDataSetChanged();
        }
    }
}
