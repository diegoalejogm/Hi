package com.fbteam.hi.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import com.fbteam.hi.Configuration;

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
    }

    public void addTestData()
    {
        // TESTING
        this.firstName = "Mykola";
        this.lastName= "Schevchenko";
        Link link0 = new Link("Mobile1", "12345678",Configuration.TYPE_LINK_PHONE, false);
        Link link1 = new Link("Facebook", "http://niksheva.com",Configuration.TYPE_LINK_WEBSITE, false);
        Link link2 = new Link("Email", "niksheva@hi.com",Configuration.TYPE_LINK_EMAIL, false);
        Category c1 = new Category("Personal");
        c1.addLink(link0);
        c1.addLink(link1);
        c1.addLink(link2);
        Category c2 = new Category("Professional");
        c2.addLink(link2);

        links.add(link0);
        links.add(link1);
        links.add(link2);

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

    public void addLinkNoCategory(Link newLink) {
        this.links.add(newLink);
    }

    public void persist(SharedPreferences preferences)
    {
        SharedPreferences.Editor editor=preferences.edit();
        int categoryCounter = 0;
        int linkCounter = 0;

        String separator = ",";

        editor.putString("user", this.toStringEncoding(separator));

        for(Link link: this.links)
        {
            editor.putString("link/" + linkCounter, link.toStringEncoding(separator));
            StringBuffer categories = new StringBuffer();
            for(Category category : link.getCategories())
            {
                categories.append(category.getId() + separator);
            }
            int length = (categories.length() == 0) ? 0 : categories.length() - 1;
            categories.setLength(length);
            editor.putString("link/" + (linkCounter++) + "/categories", categories.toString());
        }

        for(Category category : this.categories)
        {
            editor.putString("category/" + (categoryCounter++), category.toStringEncoding(separator));
        }

        editor.putBoolean("registered", true);
        editor.commit();
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Contact addContactFromQRString(String contents)
    {
        Contact c = Contact.fromStringEncoding(contents);
        contacts.add(c);
        return c;
    }

    public String toQRRepresentationWithCategory(Category c)
    {
        return Contact.fromUserAndCategory(this, c).toStringEncoding();
    }

    public String getFullName()
    {
        return firstName + ((!lastName.equals(""))? " " + lastName:"");
    }

    public Link getFirstEmail()
    {
        for (Link l : this.links)
        {
            if(l.getType() == Configuration.TYPE_LINK_EMAIL) return l;
        }
        return null;
    }
}
