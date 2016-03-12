package com.fbteam.hi.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fbteam.hi.R;
import com.fbteam.hi.adapters.CategoryListAdapter;
import com.fbteam.hi.adapters.EditLinksAdapter;
import com.fbteam.hi.models.Category;

/**
 * Created by nik on 12/03/16.
 */

public class EditLinksActivity extends ActivityNavMenu implements View.OnClickListener {

    // list view
    private ListView links;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        loadNavDrawer(toolbar);
        findElements();
    }


    private void findElements(){

        /// set up list
        links = (ListView) findViewById(R.id.categoriesList);
        links.setAdapter(new EditLinksAdapter(this, R.layout.edit_link_row));
        links.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Category clickedShout = (Category) adapterView.getItemAtPosition(i);

            }
        });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id){
            case R.id.FAB_AddFriend:

                break;
        }
    }
}


