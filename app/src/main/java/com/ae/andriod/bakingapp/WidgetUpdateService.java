package com.ae.andriod.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.widget.Toast;

public class WidgetUpdateService extends JobIntentService {

    public static final String ACTION_UPDATE_APP_WIDGETS = "com.ae.andriod.bakingapp.WidgetUpdateService.update.app.widget";

    /**
     * Unique job ID for this service.
     */
    static final int JOB_ID = 1000;

    /**
     * Convenience method for enqueuing work in to this service.
     */
    static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, WidgetUpdateService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_APP_WIDGETS.equals(action)) {
                handleActionUpdateAppWidgets();
            }
        }
    }

    private void handleActionUpdateAppWidgets() {

        //You can do any task regarding this process you want to do here, then update the widget.

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidget.class));

        BakingAppWidget.updateAllAppWidget(this, appWidgetManager,appWidgetIds);
    }


    public static void startActionUpdateAppWidgets(Context context) {

        Intent intent = new Intent(context, WidgetUpdateService.class);
        intent.setAction(ACTION_UPDATE_APP_WIDGETS);

        enqueueWork(context, intent);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        toast("All work complete");
    }

    final Handler mHandler = new Handler();

    // Helper for showing tests
    void toast(final CharSequence text) {
        mHandler.post(new Runnable() {
            @Override public void run() {
                Toast.makeText(WidgetUpdateService.this, text, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
