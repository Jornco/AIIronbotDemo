package com.jornco.aiironbotdemo.ble;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.jornco.aiironbotdemo.ble.common.BLELog;

/**
 * Created by kkopite on 2017/12/22.
 */

class A2BLEConnect extends BluetoothGattCallback {

    private static final String TAG = "A2BLEConnect";

    private BluetoothAdapter mAdapter;
    private BluetoothGatt mGatt;
    private Context mContext;

    A2BLEConnect() {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    void connect(Context context, String address) {
        mContext = context;
        BluetoothDevice device = mAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.w(TAG, "device not found, Unable to connect");
            return;
        }
        mGatt = device.connectGatt(context, false, this);
    }

    public BluetoothGatt getConnection(Context context, String address) {
        mContext = context;
        BluetoothDevice device = mAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.w(TAG, "device not found, Unable to connect");
            mGatt = null;
            return null;
        }
        mGatt = device.connectGatt(context, false, this);
        if (mGatt.connect()) {
            return mGatt;
        } else {
            mGatt = null;
            return null;
        }
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);
        String address = gatt.getDevice().getAddress();
        String msg;
        if (newState == BluetoothProfile.STATE_CONNECTED) {
            msg = address + "连接成功";
        } else {
            msg = address + "连接失败";
            if (mGatt != null) {
                mGatt.close();
            }
        }
        BLELog.log(msg);

        // toast 提示一下
        final String tmp = msg;
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, tmp, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
