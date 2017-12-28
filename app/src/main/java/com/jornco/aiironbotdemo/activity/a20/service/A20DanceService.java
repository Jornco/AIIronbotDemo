package com.jornco.aiironbotdemo.activity.a20.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class A20DanceService extends Service {
    public A20DanceService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new A20DanceBinder();
    }
}
