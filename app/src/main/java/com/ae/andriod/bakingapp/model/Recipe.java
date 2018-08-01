package com.ae.andriod.bakingapp.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;


import com.ae.andriod.bakingapp.db.ConverterIngredient;
import com.ae.andriod.bakingapp.db.ConverterStep;

import java.util.ArrayList;
import java.util.List;


/*Model for the Recipe object(POJO). */
@Entity(tableName = "recipes")
public class Recipe implements Parcelable {

    //fields from JSON
    @PrimaryKey
    @ColumnInfo(name = "recipe_id")
    @NonNull
    private int id;

    private String name;
    private int servings;
    private String image;


    //Json Arrays within Recipe Objects
//    @Ignore
    @TypeConverters(ConverterIngredient.class)
    private List<Ingredient> ingredients = new ArrayList<>();
//    @Ignore
    @TypeConverters(ConverterStep.class)
    private List<Step> steps = new ArrayList<>();

//    @Ignore
    public Recipe(int id, String name, int servings, String image, List<Ingredient> ingredients, List<Step> steps) {
        this.id = id;
        this.name = name;
        this.servings = servings;
        this.image = image;
        this.ingredients = ingredients;
        this.steps = steps;
    }

//    public Recipe(@NonNull int id, String name, int servings, String image) {
//        this.id = id;
//        this.name = name;
//        this.servings = servings;
//        this.image = image;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }


    @Override
    public String toString() {
        return "Recipe{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", servings=" + servings +
                ", image=" + image +
                ", ingredients=" + ingredients +
                ", steps=" + steps +
                '}';
    }

    //Parcel section
    @Ignore
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Ignore
    private Recipe(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.servings = in.readInt();
        this.image = in.readString();
        in.readTypedList(this.ingredients, Ingredient.CREATOR);
        in.readTypedList(this.steps, Step.CREATOR);
    }

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }

    @Ignore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.servings);
        dest.writeString(this.image);
        dest.writeTypedList(this.ingredients);
        dest.writeTypedList(this.steps);
    }
}
