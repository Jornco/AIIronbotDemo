package com.jornco.aiironbotdemo.activity.a8.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class A8MyService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return new A8MyBinder();
    }
}
