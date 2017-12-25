package com.jornco.aiironbotdemo.activity.a9.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class A9MyService extends Service {
    public A9MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new A9MyBinder();
    }
}
