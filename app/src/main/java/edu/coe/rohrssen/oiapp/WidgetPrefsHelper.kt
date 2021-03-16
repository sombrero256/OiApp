package edu.coe.rohrssen.oiapp

import android.content.Context
import android.util.Log

class WidgetPrefsHelper {
    companion object {
        val PREFS_NAME = "edu.coe.rohrssen.oiapp.OiWidget"
        val PREF_PREFIX_KEY = "oiwidget_"
        val LOGKEY = "Prefs"

        @JvmStatic
        fun saveNamePref(context: Context, appWidgetId: Int, text: String){
            val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
            prefs.putString(PREF_PREFIX_KEY + appWidgetId + "_name", text)
            prefs.apply()
            Log.i(LOGKEY, text)
        }
        @JvmStatic
        fun saveMsgPref(context: Context, appWidgetId: Int, text: String){
            val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
            prefs.putString(PREF_PREFIX_KEY + appWidgetId + "_msg", text)
            prefs.apply()
            Log.i(LOGKEY, text)
        }


        @JvmStatic
        fun loadNamePref(context: Context, appWidgetId: Int): String?{
            val prefs = context.getSharedPreferences(PREFS_NAME, 0)
            val nameValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId + "_name", "No value found")
            Log.i(LOGKEY, "in loadNamePref")
            return nameValue
        }

        @JvmStatic
        fun loadMsgPref(context: Context, appWidgetId: Int): String?{
            val prefs = context.getSharedPreferences(PREFS_NAME, 0)
            val msgValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId + "_msg", "No value found")
            Log.i(LOGKEY, "in loadMsgPref")
            return msgValue
        }



        @JvmStatic
        fun deleteNamePref(context: Context, appWidgetId: Int){
            val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
            prefs.remove(PREF_PREFIX_KEY + appWidgetId + "_name")
            prefs.apply()
        }
        @JvmStatic
        fun deleteMsgPref(context: Context, appWidgetId: Int){
            val prefs = context.getSharedPreferences(PREFS_NAME, 0).edit()
            prefs.remove(PREF_PREFIX_KEY + appWidgetId + "_msg")
            prefs.apply()
        }

    }
}