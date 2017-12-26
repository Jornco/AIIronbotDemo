package com.jornco.aiironbotdemo.activity.a13;

import android.bluetooth.BluetoothGattCallback;

import com.jornco.aiironbotdemo.ble.device.IronbotInfo;

/**
 * Created by kkopite on 2017/12/26.
 */

public class A13BLEService extends BluetoothGattCallback {

    private IronbotInfo mInfo;
    private String mAddress;

    public A13BLEService(IronbotInfo info) {
        mInfo = info;
        mAddress = info.getAddress();
    }

    public IronbotInfo getInfo() {
        return mInfo;
    }
}
