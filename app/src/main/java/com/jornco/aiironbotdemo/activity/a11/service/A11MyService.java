package com.jornco.aiironbotdemo.activity.a11.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class A11MyService extends Service {
    public A11MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new A11MyBinder();
    }
}
