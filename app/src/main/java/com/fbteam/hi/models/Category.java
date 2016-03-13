package com.fbteam.hi.models;

import android.content.Intent;

import com.fbteam.hi.ShowQRActivity;

import java.security.SecurityPermission;
import java.util.ArrayList;

/**
 * Created by Diego on 3/12/16.
 */
public class Category
{

    private String id;
    private String name;
    private ArrayList<Link> links;

    public Category(String name)
    {
        this.id = name;
        this.name = name;
        links = new ArrayList<>();
    }


    public String getId()
    {
        return id;
    }

    public String getName() {

        return name;
    }

    public ArrayList<Link> getLinks()
    {
        return links;
    }


    public void setNewTitle(String title){
        this.name = title;
    }
    public void addLink(Link link)
    {
        if(!isLinkInCategory(link)) {
            links.add(link);
            link.addCategory(this);
        }
    }

    public void removeLink(Link link)
    {
        Link remove = null;
        for(Link tempL : links)
            if(tempL.getName().equals(link.getName()))
                remove = tempL;
        if(remove != null) {
            links.remove(remove);
            link.removeFromCategory(this);
        }
    }



    // UTILS


    public static Category fromStringEncoding(String string, String separator)
    {
        //public Link(String name, String content, boolean verified)
        String[] attribute = string.split(separator);
        return new Category(attribute[0]);
    }


    public boolean containsLink(Link link){
        for (Link tempL : links)
            if(tempL.getName().equals(link.getName()))
                return true;
        return false;
    }


    public Link getLinkByID(int id){
        return links.get(id);
    }

    public String toStringEncoding(String separator) {
        StringBuffer sb = new StringBuffer(name+separator);
        for (Link link : this.links) {
            sb.append(link.getId() + separator);
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public boolean isLinkInCategory(Link link){
        for (Link tempL : this.links) {
            if(tempL.getName().equals(link.getName()))
                return true;
        }
        return false;
    }
}
