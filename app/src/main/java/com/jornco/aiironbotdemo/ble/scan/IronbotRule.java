package com.jornco.aiironbotdemo.ble.scan;

/**
 * Created by kkopite on 2017/12/24.
 */

public interface IronbotRule {
    boolean isRead(String uuid);
    boolean isWrite(String uuid);
}
