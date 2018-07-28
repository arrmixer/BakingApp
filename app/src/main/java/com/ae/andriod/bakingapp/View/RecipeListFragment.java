package com.ae.andriod.bakingapp.View;

import android.arch.lifecycle.AndroidViewModel;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ae.andriod.bakingapp.DB.ConverterIngredient;
import com.ae.andriod.bakingapp.DB.ConverterStep;
import com.ae.andriod.bakingapp.R;
import com.ae.andriod.bakingapp.Util.IngredientListSharedPreference;
import com.ae.andriod.bakingapp.Util.NetworkUtil;
import com.ae.andriod.bakingapp.Util.RecipeResponse;
import com.ae.andriod.bakingapp.Util.RecipeService;
import com.ae.andriod.bakingapp.ViewModel.RecipeViewModel;
import com.ae.andriod.bakingapp.model.Ingredient;
import com.ae.andriod.bakingapp.model.Recipe;
import com.ae.andriod.bakingapp.databinding.FragmentRecipesBinding;
import com.ae.andriod.bakingapp.model.Step;


import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecipeListFragment extends Fragment implements RecipeListAdapter.ItemClickListener {

    private static final String TAG = RecipeListFragment.class.getSimpleName();

    protected static final String EXTRA_RECIPE_LIST = "com.ae.andriod.bakingapp.recipe.list";

    protected static final String EXTRA_RECIPE = "com.ae.andriod.bakingapp.recipe";

    protected static final String EXTRA_LIST_INGREDIENTS = "com.ae.andriod.bakingapp.ingredient.list";

    protected static final String EXTRA_LIST_STEPS_ITEM = "com.ae.andriod.bakingapp.step.list.item";

    protected static final String EXTRA_LIST_STEPS = "om.ae.andriod.bakingapp.ingredient.steps";

    /*Placeholders for Recipes*/
    private List<Recipe> mRecipeList;

    //instance of ViewModel
    private RecipeViewModel mRecipeViewModel;

    //DataBinding instance
    FragmentRecipesBinding mFragmentRecipesBinding;


    //instance of Callback
    private Callbacks mCallbacks;

    //Interface for hosting activities
    public interface Callbacks {
        void onRecipeSelected(Recipe recipe, List<Recipe> recipes);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //must implement interface on RecipeListActivity
        //because cast is unchecked
        mCallbacks = (Callbacks) context;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_RECIPE_LIST, (ArrayList<? extends Parcelable>) mRecipeList);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.recipe_title);


        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);


        if (savedInstanceState != null) {
            mRecipeList = savedInstanceState.getParcelableArrayList(EXTRA_RECIPE_LIST);
            mRecipeViewModel.setLiveRecipeListData(mRecipeList);
        } else {
            //retrofit api call
            fetchRecipes();
        }


        //create LiveData Observable
        mRecipeViewModel.getLiveRecipeListData().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                Log.i(TAG, "OnChanged called!");
                mRecipeList = recipes;
                setupAdapter();
                Log.i(TAG, "Recipes total: first " + mRecipeList.size());
                Log.i(TAG, "Steps list is empty? " + mRecipeList.get(0).getSteps().isEmpty());
                Log.i(TAG, "Ingredients list is empty? " + mRecipeList.get(0).getIngredients().isEmpty());
                Log.i(TAG, "Ingredients back to JSON --------------" + ConverterIngredient.fromArrayListIngredient(mRecipeList.get(0).getIngredients()));
                Log.i(TAG, "Steps back to JSON --------------" + ConverterStep.fromArrayListStep(mRecipeList.get(0).getSteps()));
                String ingredients = ConverterIngredient.fromArrayListIngredient(mRecipeList.get(0).getIngredients());
                String steps = ConverterStep.fromArrayListStep(mRecipeList.get(0).getSteps());

                List<Ingredient> testI = ConverterIngredient.fromStringIngredient(ingredients);
                Log.i(TAG, "From JSON back to ingredients ---------" +  testI.toString());
                List<Step> testS = ConverterStep.fromStringStep(steps);
                Log.i(TAG, "From JSON back to Steps ---------" +  testS.toString());

                /*Place values into sharePreferences
                 * to be used by the widget ListView*/
                IngredientListSharedPreference.setPrefIngredientsQuery(getContext(), mRecipeList.get(0).getIngredients());
                IngredientListSharedPreference.setPrefRecipeName(getContext(), mRecipeList.get(0).getName());
                IngredientListSharedPreference.setPrefRecipeId(getContext(), mRecipeList.get(0).getId());

                Log.i(TAG, "SharedPreference Ingredient List: " + IngredientListSharedPreference.getPrefIngredientsQuery(getContext()) );
                Log.i(TAG, "SharedPreference Recipe Name: " + IngredientListSharedPreference.getPrefRecipeName(getContext()));
                Log.i(TAG, "SharedPreference Recipe Id: " + IngredientListSharedPreference.getPrefRecipeId(getContext()));

            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mFragmentRecipesBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipes, container, false);

        mFragmentRecipesBinding.setLifecycleOwner(this);

        mFragmentRecipesBinding.recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        return mFragmentRecipesBinding.getRoot();


    }

    //release the callback once the fragment is detached
    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onItemClickListener(int itemId) {
        Toast.makeText(getContext(), "working: " + itemId, Toast.LENGTH_SHORT).show();
        /*Used this convention to facilitate Master-Detail layout function but
         * not using it with the main page only the second and third pages where the actual
         * recipe data shows up. */
        mCallbacks.onRecipeSelected(mRecipeList.get(itemId), mRecipeList);
//        Log.i(TAG, "Steps list is empty in intent? " + mRecipeList.get(itemId).getSteps().isEmpty());
//        Log.i(TAG, "Ingredients list is empty in intent? " + mRecipeList.get(itemId).getIngredients().isEmpty());
    }

    //make sure data is in before assigning to adapter
    private void setupAdapter() {
        if (isAdded() && mRecipeList != null) {
            RecipeListAdapter recipeListAdapter = new RecipeListAdapter(this, getContext(), mRecipeViewModel);
            mFragmentRecipesBinding.recyclerView.setAdapter(recipeListAdapter);
            recipeListAdapter.notifyDataSetChanged();
        }
    }


    //Network call using Retrofit2
    public void fetchRecipes() {

        final List<Recipe> recipes = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();

        Retrofit mRetrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(NetworkUtil.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecipeService service = mRetrofit.create(RecipeService.class);

        Call<List<RecipeResponse>> call = service.listRecipes();

        call.enqueue(new Callback<List<RecipeResponse>>() {
            @Override
            public void onResponse(Call<List<RecipeResponse>> call, Response<List<RecipeResponse>> response) {
                List<RecipeResponse> recipeResponses = response.body();

                Log.i(TAG, recipeResponses.get(0).getIngredient().toString());
                Log.i(TAG, "" + recipeResponses.get(0).getIngredient().size());
                Log.i(TAG, recipeResponses.get(0).getStep().toString());
                Log.i(TAG, "" + recipeResponses.get(0).getStep().size());

                for (RecipeResponse r : recipeResponses) {
                    //use helper method to create Recipe objects
                    Recipe recipe = makeRecipe(r);

                    recipes.add(recipe);
                }

                /*assign Recipes list to instance variable
                 * and then set MutableLiveData object*/
                mRecipeList = recipes;
                mRecipeViewModel.setLiveRecipeListData(mRecipeList);

                //add the recipes to the DB
                for(Recipe recipe : mRecipeList){
                    mRecipeViewModel.insertMovieDB(recipe);
                }


            }

            @Override
            public void onFailure(Call<List<RecipeResponse>> call, Throwable t) {

                Log.i(TAG, "Didn't work: " + t.getMessage());
            }
        });


    }

    //helper method to transfer response data to Recipe model
    private static Recipe makeRecipe(RecipeResponse recipeResponse) {

        List<Ingredient> ingredientsParcelable = new ArrayList<>();
        List<Step> stepsParcelable = new ArrayList<>();

        //Recipe Section
        int id = recipeResponse.getId();
        String name = recipeResponse.getName();
        int servings = recipeResponse.getServings();
        String image = recipeResponse.getImage();


        List<Ingredient> ingredients = recipeResponse.getIngredient();

        List<Step> steps = recipeResponse.getStep();

        Recipe recipe = new Recipe(id, name, servings, image, ingredients, steps);





        return recipe;
    }

}

