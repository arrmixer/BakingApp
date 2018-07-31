package com.ae.andriod.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;


import com.ae.andriod.bakingapp.Util.IngredientListSharedPreference;
import com.ae.andriod.bakingapp.View.RecipeActivity;

/**
 * Implementation of App Widget functionality.
 */
public class BakingAppWidget extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        RemoteViews remoteView;

        remoteView = getViewForBiggerWidget(context, options);

        appWidgetManager.updateAppWidget(appWidgetId, remoteView);

        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.listView);

    }

    private static RemoteViews getViewForBiggerWidget(Context context, Bundle options) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget_big);

        int minHeight = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
        if (minHeight < 100) {
            views.setViewVisibility(R.id.titleTextView, View.GONE);
        }else{
            views.setViewVisibility(R.id.titleTextView, View.VISIBLE);

            String recipeName = IngredientListSharedPreference.getPrefRecipeName(context);

            views.setTextViewText(R.id.titleTextView, recipeName);

        }

        Intent intent = new Intent(context, ListViewWidgetService.class);
        views.setRemoteAdapter(R.id.listView, intent);

        views.setEmptyView(R.id.listView, R.id.empty_view);

        Intent appIntent = new Intent(context, RecipeActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.listView, appPendingIntent);

        return views;

    }

    public static void updateAllAppWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        WidgetUpdateService.startActionUpdateAppWidgets(context);
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager, int appWidgetId, Bundle newOptions) {
        //if need width
//        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
//        int width = options.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);

        WidgetUpdateService.startActionUpdateAppWidgets(context);

        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions);
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

