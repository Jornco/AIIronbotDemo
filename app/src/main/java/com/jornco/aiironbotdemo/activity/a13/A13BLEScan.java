package com.jornco.aiironbotdemo.activity.a13;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import com.jornco.aiironbotdemo.ble.common.BLELog;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;
import com.jornco.aiironbotdemo.ble.scan.IronbotFilter;
import com.jornco.aiironbotdemo.ble.scan.IronbotSearcherCallback;

/**
 * Created by kkopite on 2017/12/26.
 */

public class A13BLEScan implements BluetoothAdapter.LeScanCallback {

    private BluetoothAdapter mAdapter = BluetoothAdapter.getDefaultAdapter();
    private IronbotSearcherCallback mCallback;
    private IronbotFilter mFilter;

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
            A13BLEService srv = new A13BLEService(info);
            A13IronbotSearcher.mServiceList.put(address, srv);
            if (mCallback != null) {
                mCallback.onIronbotFound(info);
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
