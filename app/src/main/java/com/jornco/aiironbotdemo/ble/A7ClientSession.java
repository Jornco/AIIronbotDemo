package com.jornco.aiironbotdemo.ble;

/**
 * Created by kkopite on 2017/12/24.
 */

public class A7ClientSession {

    A5BLESession mBLESession;

    public A7ClientSession(A5BLESession BLESession) {
        mBLESession = BLESession;
    }

    public void sendMsg(IronbotCode code, OnIronbotWriteCallback callback) {
        mBLESession.sendMsg(code, callback);
    }
}
