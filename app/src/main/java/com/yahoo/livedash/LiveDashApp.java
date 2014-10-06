package com.yahoo.livedash;

import android.content.Context;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.parse.Parse;
import com.parse.ParseObject;

/*
 * This is the Android application itself and is used to configure various settings
 * including the image cache in memory and on disk. This also adds a singleton
 * for accessing the relevant rest client.
 * 
 *     TwitterRestClient client = RestClientApp.getRestClient();
 *     // use client to send requests to API
 *     
 */
public class LiveDashApp extends com.activeandroid.app.Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        registerParse();
        LiveDashApp.context = this;

        // Create global configuration and initialize ImageLoader with this configuration
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().
                cacheInMemory().cacheOnDisc().build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
    }

    private void registerParse() {
        Parse.initialize(this, "Sai0SExU32AhrAU6brTVg0cEprj79Wuhg0bodOpB", "4zy9vfOapR4rIUN43M2XFYXJuHQ38LgOlkL0UN9V");

        ParseObject testObject = new ParseObject("Ping");
        testObject.put("name", "josephr");
        testObject.saveInBackground();
    }

}