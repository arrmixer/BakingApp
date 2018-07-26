package com.ae.andriod.bakingapp.View;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ae.andriod.bakingapp.R;
import com.ae.andriod.bakingapp.ViewModel.RecipeViewModel;
import com.ae.andriod.bakingapp.databinding.IngredientListItemBinding;
import com.ae.andriod.bakingapp.model.Ingredient;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientHolder>{
    //    Class variables for the List that holds MovieViewModel and the Context
    private final Context mContext;
    private RecipeViewModel mRecipeViewModel;

    public IngredientAdapter(Context context, RecipeViewModel recipeViewModel) {
        mContext = context;
        mRecipeViewModel = recipeViewModel;
    }

    @NonNull
    @Override
    public IngredientHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //get inflater from container Activity
        LayoutInflater inflater = LayoutInflater.from(mContext);

        IngredientListItemBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.ingredient_list_item, parent, false);

        return new IngredientHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull IngredientHolder holder, int position) {
        holder.bindIngredient(position);
    }

    @Override
    public int getItemCount() {
        return mRecipeViewModel.getIngredients().size();
    }

    class IngredientHolder extends RecyclerView.ViewHolder{

        private final IngredientListItemBinding mIngredientListItemBinding;

        public IngredientHolder(IngredientListItemBinding binding) {
            super(binding.getRoot());

            mIngredientListItemBinding = binding;

            mIngredientListItemBinding.setViewModelRecipe(mRecipeViewModel);
        }

        private void bindIngredient(int position){
            //get Ingredient data from list within ViewModel
            Ingredient ingredient = mRecipeViewModel.getIngredients().get(position);

            //get data from model to populate
            double quantity = ingredient.getQuantity();
            //transform to String
            String quantityString = Double.toString(quantity);
            String measure = ingredient.getMeasure();
            String ingredientTitle = ingredient.getIngredient();

            mIngredientListItemBinding.ingredientQuantity.setText(quantityString);
            mIngredientListItemBinding.ingredientMeasure.setText(measure);
            mIngredientListItemBinding.ingredientTitle.setText(ingredientTitle);


        }
    }
}
