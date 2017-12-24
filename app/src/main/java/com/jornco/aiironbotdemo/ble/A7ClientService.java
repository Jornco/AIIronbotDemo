package com.jornco.aiironbotdemo.ble;

import android.content.Context;

import com.jornco.aiironbotdemo.ble.device.IronbotInfo;

/**
 * Created by kkopite on 2017/12/24.
 */

public class A7ClientService {

    private A5BLEService mBLEService;

    public A7ClientService(IronbotInfo info) {
        mBLEService = A7IronbotSearcher.findService(info);
    }

    public A7ClientSession getSession(Context context) {
        A5BLESession session = mBLEService.getSession(context);
        A7ClientSession cs = new A7ClientSession(session);
        return cs;
    }

}
