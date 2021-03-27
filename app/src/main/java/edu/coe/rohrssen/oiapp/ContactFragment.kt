package edu.coe.rohrssen.oiapp

import android.Manifest
import android.annotation.SuppressLint
import android.appwidget.AppWidgetManager
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader


@SuppressLint("InlinedApi")
private val FROM_COLUMNS: Array<String> = arrayOf(
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)) {
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        } else {
            ContactsContract.Contacts.DISPLAY_NAME
        }
)

private val TO_IDS: IntArray = intArrayOf(android.R.id.text1)


class ContactsFragment :
        Fragment(),
        LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemClickListener {
    // Define global mutable variables
    // Define a ListView object
    lateinit var contactsList: ListView

    // Define variables for the contact the user selects
    // The contact's _ID value
    var contactId: Long = 0

    // The contact's LOOKUP_KEY
    var contactKey: String? = null

    // A content URI for the selected contact
    var contactUri: Uri? = null

    // An adapter that binds the result Cursor to the ListView
    private val cursorAdapter: SimpleCursorAdapter? = null
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the fragment layout
        return inflater.inflate(R.layout.oiconfigure_layout, container, false)
    }




    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
        TODO("Not yet implemented")
    }

    override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
        TODO("Not yet implemented")
    }

    override fun onLoaderReset(loader: Loader<Cursor>) {
        TODO("Not yet implemented")
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("Not yet implemented")
    }
}