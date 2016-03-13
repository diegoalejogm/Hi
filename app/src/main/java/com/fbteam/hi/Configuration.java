package com.fbteam.hi;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by nik on 12/03/16.
 */

public class Configuration {

    public static final int ONLINE_MODE_APP = 0;
    public static final int OFFLINE_MODE_APP = 1;

    public static final int IMPORT_INFO_FROM_FACEBOOK = 0;
    public static final int IMPORT_INFO_FROM_ADDRESSBOOK = 1;

    // storage
    public static final String DB_PREFERENCES = "MyPreferences";
    public static final String CATEGORY_ID = "CategoryID";

    public static final int TYPE_LINK_PHONE = 0;
    public static final int TYPE_LINK_WEBSITE =  1;
    public static final int TYPE_LINK_EMAIL =  2;

    public static final Map<String, String> links;
    static {
        Map<String, String> helper = new HashMap<String, String>();
        helper.put("Facebook", "https://facebook.com/");
        helper.put("Instagram", "https://instagram.com/");
        helper.put("Github", "https://github.com/");
        helper.put("Linkedin", "https://linkedin.com/in/");
        helper.put("Twitter", "https://twitter.com/");
        helper.put("Snapchat", "https://snapchat.com/add/");
        helper.put("Google+", "https://plus.google.com/");
        helper.put("Pinterest", "https://pinterest.com/");
        helper.put("Spotify", "https://play.spotify.com/user/");
        helper.put("Youtube", "https://youtube.com/channel/");
        helper.put("Tumblr", "https://tumblr.com/blog/");
        helper.put("Medium", "https://medium.com/@");
        links = Collections.unmodifiableMap(helper);
    }


    public static final Map<String, Integer> linksPics;
    static {
        Map<String, Integer> helper = new HashMap<String, Integer>();
        helper.put("facebook", R.mipmap.fb);
        helper.put("instagram",R.mipmap.instagram);
        helper.put("github", R.mipmap.github);
        helper.put("linkedin", R.mipmap.linkedin);
        helper.put("twitter",R.mipmap.twitter);
        helper.put("snapchat", R.mipmap.snapchat);
        helper.put("google",  R.mipmap.google);
        helper.put("pinterest", R.mipmap.pinterest);
        helper.put("spotify", R.mipmap.spotify);
        helper.put("youtube", R.mipmap.youtube);
        helper.put("tumblr",  R.mipmap.tumblr);
        helper.put("medium",  R.mipmap.medium);
        linksPics = Collections.unmodifiableMap(helper);
    }
}
