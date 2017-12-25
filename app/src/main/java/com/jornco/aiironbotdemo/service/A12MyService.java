package com.jornco.aiironbotdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class A12MyService extends Service {
    public A12MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new A12MyBinder();
    }
}
