package com.jornco.aiironbotdemo.ble.scan;

import android.bluetooth.BluetoothDevice;

/**
 * Created by kkopite on 2017/12/22.
 */

public interface IronbotFilter {

    /**
     *
     * @param info 扫描到的蓝牙设备
     * @return false 就过滤掉
     */
    boolean filter(BluetoothDevice info);
}
