package com.jornco.aiironbotdemo.util;

import com.jornco.aiironbotdemo.ble.common.BLEWriterError;
import com.jornco.aiironbotdemo.ble.common.ISend;
import com.jornco.aiironbotdemo.ble.common.IronbotCode;
import com.jornco.aiironbotdemo.ble.connect.OnIronbotWriteCallback;

import java.util.List;

/**
 * 发送一套动作的帮助类
 * Created by kkopite on 2017/12/28.
 */

public class ActionSendHelper {

    private ISend mSend;
    private List<String> mData;
    private int index;

    public ActionSendHelper(ISend send, List<String> data) {
        mSend = send;
        mData = data;
        index = 0;
    }

    public void start() {
        next();
    }

    private void next() {
        if (mData == null || mData.size() == 0) {
            return;
        }
        if (index >= mData.size()) {
            return;
        }
        String cmd = mData.get(index);
        index++;
        mSend.sendMsg(IronbotCode.create(cmd), new OnIronbotWriteCallback() {
            @Override
            public void onWriterSuccess(String address) {

            }

            @Override
            public void onWriterFailure(String address, BLEWriterError error) {

            }

            @Override
            public void onAllDeviceFailure() {

            }

            @Override
            public void onWriterEnd() {
                next();
            }
        });
    }

    public void stop(){
        if (mData != null) {
            mData.clear();
        }
        mData = null;
    }
}
