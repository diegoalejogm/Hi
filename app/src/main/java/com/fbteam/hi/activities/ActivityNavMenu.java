package com.fbteam.hi.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.fbteam.hi.Configuration;

import com.fbteam.hi.R;
import com.fbteam.hi.helper.CaptureQRActivityAnyOrientation;
import com.fbteam.hi.models.App;
import com.fbteam.hi.models.Category;
import com.fbteam.hi.models.Link;
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

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        View headerView = navigationView.getHeaderView(0);
        TextView tv = (TextView) headerView.findViewById(R.id.userNameTextView);
        tv.setText(App.getMe().getFullName());

        tv = (TextView) headerView.findViewById(R.id.emailTextView);
        Link email = App.getMe().getFirstEmail();
        if(email != null) tv.setText(email.getContent());
        else tv.setText("");

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
                App.openActivity(this, EditLinksActivity.class);
                break;
            case R.id.nav_new_category:
                Category newCategory = new Category("New");
                App.getMe().getCategories().add(newCategory);
                int categoryID = App.getMe().getCategories().size() - 1;
                clickOnAndEditCategory(categoryID);
                break;
            case R.id.nav_history:
                App.openActivity(this, HistoryActivity.class);
                break;
            case R.id.nav_logout:
                SharedPreferences settings = getSharedPreferences(Configuration.DB_PREFERENCES, 0);
                settings.edit().putBoolean("registered", false).commit();
                LoginManager.getInstance().logOut();
                App.openActivity(this, LoginActivity.class);
                finish();
                break;

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }


    protected void clickOnAndEditCategory(int id){
        System.out.println("category pro-clicked " + id);
        Intent intent = new Intent(this, EditCategoryActivity.class);
        intent.putExtra(Configuration.CATEGORY_ID, id);
        startActivityForResult(intent, 9090);
    }
}
