package com.fbteam.hi.models;

/**
 * Created by Diego on 3/12/16.
 */
public class Link
{
    private String id;
    private String name;
    private String content;
    private String verified;

    private int type;


    public Link(String name, String content, String verified)
    {
        this.name = name;
        this.content = content;
        this.verified = verified;
    }

}
