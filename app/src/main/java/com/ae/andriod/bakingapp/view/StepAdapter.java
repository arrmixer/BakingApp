package com.ae.andriod.bakingapp.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ae.andriod.bakingapp.R;
import com.ae.andriod.bakingapp.viewmodel.RecipeViewModel;
import com.ae.andriod.bakingapp.databinding.StepListItemBinding;
import com.ae.andriod.bakingapp.model.Step;

public class StepAdapter  extends RecyclerView.Adapter<StepAdapter.StepHolder>{

    //     Member variable to handle item clicks
    final private ItemClickListener mItemClickListener;

    //    Class variables for the List that holds MovieViewModel and the Context
    private final Context mContext;
    private RecipeViewModel mRecipeViewModel;

    public StepAdapter(ItemClickListener itemClickListener, Context context, RecipeViewModel recipeViewModel) {
        mItemClickListener = itemClickListener;
        mContext = context;
        mRecipeViewModel = recipeViewModel;
    }

    @NonNull
    @Override
    public StepHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //get inflater from container Activity
        LayoutInflater inflater = LayoutInflater.from(mContext);

        StepListItemBinding binding = DataBindingUtil
                .inflate(inflater, R.layout.step_list_item, parent, false);

        return new StepHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StepHolder holder, int position) {
        holder.bindRecipeStep(position);
    }

    @Override
    public int getItemCount() {

        return mRecipeViewModel.getSteps().size();
    }

    public interface ItemClickListener {
        void onItemClickListener(int itemId);
    }

    class StepHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final StepListItemBinding mStepListItemBinding;

        StepHolder(StepListItemBinding binding) {
            super(binding.getRoot());

            mStepListItemBinding = binding;

            itemView.setOnClickListener(this);

            mStepListItemBinding.setViewModelRecipe(mRecipeViewModel);
        }

        private void bindRecipeStep(int position){
            Step step = mRecipeViewModel.getSteps().get(position);

                mStepListItemBinding.txtShortDescription.setText(step.getShortDescription());

        }

        @Override
        public void onClick(View v) {
            int elementId = getAdapterPosition();
            mItemClickListener.onItemClickListener(elementId);
        }
    }
}
