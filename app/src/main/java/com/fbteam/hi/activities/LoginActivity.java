package com.fbteam.hi.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.fbteam.hi.Configuration;
import com.fbteam.hi.R;

/**
 * Created by nik on 12/03/16.
 */

public class LoginActivity extends Activity implements View.OnClickListener  {


    private int[] activity_buttons = {
            R.id.doneButton,
            R.id.importFromFb,
            R.id.importFromContact
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // set Initial data
        setInitialData();
        // find all elements in the acitivty
        findActivityElements();
    }

    /**
     * Set initial data of the application
     */
    public void setInitialData(){
        // read if user is here the first time ever



        // Start to download user's data from API. Once complete callback to open the main Button

//        RequestCallback apiCallback = new RequestCallback() {
//            @Override
//            public void onRequestComplete(String result) {
//                System.out.println(result);
//                ApiUtils.parseAppData(result);
//                findViewById(R.id.doneButton).setVisibility(View.VISIBLE);
//            }
//        };
//
//        // download app data with the related callback
//        ApiUtils.getRequestWithCallBack(Configurations.REMOTE_SERVER_URL, apiCallback);
    }


    /**
     * Set up main elements/views inside the activity
     */
    public void findActivityElements() {
        // assign listener to all buttons in the activity
        for (int i = 0; i < activity_buttons.length; i++) {
            Button activity_button = (Button) this.findViewById(activity_buttons[i]);
            activity_button.setOnClickListener(this);
        }

        ImageView tempImageView = (ImageView) findViewById(R.id.nameImageView);
        tempImageView.getDrawable().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
        tempImageView = (ImageView) findViewById(R.id.passwordImageView);
        tempImageView.getDrawable().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
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
    public void onClick(View view) {
        System.out.println("button clicked " + view.getId());
        switch (view.getId()) {
            case R.id.offlineMode:
                openHomeActivity(Configuration.OFFLINE_MODE_APP);
                break;
            case R.id.importFromContact:     // import from contact address books
                importInformationFrom(Configuration.IMPORT_INFO_FROM_ADDRESSBOOK);
                openHomeActivity(Configuration.ONLINE_MODE_APP);
                break;
            case R.id.importFromFb:         // import from fb
                importInformationFrom(Configuration.IMPORT_INFO_FROM_FACEBOOK);
                openHomeActivity(Configuration.ONLINE_MODE_APP);
                break;
        }
    }

//    public void login(final int appMode){
//        // change UI
//        findViewById(R.id.usernameEditText).setEnabled(false);
//        findViewById(R.id.passwordEditText).setVisibility(View.GONE);
//        findViewById(R.id.doneButton).setEnabled(false);
//        openHomeActivity(appMode);
//    }

    public void importInformationFrom(int source){
        switch(source){
            case Configuration.IMPORT_INFO_FROM_ADDRESSBOOK:
                //contact create user

                break;
            case Configuration.IMPORT_INFO_FROM_FACEBOOK:
                //faceboook create user
                break;
        }
    }

    /**
     * Open main activityi after successful login
     * @param appMode
     */
    public void openHomeActivity(int appMode){
        Intent intent = new Intent(this, HomeActivity.class);
        String appModeStr = "" + appMode;
        intent.putExtra("com.example.myfirstapp.MODE", appModeStr);
        startActivity(intent);
        finish();
    }
}