package com.fbteam.hi.activities;

import android.os.Bundle;
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
public class HistoryActivity extends ActivityNavMenu implements View.OnClickListener  {

    ListView contactsList;

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
        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                System.out.println("on item click" + i + " " + l + " " + view.getId());
                Contact contact = (Contact) adapterView.getItemAtPosition(i);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }
}
