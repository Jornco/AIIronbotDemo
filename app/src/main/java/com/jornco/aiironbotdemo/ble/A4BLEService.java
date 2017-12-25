package com.jornco.aiironbotdemo.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothProfile;
import android.content.Context;

import com.jornco.aiironbotdemo.ble.common.BLELog;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;

/**
 * Created by kkopite on 2017/12/24.
 */

public class A4BLEService extends BluetoothGattCallback {

    private IronbotInfo mInfo;
    private BluetoothAdapter mAdapter;
    private BluetoothGatt mGatt;

    public A4BLEService(IronbotInfo info) {
        mInfo = info;
        mAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    public IronbotInfo getInfo() {
        return mInfo;
    }

    public A4BLESession getSession(Context context) {
        String address = mInfo.getAddress();
        mGatt = getConnection(context, address);
        if (mGatt == null) {
            return null;
        }
        return new A4BLESession(mGatt, this);
    }

    private BluetoothGatt getConnection(Context context, String address) {
        BluetoothDevice device = mAdapter.getRemoteDevice(address);
        if (device == null) {
            BLELog.log("Device not found Unable to connect");
            mGatt = null;
        } else {
            mGatt = device.connectGatt(context, false, this);
            if (!mGatt.connect()) {
                mGatt = null;
            }
        }
        return mGatt;
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
        String address = mInfo.getAddress();
        if (newState == BluetoothProfile.STATE_CONNECTED) {
            BLELog.log(address + "连接成功");
        } else {
            BLELog.log(address + "连接断开");
            if (mGatt != null) {
                mGatt.close();
            }
        }
    }
}
