package com.jornco.aiironbotdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.jornco.aiironbotdemo.ble.A11MyBinder;

public class A11MyService extends Service {
    public A11MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new A11MyBinder();
    }
}
