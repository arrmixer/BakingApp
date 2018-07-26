package com.ae.andriod.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.ae.andriod.bakingapp.DB.RecipeRepository;
import com.ae.andriod.bakingapp.R;
import com.ae.andriod.bakingapp.Util.NetworkUtil;
import com.ae.andriod.bakingapp.View.RecipeActivity;
import com.ae.andriod.bakingapp.View.RecipeFragment;
import com.ae.andriod.bakingapp.View.RecipeListActivity;
import com.ae.andriod.bakingapp.View.RecipeListFragment;
import com.ae.andriod.bakingapp.ViewModel.RecipeViewModel;
import com.ae.andriod.bakingapp.model.Recipe;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {

    public static final String EXTRA_WIDGET_ID = "com.ae.andriod.bakingapp.widget.id";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            // Create an Intent to launch RecipeListActivity when clicked
            Intent intent = new Intent(context, RecipeListActivity.class);

            intent.putExtra(EXTRA_WIDGET_ID, 4);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

            // Construct the RemoteViews object
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);

            // Widgets allow click handlers to only launch pending intents
            views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views);

        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

