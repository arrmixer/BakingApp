package com.ae.andriod.bakingapp.View;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ae.andriod.bakingapp.R;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.AllOf.allOf;

@RunWith(AndroidJUnit4.class)
public class IngredientListActivityTest {

    @Rule
    public ActivityTestRule<IngredientListActivity> mActivityTestRule = new ActivityTestRule<>(IngredientListActivity.class);


    //check to see if the fields within the item view are displayed
    @Test
    public void textViewIngredientTest(){
        onView(withId(R.id.recycler_view_ingredients))
                .check(matches(withViewAtPosition(1, hasDescendant(allOf(withId(R.id.ingredient_measure), isDisplayed())))));

        onView(withId(R.id.recycler_view_ingredients))
                .check(matches(withViewAtPosition(1, hasDescendant(allOf(withId(R.id.ingredient_title), isDisplayed())))));

        onView(withId(R.id.recycler_view_ingredients))
                .check(matches(withViewAtPosition(1, hasDescendant(allOf(withId(R.id.ingredient_quantity), isDisplayed())))));


    }

    public static Matcher<View> withViewAtPosition(final int position, final Matcher<View> itemMatcher) {
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(org.hamcrest.Description description) {
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(RecyclerView recyclerView) {
                final RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(position);
                return viewHolder != null && itemMatcher.matches(viewHolder.itemView);
            }
        };
    }



}
