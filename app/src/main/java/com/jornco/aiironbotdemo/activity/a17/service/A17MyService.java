package com.jornco.aiironbotdemo.activity.a17.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class A17MyService extends Service {
    public A17MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new A17SearchBinder();
    }
}
