package com.jornco.aiironbotdemo.activity.a16.service;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.jornco.aiironbotdemo.activity.a13.A13BLEService;
import com.jornco.aiironbotdemo.activity.a13.A13IronbotSearcher;
import com.jornco.aiironbotdemo.activity.a16.A16SrvBinder;
import com.jornco.aiironbotdemo.ble.ISearchIronbot;
import com.jornco.aiironbotdemo.ble.common.BLELog;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;
import com.jornco.aiironbotdemo.ble.scan.IronbotSearcherCallback;

/**
 * Created by kkopite on 2017/12/26.
 */

public class A16SearchBinder extends ISearchIronbot.Stub implements IronbotSearcherCallback {
    private A13IronbotSearcher mSearcher = new A13IronbotSearcher();
    private IBinder mBinderCallback;

    @Override
    public void onIronbotFound(IronbotInfo info) {
        BLELog.log(info.toString());
        Parcel data2 = Parcel.obtain();
        Parcel reply2 = Parcel.obtain();
        // 調用IronbotInfo的toXml()
        data2.writeString( info.toXml() );
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
        IronbotInfo info = new IronbotInfo(infoXml);
        A13BLEService service = mSearcher.findService(info);
        return new A16SrvBinder(service, mBinderCallback);
    }
}
