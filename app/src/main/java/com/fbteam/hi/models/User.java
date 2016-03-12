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

    private static final String ATTRIBUTE_SEPARATOR = ",";

    // static objects
    private String id;
    private String firstName;
    private String lastName;

    ArrayList<Link> links;
    ArrayList<Category> categories;
    ArrayList<Contact> contacts;


    public User(){
        links = new ArrayList<>();
        categories = new ArrayList<>();
        contacts = new ArrayList<>();

        //public Link(String name, String content, boolean verified)

        // TESTING
        this.firstName = "Mykola";
        this.lastName= "Schevchenko";
        Link link1 = new Link("facebook", "niksheva", false);
        Link link2 = new Link("instagram", "niksheva", false);
        Link link3 = new Link("github", "nshevchenko", false);
        Category c1 = new Category("Personal");
        c1.addLink(link1);
        c1.addLink(link2);
        Category c2 = new Category("Professional");
        c2.addLink(link2);
        c2.addLink(link3);

        links.add(link1);
        links.add(link2);
        links.add(link3);

        categories.add(c1);
        categories.add(c2);
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

        String separator = ",";

        preferences.edit().putString("user", this.toStringEncoding(separator));

        for(Link link: this.links)
        {
            preferences.edit().putString("link/"+linkCounter, link.toStringEncoding(separator));
            StringBuffer categories = new StringBuffer();
            for(Category category : link.getCategories())
            {
                categories.append(category.getId() + separator);
            }
            categories.setLength(categories.length() - 1);
            preferences.edit().putString("link/" + (linkCounter++) + "/categories", categories.toString());
        }

        for(Category category : this.categories)
        {
            preferences.edit().putString("category/" + (categoryCounter++), category.toStringEncoding(separator));
        }
    }

    private String toStringEncoding(String separator)
    {
        return id + separator + firstName + separator + lastName;
    }

    private void updateFromString(String string)
    {
        String separator = this.ATTRIBUTE_SEPARATOR;
        String[] userData = string.split(separator);
        this.id = userData[0];
        this.firstName = userData[1];
        this.lastName = userData[2];
    }

    public void restore(SharedPreferences preferences)
    {
        String userValues = preferences.getString("user", null);

        // TODO: Throw exception
        if(userValues == null) return;
        else updateFromString(userValues);

        // Add categories
        String categoryString = null;
        int categoryCounter = 0;
        while( (categoryString =preferences.getString("category/"+ categoryCounter,null)) != null)
        {
            Category newCategory = Category.fromStringEncoding(categoryString, this.ATTRIBUTE_SEPARATOR);
            this.categories.add(newCategory);

            categoryCounter++;
        }

        String linkString = null;
        int linkCounter = 0;

        // Add Links
        while( (linkString =preferences.getString("link/"+linkCounter,null)) != null)
        {
            Link newLink = Link.fromStringEncoding(linkString, this.ATTRIBUTE_SEPARATOR);
            this.links.add(newLink);

            String categoriesString = preferences.getString("link/"+linkCounter+"/categories","");
            for(String categoryName : categoriesString.split(this.ATTRIBUTE_SEPARATOR))
            {
                Category c = findCategoryByName(categoryName);
                if(c == null) continue;
                c.addLink(newLink);
            }
            linkCounter++;
        }
    }

    public String getId()
    {
        return id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public ArrayList<Contact> getContacts()
    {
        return contacts;
    }

    public Category findCategoryByName(String name)
    {

        for(Category category : categories)
        {
            if(category.getName().equals(name)) return category;
        }
        return null;

    }

    public void addContactFromQRString(String contents)
    {
        contacts.add(Contact.fromStringEncoding(contents));
    }

    public String toQRRepresentationWithCategory(Category c)
    {
        return Contact.fromUserAndCategory(this, c).toStringEncoding();
    }
}
