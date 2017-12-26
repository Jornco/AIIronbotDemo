package com.jornco.aiironbotdemo.activity.a15.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class A15MyService extends Service {
    public A15MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new A15SearchBinder();
    }
}
