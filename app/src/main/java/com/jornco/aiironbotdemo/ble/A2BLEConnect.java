package com.jornco.aiironbotdemo.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;

/**
 * Created by kkopite on 2017/12/22.
 */

class A2BLEConnect extends BluetoothGattCallback {

    private static final String TAG = "A2BLEConnect";

    private BluetoothAdapter mAdapter;
    private BluetoothGatt mGatt;

    A2BLEConnect () {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    void connect(Context context, String address) {
        BluetoothDevice device = mAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.w(TAG, "device not found, Unable to connect");
            return;
        }
        mGatt = device.connectGatt(context, false, this);
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
        String address = gatt.getDevice().getAddress();
        if (newState == BluetoothProfile.STATE_CONNECTED) {
            BLELog.log(address + "连接成功");
        } else {
            BLELog.log(address + "连接断开");
        }
    }
}
