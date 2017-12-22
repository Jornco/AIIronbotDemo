package com.jornco.aiironbotdemo.ble;

import android.bluetooth.BluetoothGatt;
import android.content.Context;

import com.jornco.aiironbotdemo.ble.device.IronbotInfo;

/**
 * Created by kkopite on 2017/12/22.
 */

public class A3IronbotService {

    private BluetoothGatt mGatt;
    private IronbotInfo mInfo;
    private A2BLEConnect mBlc = new A2BLEConnect();

    public A3IronbotService(IronbotInfo info) {
        mInfo = info;
    }

    public A3IronbotSession getSession(Context context) {
        String address = mInfo.getAddress();
        mGatt = mBlc.getConnection(context, address);
        A3IronbotSession session = new A3IronbotSession(mGatt);
        if (mGatt == null) {
            return null;
        } else {
            return session;
        }
    }
}
