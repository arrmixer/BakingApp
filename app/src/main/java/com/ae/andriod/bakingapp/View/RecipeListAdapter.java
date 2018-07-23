package com.ae.andriod.bakingapp.View;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ae.andriod.bakingapp.R;
import com.ae.andriod.bakingapp.ViewModel.RecipeViewModel;
import com.ae.andriod.bakingapp.databinding.RecipeListItemBinding;
import com.ae.andriod.bakingapp.model.Recipe;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeHolder> {

    //     Member variable to handle item clicks
    private final ItemClickListener mItemClickListener;

    //    Class variables for the List that holds RecipeViewModel and the Context
    private final Context mContext;
    private final RecipeViewModel mRecipeViewModel;


    public RecipeListAdapter(ItemClickListener itemClickListener, Context context, RecipeViewModel recipeViewModel) {
        mItemClickListener = itemClickListener;
        mContext = context;
        mRecipeViewModel = recipeViewModel;

    }

    @NonNull
    @Override
    public RecipeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //get inflater from container Activity
        LayoutInflater inflater = LayoutInflater.from(mContext);

        RecipeListItemBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.recipe_list_item, parent, false);


        return new RecipeHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeHolder holder, int position) {
       Recipe recipe = mRecipeViewModel.getLiveRecipeListData().getValue().get(position);
        holder.bindRecipe(recipe);
    }

    @Override
    public int getItemCount() {

            return mRecipeViewModel.getLiveRecipeListData().getValue().size();

    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    class RecipeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final RecipeListItemBinding mRecipeListItemBinding;

        private Recipe mRecipe;

        private RecipeHolder(RecipeListItemBinding binding){
            super(binding.getRoot());

            //assign instance of RecipeListItemBinding to parameter
            mRecipeListItemBinding = binding;

            //set click event to each itemView
            itemView.setOnClickListener(this);

            mRecipeListItemBinding.setViewModelRecipe(mRecipeViewModel);

        }

        private void bindRecipe(Recipe recipe){
            mRecipe = recipe;
            mRecipeViewModel.setRecipe(recipe);
            String title = mRecipeViewModel.getName();
            mRecipeListItemBinding.txtRecipeName.setText(title);

        }

        @Override
        public void onClick(View v) {
            int elementId = getAdapterPosition();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}
