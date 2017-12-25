package com.jornco.aiironbotdemo.activity.a7;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import com.jornco.aiironbotdemo.activity.a5.A5BLEService;
import com.jornco.aiironbotdemo.ble.common.BLELog;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;
import com.jornco.aiironbotdemo.ble.scan.IronbotFilter;
import com.jornco.aiironbotdemo.ble.scan.IronbotSearcherCallback;

/**
 * Created by kkopite on 2017/12/24.
 */

public class A7BLEScan implements BluetoothAdapter.LeScanCallback {

    private BluetoothAdapter mAdapter;
    private IronbotSearcherCallback mCallback;
    private IronbotFilter mFilter;

    public A7BLEScan() {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    void searchIronbot(IronbotSearcherCallback callback, IronbotFilter filter) {
        if (mAdapter == null || !mAdapter.isEnabled()) {
            BLELog.log("藍芽不可用");
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
            IronbotInfo info = new IronbotInfo(name, address);
            A5BLEService srv = new A5BLEService(info);
            A7IronbotSearcher.mServices.put(address, srv);
            if (mCallback != null) {
                mCallback.onIronbotFound(info);
                // ??
//                mCallback.onIronbotFound(info.toXml());
            }
        }
    }

    boolean isEnable(){
        return mAdapter != null && mAdapter.isEnabled();
    }

    void enable() {
        if (mAdapter != null) {
            mAdapter.enable();
        }
    }
    void stopScan(){
        mAdapter.stopLeScan(this);
        mCallback = null;
    }
}
