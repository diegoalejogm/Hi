package com.fbteam.hi.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.fbteam.hi.R;
import com.fbteam.hi.helper.CaptureQRActivityAnyOrientation;
import com.fbteam.hi.models.App;
import com.google.zxing.integration.android.IntentIntegrator;

/**
 * Created by nik on 12/03/16.
 */

public class ActivityNavMenu extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    protected void loadNavDrawer(Toolbar toolbar){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.nav_scan_user:
                new IntentIntegrator(ActivityNavMenu.this)
                        .setCaptureActivity(CaptureQRActivityAnyOrientation.class)
                        .setBeepEnabled(true)
                        .setOrientationLocked(false)
                        .initiateScan();
                break;
            case R.id.nav_edit_links:
                App.openActivity(this, LinksEditActivity.class);
                break;
            case R.id.nav_edit_categories:
                App.openActivity(this, CategoryEditActivity.class);
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }
}
