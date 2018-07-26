package com.ae.andriod.bakingapp.View;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ae.andriod.bakingapp.R;
import com.ae.andriod.bakingapp.ViewModel.RecipeViewModel;
import com.ae.andriod.bakingapp.databinding.FragmentRecipeStepBinding;
import com.ae.andriod.bakingapp.model.Recipe;
import com.ae.andriod.bakingapp.model.Step;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class StepFragment extends Fragment {

    //Constant for Logging
    private static final String TAG = StepFragment.class.getSimpleName();

    //placeholders
    private Recipe mRecipe;
    private RecipeViewModel mRecipeViewModel;
    private Step mStep;
    private int itemId;

    //Exoplayer elements
    private PlayerView playerView;
    private SimpleExoPlayer player;
    private int currentWindow;
    private long playbackPosition;
    private boolean playWhenReady = true;

    private FragmentRecipeStepBinding mFragmentRecipeStepBinding;

    /*Following of adding a static method to the Fragment Class
     * This method will put arguments in the Bundle and then
     * attached it to the fragment. In addition, this allows for
     * more modularity so that the fragment is not dependant on its
     * container activity*/
    public static StepFragment newInstance(Recipe recipe, int itemId) {

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
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer(mStep.getVideoURL());
        }

    }

    @Override
    public void onResume() {
        super.onResume();
         hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer(mStep.getVideoURL());
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RecipeListFragment.EXTRA_RECIPE, mRecipe);
        outState.putInt(RecipeListFragment.EXTRA_LIST_STEPS_ITEM, itemId);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRecipeViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);


        if (savedInstanceState != null) {
            mRecipe = savedInstanceState.getParcelable(RecipeListFragment.EXTRA_RECIPE);
            itemId = savedInstanceState.getInt(RecipeListFragment.EXTRA_LIST_STEPS_ITEM);
            mStep = mRecipe.getSteps().get(itemId);
        } else {
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

        playerView = mFragmentRecipeStepBinding.videoView;

        return mFragmentRecipeStepBinding.getRoot();
    }

    private void initializePlayer(String stringUri) {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(),
                    new DefaultLoadControl());

            playerView.setPlayer(player);
            player.setPlayWhenReady(playWhenReady);

            player.seekTo(currentWindow, playbackPosition);

            if (stringUri.isEmpty() || stringUri == null) {
                Toast.makeText(getContext(), "No video available for this Step", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Uri uri = Uri.parse(stringUri);
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);

    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory(getString(R.string.exoplayer)))
                .createMediaSource(uri);

    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {

        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }
}
