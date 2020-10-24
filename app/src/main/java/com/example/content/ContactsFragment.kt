package com.example.content

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ContactsFragment : Fragment() {

    private var contacts: ArrayList<Contact> = ArrayList()

    private lateinit var getContacts: Button

    companion object {
        fun newInstance() = ContactsFragment()
        const val REQUEST_CODE = 1;
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView: View = inflater.inflate(R.layout.fragment_contacts, container, false)
        getContacts = mView!!.findViewById<Button>(R.id.getContactsBtn)
        getContacts.setOnClickListener{
            if (checkContactsPermission()) {
                getContacts()
            } else {
                askPermission(Manifest.permission.READ_CONTACTS)
            }
        }

        return mView
    }

    private fun askPermission(vararg permissions: String) {
        requestPermissions(permissions, REQUEST_CODE)
    }

    private fun checkContactsPermission(): Boolean {
        return PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_CONTACTS
        )
    }

    private fun getContacts() {
        val uri: Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val cursor: Cursor? = context?.contentResolver?.query(uri, null, null, null, null)

        if (cursor != null) {
            while (cursor.moveToNext()) {
                val name: String = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                val number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                contacts.add(Contact(name, number))
            }
        }

        cursor?.close()


        val contactsRecycler = view!!.findViewById<RecyclerView>(R.id.contactsRecycler)
        contactsRecycler.layoutManager = LinearLayoutManager(activity)

        val mContactsAdapter = ContactsAdapter(contacts)
        contactsRecycler.adapter = mContactsAdapter
    }

}