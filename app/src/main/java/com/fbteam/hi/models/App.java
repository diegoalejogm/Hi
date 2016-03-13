package com.fbteam.hi.models;

import android.app.Activity;
import android.content.Intent;

import com.fbteam.hi.models.User;

/**
 * Created by nik on 12/03/16.
 */

public class App {

    private static User myself;

    public static User getMe(){
        return myself;
    }



    public static void setCurrentUser(User user){
        myself = user;
    }


    /**
     * Open activitiy
     * @param act
     * @param newIntentClass
     */
    public static void openActivity(Activity act, Class newIntentClass){
        Intent newIntent = new Intent(act, newIntentClass);
        act.startActivity(newIntent);
    }

}
