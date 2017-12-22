package com.jornco.aiironbotdemo.ble;

import android.content.Context;

import com.jornco.aiironbotdemo.ble.device.IronbotInfo;

/**
 * Created by kkopite on 2017/12/22.
 */

public class A2IronbotConnect {

    private A2BLEConnect mBlc = new A2BLEConnect();

    public void connect(Context context, IronbotInfo info) {
        String address = info.getAddress();
        mBlc.connect(context, address);
    }
}
