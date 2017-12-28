package com.jornco.aiironbotdemo.activity.a18;

import android.bluetooth.BluetoothGatt;
import android.content.Context;

import com.jornco.aiironbotdemo.activity.a2.A2BLEConnect;
import com.jornco.aiironbotdemo.ble.common.BLELog;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;

/**
 * Created by kkopite on 2017/12/28.
 */

public class A18BLEService {

    private BluetoothGatt mGatt;
    private IronbotInfo mInfo;
    private A2BLEConnect mBlc = new A2BLEConnect();

    public A18BLEService(IronbotInfo info) {
        mInfo = info;
    }

    public IronbotInfo getInfo() {
        return mInfo;
    }

    public A18BLESession getSession(Context context) {
        String address = mInfo.getAddress();
        mGatt = mBlc.getConnection(context, address);
        A18BLESession session = new A18BLESession(mGatt, this);
        if (mGatt == null) {
            return null;
        } else {
            return session;
        }
    }
}
