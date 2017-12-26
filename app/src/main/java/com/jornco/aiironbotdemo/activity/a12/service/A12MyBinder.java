package com.jornco.aiironbotdemo.activity.a12.service;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.jornco.aiironbotdemo.activity.a8.A8IronbotSearcher;
import com.jornco.aiironbotdemo.ble.ISearchIronbot;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;
import com.jornco.aiironbotdemo.ble.scan.IronbotSearcherCallback;

/**
 * Created by kkopite on 2017/12/25.
 */

public class A12MyBinder extends ISearchIronbot.Stub implements IronbotSearcherCallback {

    private A8IronbotSearcher mSearcher = new A8IronbotSearcher();
    private IBinder mBinderCallback;

    @Override
    public void onIronbotFound(IronbotInfo info) {
        Parcel data2 = Parcel.obtain();
        Parcel reply2 = Parcel.obtain();
        data2.writeString(info.toXml());
        try {
            mBinderCallback.transact(0, data2, reply2, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onIronbotFound(String xml) {

    }

    @Override
    public boolean isEnable() throws RemoteException {
        return mSearcher.isEnable();
    }

    @Override
    public void searchIronbot(IBinder callback) throws RemoteException {
        mBinderCallback = callback;
        mSearcher.searchIronbot(this);
    }

    @Override
    public void enable() throws RemoteException {
        mSearcher.enable();
    }

    @Override
    public void stopScan() throws RemoteException {
        mSearcher.stopScan();
    }

    @Override
    public IBinder findService(String infoXml) throws RemoteException {
        return null;
    }

}
