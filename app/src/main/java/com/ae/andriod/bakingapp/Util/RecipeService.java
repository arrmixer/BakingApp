package com.ae.andriod.bakingapp.Util;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {

    @GET("baking.json")
    Call<List<RecipeResponse>> listRecipes();


}
