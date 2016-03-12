package com.fbteam.hi.models;

import android.content.SharedPreferences;

import java.util.ArrayList;

/**
 * Created by nik on 12/03/16.
 */

public class Contact
{
    // static objects
    private String id;
    private String firstName;
    private String lastName;

    ArrayList<Link> links;


    public Contact(String firstName, String lastName, ArrayList<Link> links)
    {
        this.id = firstName + lastName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.links = links;
    }

    public String toStringEncoding()
    {
        StringBuffer sb = new StringBuffer();
        sb.append(this.firstName+","+this.lastName);
        for(Link link : this.links)
        {
            sb.append(link.toStringEncoding(",")+",");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public static Contact fromStringEncoding(String string)
    {
        String[] data = string.split("&");

        String firstName = "", lastName = "";
        ArrayList<Link> links = new ArrayList<>();

        if(data.length >= 1)
        {
            String[] names = data[0].split(",");
            firstName = names[0];
            lastName = names[1];
        }
        if(data.length >=2)
        {
            String[] linkData = data[1].split(";");
            for(String linkString : linkData)
            {
                Link newLink = Link.fromStringEncoding(linkString, ",");
                links.add(newLink);
            }
        }
        return new Contact(firstName,lastName,links);
    }

    public Contact(){
        links = new ArrayList<Link>();
    }

    public ArrayList<Link> getLinks()
    {
        return links;
    }

    public static Contact fromUserAndCategory(User user, Category category)
    {
        return new Contact(user.getFirstName(), user.getLastName(), category.getLinks());
    }
}
