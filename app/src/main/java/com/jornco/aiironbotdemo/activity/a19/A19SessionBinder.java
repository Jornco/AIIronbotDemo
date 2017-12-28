package com.jornco.aiironbotdemo.activity.a19;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import com.jornco.aiironbotdemo.ble.IronbotSession;
import com.jornco.aiironbotdemo.ble.common.BLEWriterError;
import com.jornco.aiironbotdemo.ble.common.IronbotCode;
import com.jornco.aiironbotdemo.ble.connect.OnIronbotWriteCallback;

/**
 * Created by kkopite on 2017/12/28.
 */

public class A19SessionBinder extends IronbotSession.Stub{

    private A19BLESession mSession;
    private IBinder mBinderCallback;

    public A19SessionBinder(A19BLESession session) {
        mSession = session;
    }

    @Override
    public boolean isConnected() throws RemoteException {
        return false;
    }

    @Override
    public String getServiceInfoXml() throws RemoteException {
        return mSession.getServiceInfoXml();
    }

    @Override
    public void sendMsg(String codeXml, IBinder callback) throws RemoteException {
        mBinderCallback = callback;
        IronbotCode code = IronbotCode.create(codeXml);
        mSession.sendMsg(code, new OnIronbotWriteCallback() {
            @Override
            public void onWriterSuccess(String address) {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                data.writeString("WriterSuccess!");
                try {
                    mBinderCallback.transact(1, data, reply, 0);
                } catch (RemoteException e) { e.printStackTrace(); }
            }

            @Override
            public void onWriterFailure(String address, BLEWriterError error) {
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                data.writeString("WriterFailure!");
                try {
                    mBinderCallback.transact(1, data, reply, 0);
                } catch (RemoteException e) { e.printStackTrace(); }
            }

            @Override
            public void onAllDeviceFailure() {

            }

            @Override
            public void onWriterEnd() {

            }
        });
    }
}
