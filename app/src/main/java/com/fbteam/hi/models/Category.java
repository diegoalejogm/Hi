package com.fbteam.hi.models;

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
        this.name = name;
        links = new ArrayList<>();
    }

    public void addLink(Link link)
    {
        links.add(link);
    }

    public String getName()
    {
        return name;
    }

    public ArrayList<Link> getLinks()
    {
        return links;
    }
}
