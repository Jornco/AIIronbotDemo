package com.jornco.aiironbotdemo.ble.common;

import com.jornco.aiironbotdemo.ble.connect.OnIronbotWriteCallback;

/**
 * Created by kkopite on 2017/12/28.
 */

public interface ISend {

    void sendMsg(IronbotCode code, OnIronbotWriteCallback callback);
}
