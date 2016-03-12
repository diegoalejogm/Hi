package com.fbteam.hi.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by nik on 12/03/16.
 */

public class User {

    // static objects
    private String id;
    private String firstName;
    private String lastName;


    ArrayList<Link> links;
    ArrayList<Category> categories;

    public User(String id, String firstNames, String lastNames) {
        this.id = firstName + lastName;
        this.firstName = firstNames;
        this.lastName = lastNames;
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


    public void persist(SharedPreferences preferences)
    {
        int categoryCounter = 0;
        int linkCounter = 0;

        preferences.edit().putString("user", this.toPersistenceString());

        for(Link link: this.links)
        {
            preferences.edit().putString("link/"+linkCounter, link.toPersistenceString());
            StringBuffer categories = new StringBuffer();
            for(Category category : link.getCategories())
            {
                categories.append(category.getId() + ",");

            }
            categories.setLength(categories.length() - 1);
            preferences.edit().putString("link/"+linkCounter+"/categories",categories.toString());
        }

        for(Category category : this.categories)
        {
            preferences.edit().putString("category/"+categoryCounter, category.toString());
        }
    }

    private String toPersistenceString()
    {
        return id + "," + firstName + "," + lastName;
    }

    private void updateFromString(String string)
    {
        String[] userData = string.split(",");
        this.id = userData[0];
        this.firstName = userData[1];
        this.lastName = userData[2];
    }

    public void restore(SharedPreferences preferences)
    {
        String userValues = preferences.getString("user",null);

        // TODO: Throw exception
        if(userValues == null) return;
        else updateFromString(userValues);

        // Add categories
        String categoryString = null;
        int categoryCounter = 0;
        while( (categoryString =preferences.getString("category/"+ categoryCounter,null)) != null)
        {
            Category newCategory = Category.fromString(categoryString);
            this.categories.add(newCategory);

            categoryCounter++;
        }

        String linkString = null;
        int linkCounter = 0;

        // Add Links
        while( (linkString =preferences.getString("link/"+linkCounter,null)) != null)
        {
            Link newLink = Link.fromString(linkString);
            this.links.add(newLink);

            String categoriesString = preferences.getString("link/"+linkCounter+"/categories","");
            for(String categoryName : categoriesString.split(","))
            {
                Category c = findCategoryByName(categoryName);
                if(c == null) continue;
                c.addLink(newLink);
            }
            linkCounter++;
        }
    }

    public Category findCategoryByName(String name)
    {
        for(Category category : categories)
        {
            if(category.getName().equals(name)) return category;
        }
        return null;
    }

}
