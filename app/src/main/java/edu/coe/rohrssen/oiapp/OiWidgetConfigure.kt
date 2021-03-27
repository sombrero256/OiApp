package edu.coe.rohrssen.oiapp

import android.Manifest
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.CursorWrapper

import android.net.Uri

import android.os.Bundle
import android.provider.ContactsContract
import android.text.Editable
import android.text.TextWatcher
import android.util.Log

import android.view.View
import android.view.ViewGroup

import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.cursoradapter.widget.SimpleCursorAdapter


class OiWidgetConfigure : AppCompatActivity(), View.OnClickListener{
    var mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    private val REQUEST_SEND_SMS = 123
    var listView: ListView? = null
    var personName: EditText?= null
    var searchName: String = ""

    var passPhoneNumber = ""
    var passName = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.oiconfigure_layout)
        // Find the widget id from the intent.
        val intent: Intent = intent
        val extras = intent.extras
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
        }
        findViewById<Button>(R.id.saveButton).setOnClickListener(this)
        personName = findViewById(R.id.editTextPhone)
        listView = findViewById(R.id.list)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.SEND_SMS, Manifest.permission.READ_CONTACTS),
                    REQUEST_SEND_SMS)
        }
        personName!!.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable) {
                Log.i("MAIN", "Text changed")
                searchName = personName!!.text.toString()
                contacts
            }
            override fun beforeTextChanged(s: CharSequence, start:Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start:Int, before: Int, count: Int) {}
        });
        listView!!.setOnItemClickListener(object : AdapterView.OnItemClickListener{
            override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                var vg = parent!![position] as ViewGroup
                passPhoneNumber = findViewById<TextView>(android.R.id.text1).text.toString()
                passName = vg.findViewById<TextView>(android.R.id.text2).text.toString()
            }
        })
    }
    override fun onClick(v: View?) {
        val context: Context = this@OiWidgetConfigure
        // Push widget update to surface with newly set prefix
        val appWidgetManager = AppWidgetManager.getInstance(context)
        // Make sure we pass back the original appWidgetId
        val resultValue = Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId)
        val msg = findViewById<EditText>(R.id.editTextMessage).text.toString()
        WidgetPrefsHelper.saveNamePref(this, mAppWidgetId, passName)
        WidgetPrefsHelper.saveNumberPref(this, mAppWidgetId, passPhoneNumber)
        WidgetPrefsHelper.saveMsgPref(this, mAppWidgetId, msg)
        setResult(RESULT_OK, resultValue)
        finish()
    }
    val contacts: Unit
        get() {
            // create cursor and query the data
            searchName = personName!!.text.toString()
            val uri: Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            val cursor = contentResolver.query(uri, null, "DISPLAY_NAME = '$searchName'", null, null)
            val data = arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val to = intArrayOf(android.R.id.text1, android.R.id.text2)
            val adapter = SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor, data, to, 1)
            listView!!.adapter = adapter
        }
}



