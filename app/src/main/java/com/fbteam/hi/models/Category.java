package com.fbteam.hi.models;

import android.content.Intent;

import com.fbteam.hi.ShowQRActivity;

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

    public static Category fromString(String string)
    {
        //public Link(String name, String content, boolean verified)
        String[] attribute = string.split(",");
        return new Category(attribute[0]);
    }

    public void addLink(Link link)
    {
        links.add(link);
        link.addCategory(this);
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {

        return name;
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer(name);
        for(Link link : this.links)
        {
            sb.append(link.getId()+"&");
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public ArrayList<Link> getLinks()
    {
        return links;
    }

    public Link getLinkByID(int id){
        return links.get(id);
    }


    public boolean containsLink(Link link){
        for (Link tempL : links)
            if(tempL.getName().equals(link.getName()))
                return true;
        return false;
    }
}
