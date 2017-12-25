package com.jornco.aiironbotdemo.activity.a7;

import android.content.Context;

import com.jornco.aiironbotdemo.activity.a5.A5BLEService;
import com.jornco.aiironbotdemo.activity.a5.A5BLESession;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;

/**
 * Created by kkopite on 2017/12/24.
 */

public class A7ClientService {

    private A5BLEService mBLEService;
    private IronbotInfo mInfo;

    public A7ClientService(IronbotInfo info) {
        mInfo = info;
        mBLEService = A7IronbotSearcher.findService(info);
    }

    public IronbotInfo getInfo() {
        return mInfo;
    }

    public A7ClientSession getSession(Context context) {
        if (mBLEService == null) {
            return null;
        }
        A5BLESession session = mBLEService.getSession(context);
        return new A7ClientSession(session);
    }

}
