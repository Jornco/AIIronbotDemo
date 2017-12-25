package com.jornco.aiironbotdemo.activity.a11.service;

import android.content.Intent;
import android.os.Binder;
import android.os.Parcel;
import android.os.RemoteException;

import com.jornco.aiironbotdemo.IApplication;
import com.jornco.aiironbotdemo.activity.a11.A11SearchActivity;
import com.jornco.aiironbotdemo.activity.a8.A8IronbotSearcher;
import com.jornco.aiironbotdemo.ble.common.BLELog;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;
import com.jornco.aiironbotdemo.ble.scan.IronbotSearcherCallback;

/**
 * Created by kkopite on 2017/12/25.
 */

public class A11MyBinder extends Binder implements IronbotSearcherCallback{

    private A8IronbotSearcher mSearcher = new A8IronbotSearcher();

    @Override
    public void onIronbotFound(IronbotInfo info) {
        BLELog.log("onIronbotFound: " + info.toString());
        Intent intent = new Intent(A11SearchActivity.EVENT_FIND_DEVICE);
        intent.putExtra("value", info.toXml());
        IApplication.getInstance().sendBroadcast(intent);
    }

    @Override
    public void onIronbotFound(String xml) {

    }

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        if (code == 0) {
            reply.writeByte((byte) (mSearcher.isEnable() ? 1 : 0));
            return true;
        } else if (code == 1) {
            mSearcher.searchIronbot(this);
            return true;
        } else if (code == 2) {
            mSearcher.enable();
            return true;
        } else if (code == 3) {
            mSearcher.stopScan();
            return true;
        } else return false;
    }
}
