package com.jornco.aiironbotdemo.ble.scan;

import com.jornco.aiironbotdemo.ble.device.IronbotInfo;

/**
 * Created by kkopite on 2017/12/22.
 */

public interface IronbotSearcherCallback {

    /**
     * 扫描得到的设备, 需要自己去重
     * @param info 蓝牙设备
     */
    void onIronbotFound(IronbotInfo info);

    void onIronbotFound(String xml);
}
