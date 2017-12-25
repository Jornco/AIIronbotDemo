package com.jornco.aiironbotdemo.activity.a10;

import android.os.IBinder;

/**
 * Created by kkopite on 2017/12/25.
 */

public interface A10Search {

    boolean isEnable();

    void searchIronbot(IBinder callback);

    void enable();

    void stopScan();
}
