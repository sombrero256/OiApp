package edu.coe.rohrssen.oiapp

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.SmsManager
import android.widget.RemoteViews
import androidx.annotation.RequiresApi


class OiWidget : AppWidgetProvider() {

    var SMSmgr: SmsManager? = null
    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
        SMSmgr = SmsManager.getDefault()

    }

    override fun onDeleted(context: Context?, appWidgetIds: IntArray?) {
        super.onDeleted(context, appWidgetIds)
        WidgetPrefsHelper.deleteMsgPref(context!!, appWidgetIds!![0])
        WidgetPrefsHelper.deleteNamePref(context!!, appWidgetIds!![0])

        }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onUpdate(
            context: Context,
            appWidgetManager: AppWidgetManager, appWidgetIds: IntArray
    ) {
        super.onUpdate(context, appWidgetManager, appWidgetIds)
        for (appWidget in appWidgetIds) {
            updateWidget(context, appWidgetManager, appWidget)
        }
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun updateWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {

        val updateViews = RemoteViews(
                context.packageName, R.layout.oiwidget_layout
        )

        val name = WidgetPrefsHelper.loadNamePref(context, appWidgetId)
        val msg = WidgetPrefsHelper.loadMsgPref(context, appWidgetId)
        SMSmgr = SmsManager.getDefault()

        var number = "5554"

        SMSmgr!!.sendTextMessage(number, null, "Oi: $msg", null, null)

        updateViews.setTextViewText(R.id.Name, name)
        updateViews.setTextViewText(R.id.Msg, msg)

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