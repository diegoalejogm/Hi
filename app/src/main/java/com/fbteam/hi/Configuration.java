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

}
