package com.flipkart.newsapp.config;

import android.content.Context;
import android.content.SharedPreferences;

import com.flipkart.newsapp.network.request.common.NetworkRequestQueue;

/**
 * Created by manish.patwari on 5/8/15.
 */
public class AppPreferences {

    private static final String PACKAGE_NAME = "com.flipkart.newsapp";

    // Google Console APIs developer key
    public static final String YOUTUBE_DEVELOPER_KEY = "AIzaSyCwOnp5WDS9OD8P5zHoqynm0t8cbkT-GDo";

    private static AppPreferences instance;
    private SharedPreferences sharedPreferences;
    public static String FLICKR_API_URL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=5d96cc677f6446044c8bd880b943549b&per_page=30&format=json&nojsoncallback=1";

    private AppPreferences(){};

    public static AppPreferences getInstance(){
        if(instance == null){
            synchronized (AppPreferences.class){
                if(instance == null){
                    instance = new AppPreferences();
                }
            }
        }
        return instance;
    }

    public AppPreferences initialize(Context context) {
        sharedPreferences = context.getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE);

        //Start Volley
        NetworkRequestQueue.getInstance().initialize(context);

        return instance;
    }

    private static String getKey(String initialKey){
        return PACKAGE_NAME + "." + initialKey;
    }

    private final String  EMAIL_ID                      =   getKey("emailId");
    private final String  USER_NAME                     =   getKey("userName");


    public Boolean isInitialized(){
        if(instance != null && sharedPreferences!= null)
        {
            return  true;
        }
        return false;
    }
    public void saveEmailId(String emailId) {
        if(!isInitialized() || emailId == null || emailId.trim() == "") return;
        this.sharedPreferences.edit().putString(EMAIL_ID, emailId).commit();
    }


    public String getEmailId() {
        if(!isInitialized()) return null;
        return this.sharedPreferences.getString(EMAIL_ID, null);
    }

    public void saveUserName(String userName) {
        if(!isInitialized() || userName == null || userName.trim() == "") return;
        this.sharedPreferences.edit().putString(USER_NAME, userName).commit();
    }

    public String getUserName() {
        if(!isInitialized()) {
            return null;
        }
        return this.sharedPreferences.getString(USER_NAME, "Guest");
    }

    public void destroy()
    {
        instance = null;
        sharedPreferences = null;
    }


}
