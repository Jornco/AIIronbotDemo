package com.jornco.aiironbotdemo.activity.a21.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class A21MusicService extends Service {
    public A21MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new A21MusicBinder();
    }
}
