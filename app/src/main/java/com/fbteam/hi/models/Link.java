package com.fbteam.hi.models;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Diego on 3/12/16.
 */
public class Link
{
    private String id;
    private String name;
    private String content;
    private boolean verified;

    private ArrayList<Category> categories;

    public ArrayList<Category> getCategories()
    {
        return categories;
    }

    private int type;


    public String getName()
    {
        return name;
    }

    public String getContent()
    {
        return content;
    }

    public boolean getVerified()
    {
        return verified;
    }

    public String getId()
    {
        return id;
    }

    public int getType()
    {
        return type;
    }

    public Link(String name, String content, boolean verified)
    {
        this.id = name;
        this.name = name;
        this.content = content;
        this.verified = verified;

        this.categories = new ArrayList<>();
    }

    public String toStringEncoding(String encoding)
    {
        return name+encoding+content+encoding+verified;
    }

    public static Link fromStringEncoding(String string, String separator)
    {
        String[] attributes = string.split(separator);
        return new Link(attributes[0],attributes[1], attributes[2]=="true"? true: false);
    }

    public void addCategory(Category category)
    {
        categories.add(category);
    }
}
