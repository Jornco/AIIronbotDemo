package com.jornco.aiironbotdemo.activity.a13.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class A13MyService extends Service {
    public A13MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new A13MyBinder();
    }
}
