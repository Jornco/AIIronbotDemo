package com.jornco.aiironbotdemo.ble;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/**
 * Created by kkopite on 2017/12/25.
 */

public class A11SearchProxy implements A11ISearch {

    private IBinder mBinder;
    private Parcel data = Parcel.obtain();
    private Parcel reply = Parcel.obtain();

    public A11SearchProxy(IBinder binder) {
        mBinder = binder;
    }

    @Override
    public boolean isEnable() {
        try {
            mBinder.transact(0, data, reply, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return reply.readByte() != 0;
    }

    @Override
    public void searchIronbot() {
        try {
            mBinder.transact(1, data, reply, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void enable() {
        try {
            mBinder.transact(2, data, reply, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stopScan() {
        try {
            mBinder.transact(3, data, reply, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
