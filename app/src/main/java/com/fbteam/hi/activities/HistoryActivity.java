package com.fbteam.hi.activities;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fbteam.hi.R;
import com.fbteam.hi.adapters.CategoryListAdapter;
import com.fbteam.hi.adapters.ContactListAdapter;
import com.fbteam.hi.models.Category;
import com.fbteam.hi.models.Contact;

/**
 * Created by leticiawanderley on 13/03/2016.
 */
public class HistoryActivity extends ActivityNavMenu {

    ListView contactsList;
    private final int PICK_CONTACT = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadNavDrawer(toolbar);
        findElements();
    }

    private void findElements(){
        /// set up list
        contactsList = (ListView) findViewById(R.id.contactsList);
        contactsList.setAdapter(new ContactListAdapter(this, R.layout.contact_row));
        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                Contact contact = (Contact) adapterView.getItemAtPosition(i);
                AddressBookManager.openContact(HistoryActivity.this.getApplicationContext(), contact);

//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT, Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, contact.getId()));
//                intent.setType(ContactsContract.Contacts.CONTENT_ITEM_TYPE);
//                startActivityForResult(intent, PICK_CONTACT);
            }
        });
    }
    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
    }



}
