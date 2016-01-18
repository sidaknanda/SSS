package com.android.sss;

import android.app.Application;
import android.content.Context;


public class SSSApplication extends Application {

    private static SSSApplication instance;

    public static SSSApplication getInstance() {
        return instance;
    }

    public static Context getAppContext() {
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
