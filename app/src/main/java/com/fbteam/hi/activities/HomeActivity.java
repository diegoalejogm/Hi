package com.fbteam.hi.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import android.widget.Toast;
import com.fbteam.hi.R;
import com.fbteam.hi.ShowQRActivity;
import com.fbteam.hi.adapters.CategoryListAdapter;
import com.fbteam.hi.helper.CaptureQRActivityAnyOrientation;
import com.fbteam.hi.models.App;
import com.fbteam.hi.models.Category;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.FileNotFoundException;
import java.io.InputStream;

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
        categoriesList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Category category = (Category) adapterView.getItemAtPosition(i);
                // create
                showQR(category);
            }
        });
    }


    public void showQR(Category category){
        Intent intent = new Intent(this, ShowQRActivity.class);
        intent.putExtra("categoryId", category.getId());
        startActivityForResult(intent, 9090);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id){
            case R.id.FAB_AddFriend:
                new IntentIntegrator(this)
                        .setCaptureActivity(CaptureQRActivityAnyOrientation.class)
                        .setBeepEnabled(true)
                        .setOrientationLocked(false)
                        .initiateScan();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);


        if(resultCode == Activity.RESULT_CANCELED) return;
        // QR Code Scan Cancelled
        else if(result.getContents() == null) {
            Log.d("MainActivity", "Cancelled scan");
            Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
        }
        // QR Code Scan Succeed
        else
        {
            Log.d("MainActivity", "Scanned");
            try
            {
                App.getMe().addContactFromQRString(result.getContents());
                Toast.makeText(this, "Success", Toast.LENGTH_LONG).show();
                Log.v("TTTT", App.getMe().getContacts().get(0).toStringEncoding());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


}
