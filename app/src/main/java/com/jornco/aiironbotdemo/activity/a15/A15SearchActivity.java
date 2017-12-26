package com.jornco.aiironbotdemo.activity.a15;

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
import com.jornco.aiironbotdemo.activity.a15.service.A15MyService;
import com.jornco.aiironbotdemo.ble.ISearchIronbot;
import com.jornco.aiironbotdemo.ble.common.BLELog;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;

public class A15SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "A15SearchActivity";
    private IronbotInfo mDeviceInfo;

    private Button mBtnScan;
    private Button mBtnStop;
    private Button mBtnFind;
    private TextView mTvDevice;

    private IBinder mCallbackBinder;
    private ISearchIronbot proxy;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            proxy = ISearchIronbot.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a15_search);
        initView();
        bindService(new Intent(this, A15MyService.class), mConnection, BIND_AUTO_CREATE);
        mCallbackBinder = new ActBinder();
    }

    private void initView() {
        mBtnScan = (Button) findViewById(R.id.btn_scan);
        mBtnStop = (Button) findViewById(R.id.btn_stop);
        mBtnFind = (Button) findViewById(R.id.btn_find);
        mTvDevice = (TextView) findViewById(R.id.tv_device);

        mBtnScan.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
        mBtnFind.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scan:
                if (proxy == null) {
                    return;
                }
                try {
                    if (proxy.isEnable()) {
                        proxy.searchIronbot(mCallbackBinder);
                    } else {
                        proxy.enable();
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_stop:
                if (proxy != null) {
                    try {
                        proxy.stopScan();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.btn_find:
                if (mDeviceInfo == null || proxy == null) {
                    return;
                }
                try {
                    IBinder service = proxy.findService(mDeviceInfo.toXml());
                    Parcel data = Parcel.obtain();
                    Parcel reply = Parcel.obtain();
                    service.transact(0, data, reply, 0);
                    IronbotInfo info = new IronbotInfo(reply.readString());
                    mTvDevice.setText(info.toString());
                    BLELog.log(info.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (proxy != null) {
            try {
                proxy.stopScan();
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
            mDeviceInfo = new IronbotInfo(infoXml);
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
