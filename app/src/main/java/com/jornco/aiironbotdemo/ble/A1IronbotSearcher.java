package com.jornco.aiironbotdemo.ble;

import android.bluetooth.BluetoothDevice;

import com.jornco.aiironbotdemo.ble.scan.IronbotFilter;
import com.jornco.aiironbotdemo.ble.scan.IronbotSearcherCallback;

/**
 * Created by kkopite on 2017/12/22.
 */

public class A1IronbotSearcher {

    private A1BLEScan mBLEScan = new A1BLEScan();

    private IronbotFilter mFilter = new IronbotFilter() {
        @Override
        public boolean filter(BluetoothDevice info) {
            String name = info.getName();
            return name != null && (name.equals("RS-BLE") || name.equals("PS-BLE") || name.startsWith("Tav") || name.startsWith("CC"));
        }
    };

    /**
     * 扫描设备
     * @param callback 扫描设备的回调
     */
    public void searchIronbot(IronbotSearcherCallback callback) {
        mBLEScan.searchIronbot(callback, mFilter);
    }

    /**
     * 停止扫描
     */
    public void stopScan(){
        mBLEScan.stopScan();
    }

    /**
     * 蓝牙是否可用
     * @return true if bluetooth is enabled
     */
    public boolean isEnable(){
        return mBLEScan.isEnable();
    }

    /**
     * 打开蓝牙
     */
    public void enable() {
        mBLEScan.enable();
    }

}
