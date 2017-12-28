package com.jornco.aiironbotdemo.activity.a18.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class A18MyService extends Service {
    public A18MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new A18SearchBinder();
    }
}
