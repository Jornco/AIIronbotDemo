package com.jornco.aiironbotdemo.activity.a14.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class A14MyService extends Service {
    public A14MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new A14SearchBinder();
    }
}
