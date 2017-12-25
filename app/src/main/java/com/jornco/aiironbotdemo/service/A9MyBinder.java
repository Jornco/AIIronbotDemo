package com.jornco.aiironbotdemo.service;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.jornco.aiironbotdemo.ble.A8IronbotSearcher;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;
import com.jornco.aiironbotdemo.ble.scan.IronbotSearcherCallback;

/**
 * Created by kkopite on 2017/12/25.
 */

public class A9MyBinder extends Binder implements IronbotSearcherCallback {

    private A8IronbotSearcher mSearcher = new A8IronbotSearcher();
    IBinder mBinderCallback;

    @Override
    protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
        if (code == 0) {
            reply.writeByte((byte) (mSearcher.isEnable() ? 1 : 0));
            return true;
        } else if (code == 1) {
            mBinderCallback = data.readStrongBinder();
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
}
