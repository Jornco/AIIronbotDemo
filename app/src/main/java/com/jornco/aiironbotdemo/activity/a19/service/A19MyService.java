package com.jornco.aiironbotdemo.activity.a19.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class A19MyService extends Service {
    public A19MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new A19SearchBinder();
    }
}
