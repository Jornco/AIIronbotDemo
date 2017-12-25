package com.jornco.aiironbotdemo.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import com.jornco.aiironbotdemo.ble.common.BLELog;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;
import com.jornco.aiironbotdemo.ble.scan.IronbotFilter;
import com.jornco.aiironbotdemo.ble.scan.IronbotSearcherCallback;

/**
 * Created by kkopite on 2017/12/22.
 */

class A1BLEScan implements BluetoothAdapter.LeScanCallback {

    // 蓝牙适配器
    private BluetoothAdapter mAdapter;

    //扫描蓝牙的回调
    private IronbotSearcherCallback mCallback;
    private IronbotFilter mFilter;

    A1BLEScan() {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    /**
     * 扫描设备
     * @param callback 扫描设备的回调
     */
    void searchIronbot(IronbotSearcherCallback callback, IronbotFilter filter) {
        if (mAdapter == null || !mAdapter.isEnabled()) {
            BLELog.log("蓝牙不可用");
            return;
        }
        mFilter = filter;
        this.mCallback = callback;
        mAdapter.startLeScan(this);
    }

    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        if (mFilter.filter(device)) {
            String address = device.getAddress();
            String name = device.getName();
            if (mCallback != null) {
                mCallback.onIronbotFound(new IronbotInfo(name, address));
            }
        }
    }

    /**
     * 蓝牙是否可用
     * @return true if bluetooth is enabled
     */
    boolean isEnable(){
        return mAdapter != null && mAdapter.isEnabled();
    }

    /**
     * 打开蓝牙
     */
    void enable() {
        if (mAdapter != null) {
            mAdapter.enable();
        }
    }

    /**
     * 停止扫描
     */
    void stopScan(){
        mAdapter.stopLeScan(this);
        mCallback = null;
    }
}
