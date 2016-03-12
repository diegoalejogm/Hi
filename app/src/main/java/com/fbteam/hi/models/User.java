package com.fbteam.hi.models;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by nik on 12/03/16.
 */

public class User {

    // static objects
    private String id;

    ArrayList<Link> links;
    ArrayList<Category> categories;

    public User(){
        links = new ArrayList<Link>();
        categories = new ArrayList<Category>();
    }

    public User(String id, ArrayList<Link> links, ArrayList<Category> categories) {
        this.id = id;
        this.links = links;
        this.categories = categories;
    }

    public User(String id) {
        this.id = id;
        this.links = new ArrayList<>();
        this.categories = new ArrayList<>();
    }

    public ArrayList<Link> getLinks()
    {
        return links;
    }

    public ArrayList<Category> getCategories()
    {
        return categories;
    }


    public void restore()
    {

    }

}
