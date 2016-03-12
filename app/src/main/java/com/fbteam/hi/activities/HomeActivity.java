package com.fbteam.hi.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fbteam.hi.R;
import com.fbteam.hi.adapters.CategoryListAdapter;
import com.fbteam.hi.helper.CaptureQRActivityAnyOrientation;
import com.fbteam.hi.models.App;
import com.fbteam.hi.models.Category;
import com.google.zxing.integration.android.IntentIntegrator;

public class HomeActivity extends ActivityNavMenu implements View.OnClickListener  {

    // list view
    private ListView categoriesList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        loadNavDrawer(toolbar);
        findElements();
    }





    private void findElements(){
        // Add friend button (FAB)
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.FAB_AddFriend);
        fab.setOnClickListener(this);

        /// set up list
        categoriesList = (ListView) findViewById(R.id.categoriesList);
        categoriesList.setAdapter(new CategoryListAdapter(this, R.layout.category_row));
        categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Category clickedShout = (Category) adapterView.getItemAtPosition(i);
                // create
//                clickedShout.createQRCode();


//                FragmentShoutDetail fragmentShoutDetail = new FragmentShoutDetail();
//                fragmentShoutDetail.setShoutDetail(clickedShout);
//
//                fragmentShoutDetail.show(getFragmentManager(), "Shout detail");
            }
        });

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id){
            case R.id.FAB_AddFriend:
                 new IntentIntegrator(HomeActivity.this)
                         .setCaptureActivity(CaptureQRActivityAnyOrientation.class)
                         .setBeepEnabled(true)
                         .setOrientationLocked(false)
                         .initiateScan();
                break;
        }
    }


}
