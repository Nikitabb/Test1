package com.example.contacts

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val REQUEST_CODE_READ_CONTACTS = 1
    private var  READ_CONTACTS_GRANTED = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val AcceptReadContacts = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
        if (AcceptReadContacts == PackageManager.PERMISSION_GRANTED){
            READ_CONTACTS_GRANTED = true
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CODE_READ_CONTACTS)

        }
        if(READ_CONTACTS_GRANTED){
            loadContacts()
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permission: Array<String?>, grantResults: IntArray){
      when(requestCode){
        REQUEST_CODE_READ_CONTACTS -> {
            if(grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
             loadContacts()
            }else{
                Toast.makeText(this, "Разрешение не выдано", Toast.LENGTH_LONG).show()
            }
            return
        }
      }
    }
    private fun loadContacts(){
        val cursor: Cursor? = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null)
        startManagingCursor(cursor)
        val  from = arrayOf(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone._ID)
        val to = intArrayOf(android.R.id.text1, android.R.id.text2)
        val simple = SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor,from,to)
        lv_contacts.adapter = simple
    }
}
