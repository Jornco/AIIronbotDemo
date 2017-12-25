package com.jornco.aiironbotdemo.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jornco.aiironbotdemo.R;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;
import com.jornco.aiironbotdemo.service.A9MyBinder;
import com.jornco.aiironbotdemo.service.A9MyService;

public class A9SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "A9SearchActivity";
    private A9MyBinder mBinder;
    private IronbotInfo mDeviceInfo;


    private Button mBtnScan;
    private Button mBtnStop;
    private TextView mTvDevice;

    private IBinder mIBinder;
    private IBinder mCallbackBinder;


    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (A9MyBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a9_search);
        initView();
        bindService(new Intent(this, A9MyService.class), mConnection, BIND_AUTO_CREATE);
        mCallbackBinder = new ActBinder();
    }

    private void initView() {
        mBtnScan = (Button) findViewById(R.id.btn_scan);
        mBtnStop = (Button) findViewById(R.id.btn_stop);
        mTvDevice = (TextView) findViewById(R.id.tv_device);

        mBtnScan.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scan:
                // 调用isEnable
                Parcel data = Parcel.obtain();
                Parcel reply = Parcel.obtain();
                try {
                    mBinder.transact(0, data, reply, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                byte b = reply.readByte();
                if (b == 1) {
                    // 调用searchIronbot
                    data.writeStrongBinder(mCallbackBinder);
                    try {
                        mBinder.transact(1, data, reply, 0);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        mBinder.transact(2, data, reply, 0);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

                break;
            case R.id.btn_stop:
                Parcel data2 = Parcel.obtain();
                Parcel reply2 = Parcel.obtain();
                try {
                    mBinder.transact(3, data2, reply2, 0);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        if (mBinder != null) {
            try {
                mBinder.transact(3, data, reply, 0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        unbindService(mConnection);
    }

    private class ActBinder extends Binder {

        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            String infoXml = data.readString();
            IronbotInfo info = new IronbotInfo(infoXml);
            mDeviceInfo = info;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTvDevice.setText(mDeviceInfo.toString());
                }
            });
            return true;
        }
    }
}
