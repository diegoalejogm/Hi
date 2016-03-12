package com.fbteam.hi.models;

import android.graphics.Bitmap;

import java.util.Calendar;

/**
 * Created by nik on 12/03/16.
 */

public class User {

    private String name;
    // static objects
    private String id;
    private String username;
    private String password;
    private String email;
    private String nameAndSurname;
    private String[] friendsIDs;
    private String[] interests;
    private Calendar createdDt;

    // extra
    private Bitmap imageProfile;


    public User(String id, String username, String email, String nameAndSurname, String password, String[] friendsIDs, String[] interests) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.nameAndSurname = nameAndSurname;
        this.password = password;
    }


}
