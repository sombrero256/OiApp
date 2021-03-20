package edu.coe.rohrssen.oiapp

import android.Manifest
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class OiWidgetConfigure : AppCompatActivity(), View.OnClickListener{
    var mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    private val REQUEST_SEND_SMS = 123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Find the widget id from the intent.
        val intent: Intent = getIntent()
        val extras = intent.extras
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
        }
        setContentView(R.layout.oiconfigure_layout)
        findViewById<Button>(R.id.saveButton).setOnClickListener(this)
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.SEND_SMS)
                !== PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS),
                    REQUEST_SEND_SMS)
        }
    }


    override fun onClick(v: View?) {
        val context: Context = this@OiWidgetConfigure

        // Push widget update to surface with newly set prefix
        val appWidgetManager = AppWidgetManager.getInstance(context)

        // Make sure we pass back the original appWidgetId
        val resultValue = Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId)
        val number = findViewById<EditText>(R.id.editTextPhone).text.toString()
        val msg = findViewById<EditText>(R.id.editTextMessage).text.toString()

        WidgetPrefsHelper.saveNamePref(this, mAppWidgetId, number)
        WidgetPrefsHelper.saveMsgPref(this, mAppWidgetId, msg)
        
        setResult(RESULT_OK, resultValue)
        finish()
    }
}