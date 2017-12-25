package com.jornco.aiironbotdemo;

import android.app.Application;

/**
 * Created by kkopite on 2017/12/25.
 */

public class IApplication extends Application {

    private static Application instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static Application getInstance() {
        return instance;
    }
}
