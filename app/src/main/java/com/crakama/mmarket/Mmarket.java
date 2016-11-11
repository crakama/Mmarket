package com.crakama.mmarket;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.firebase.client.Firebase;

/**
 * Created by crakama on 11/9/2016.
 */

public class Mmarket extends android.app.Application {
    @Override
    public void onCreate(){
        super.onCreate();

        Firebase.setAndroidContext(this);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

    }
}
