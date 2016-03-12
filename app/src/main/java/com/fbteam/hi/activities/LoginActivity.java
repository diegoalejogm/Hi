package com.fbteam.hi.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.fbteam.hi.App;
import com.fbteam.hi.Configuration;
import com.fbteam.hi.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by nik on 12/03/16.
 */

public class LoginActivity extends Activity implements View.OnClickListener  {

    CallbackManager callbackManager;

    private int[] activity_buttons = {
            R.id.importFromFb,
            R.id.importFromContact
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_login);
        // set Initial data
        setInitialData();
        // find all elements in the activity
        findActivityElements();

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        // Application code
                                        String name = null;
                                        String email = null;
                                        String linkUri = null;
                                        try {
                                            name = object.getString("name");
                                            email = object.getString("email");
                                            linkUri = object.getString("linkUri");
                                            openHomeActivity(Configuration.IMPORT_INFO_FROM_FACEBOOK);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday,linkUri");
                        request.setParameters(parameters);
                        request.executeAsync();
                        System.out.println("LOGIN RESULT");
                    }

                    @Override
                    public void onCancel() {
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        System.out.println("ERROR");
                    }
                });
    }

    /**
     * Set initial data of the application
     */
    public void setInitialData(){
        // read if user is here the first time ever or restore

        SharedPreferences settings = getSharedPreferences(Configuration.DB_PREFERENCES, 0);
        boolean registered = settings.getBoolean("registered", true);

        if(registered){
//            App.getMe().restore();
        }

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

        TextView txt = (TextView) this.findViewById(R.id.offlineMode);
        txt.setOnClickListener(this);
        LoginButton loginButton = (LoginButton) this.findViewById(R.id.importFromFb);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
//        ImageView tempImageView = (ImageView) findViewById(R.id.nameImageView);
//        tempImageView.getDrawable().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
//        tempImageView = (ImageView) findViewById(R.id.passwordImageView);
//        tempImageView.getDrawable().setColorFilter(getResources().getColor(R.color.primary), PorterDuff.Mode.SRC_ATOP);
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
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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