package com.jornco.aiironbotdemo.activity.a16.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class A16MyService extends Service {
    public A16MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new A16SearchBinder();
    }
}
