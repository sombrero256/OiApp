package edu.coe.rohrssen.oiapp

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast

class OiWidget : AppWidgetProvider() {

    override fun onUpdate(
            context: Context,
            appWidgetManager: AppWidgetManager, appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        for (appWidget in appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidget)
        }
    }

    fun updateWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {

        val updateViews = RemoteViews(
                context.packageName, R.layout.oiwidget_layout
        )
        updateViews.setTextViewText(R.id.Name, "Myself")
        updateViews.setTextViewText(R.id.Msg, "Oi, me!")

        Toast.makeText(context, "I AM HERE!", Toast.LENGTH_LONG).show()

        val intentUpdate = Intent(context, OiWidget::class.java)
        intentUpdate.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE

        val idArray = intArrayOf(appWidgetId)
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);
        val pendingUpdate = PendingIntent.getBroadcast(
                context, appWidgetId, intentUpdate,
                PendingIntent.FLAG_UPDATE_CURRENT)

        updateViews.setOnClickPendingIntent(R.id.wholeWidget, pendingUpdate);

        appWidgetManager.updateAppWidget(appWidgetId, updateViews)

    }

}