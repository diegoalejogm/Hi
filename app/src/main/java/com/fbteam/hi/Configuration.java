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


    public static final int TYPE_LINK_PHONE = 0;
    public static final int TYPE_LINK_WEBSITE =  1;
    public static final int TYPE_LINK_NAME =  2;

    public static final Map<String, String> links;
    static {
        Map<String, String> helper = new HashMap<String, String>();
        helper.put("facebook", "https://facebook.com/");
        helper.put("instagram", "https://instagram.com/");
        helper.put("github", "https://github.com/");
        helper.put("linkedin", "https://linkedin.com/in/");
        helper.put("twitter", "https://twitter.com/");
        helper.put("snapchat", "https://snapchat.com/add/");
        helper.put("google+", "https://plus.google.com/");
        helper.put("pinterest", "https://pinterest.com/");
        helper.put("spotify", "https://play.spotify.com/user/");
        helper.put("youtube", "https://youtube.com/channel/");
        helper.put("tumblr", "https://tumblr.com/blog/");
        links = Collections.unmodifiableMap(helper);
    }

}
