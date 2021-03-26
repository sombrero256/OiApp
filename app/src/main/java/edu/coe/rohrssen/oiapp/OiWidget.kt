package edu.coe.rohrssen.oiapp

import android.Manifest
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsManager
import android.view.View
import android.widget.RemoteViews
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity

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

        val defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(context) // Need to change the build to API 19
        val sendIntent = Intent(Intent.ACTION_SEND)
        sendIntent.type = "text/plain"
        sendIntent.putExtra(Intent.EXTRA_TEXT, "text")
        if (defaultSmsPackageName != null) // Can be null in case that there is no default, then the user would be able to choose
        // any app that support this intent.
        {
            sendIntent.setPackage(defaultSmsPackageName)
        }
        //startActivity(context, sendIntent, null)


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