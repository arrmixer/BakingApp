package com.ae.andriod.bakingapp.View;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.ae.andriod.bakingapp.R;
import com.ae.andriod.bakingapp.ViewModel.RecipeViewModel;
import com.ae.andriod.bakingapp.databinding.FragmentRecipeBinding;
import com.ae.andriod.bakingapp.model.Ingredient;
import com.ae.andriod.bakingapp.model.Recipe;
import com.ae.andriod.bakingapp.model.Step;


import java.util.ArrayList;
import java.util.List;

public class RecipeFragment extends Fragment implements StepAdapter.ItemClickListener{

    //Constant for Logging
    private static final String TAG = RecipeFragment.class.getSimpleName();

    //placeholders
    private Recipe mRecipe;
    private List<Step> mSteps;
    private RecipeViewModel mRecipeViewModel;

    private StepAdapter mStepAdapter;
    private RecyclerView mStepRecyclerView;


    //instance of generated DataBinding class for Fragment
    private FragmentRecipeBinding mFragmentRecipeBinding;

    //instance of Callback
    private Callbacks mCallbacks;

    //Interface for hosting activities
    public interface Callbacks {
        void onIngredientSelected(Recipe recipe);
        void onStepSelected(Recipe recipe, int itemId);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //must implement interface on RecipeListActivity
        //because cast is unchecked
        mCallbacks = (Callbacks) context;
    }


    /*Following of adding a static method to the Fragment Class
     * This method will put arguments in the Bundle and then
     * attached it to the fragment. In addition, this allows for
     * more modularity so that the fragment is not dependant on its
     * container activity*/
    public static RecipeFragment newInstance(Recipe recipe, List<Recipe> recipes){

        //get data from intent of parent activity
        Bundle bundle = new Bundle();
        bundle.putParcelable(RecipeListFragment.EXTRA_RECIPE, recipe);
        bundle.putParcelableArrayList(RecipeListFragment.EXTRA_RECIPE_LIST, (ArrayList<? extends Parcelable>) recipes);

        //place data inside fragment
        RecipeFragment recipeFragment = new RecipeFragment();
        recipeFragment.setArguments(bundle);
        return recipeFragment;
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
            mSteps = mRecipe.getSteps();
        }else{
            mRecipe = getArguments().getParcelable(RecipeListFragment.EXTRA_RECIPE);
            mSteps = mRecipe.getSteps();
        }

        //set Recipe to both fields in RecipeViewModel
        //live and regular
        mRecipeViewModel.setRecipe(mRecipe);
        mRecipeViewModel.setLiveRecipeData(mRecipe);

        mRecipeViewModel.getLiveRecipeData().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable Recipe recipe) {
                Log.i(TAG, "OnChanged called!");
                mRecipe = recipe;
                mSteps = recipe.getSteps();
                Log.i(TAG, mSteps.toString());
                setupAdapter();
                Log.i(TAG, mRecipe.toString());
            }
        });






    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mFragmentRecipeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe , container, false);

        mFragmentRecipeBinding.setLifecycleOwner(this);

       mStepRecyclerView =  mFragmentRecipeBinding.recyclerViewSteps;
       mStepRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return  mFragmentRecipeBinding.getRoot();

    }

    //release the callback once the fragment is detached
    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }


    //make sure data is in before assigning to adapter
    private void setupAdapter() {
        if(isAdded() && mSteps != null){
            mStepAdapter = new StepAdapter(this, getContext(), mRecipeViewModel);
            mStepRecyclerView.setAdapter(mStepAdapter);
            mStepAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onItemClickListener(int itemId) {

        if(itemId == 0){
            mCallbacks.onIngredientSelected(mRecipe);
        }else{
            Toast.makeText(getContext(), "working: " + itemId, Toast.LENGTH_SHORT).show();
            mCallbacks.onStepSelected(mRecipe, itemId);
            return;
        }

    }
}
