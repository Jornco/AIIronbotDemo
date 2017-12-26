package com.jornco.aiironbotdemo.activity.a14;

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
import com.jornco.aiironbotdemo.activity.a14.service.A14MyService;
import com.jornco.aiironbotdemo.ble.ISearchIronbot;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;

public class A14SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "A14SearchActivity";
    private IronbotInfo mDeviceInfo;

    private Button mBtnScan;
    private Button mBtnStop;
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
        setContentView(R.layout.activity_a14_search);
        initView();
        bindService(new Intent(this, A14MyService.class), mConnection, BIND_AUTO_CREATE);
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
            if (code == 0) {
                String infoXml = data.readString();
                mDeviceInfo = new IronbotInfo(infoXml);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvDevice.setText(mDeviceInfo.toString());
                    }
                });
                return true;
            } else if (code == 1) {
                IBinder binder = data.readStrongBinder();
                Parcel data2 = Parcel.obtain();
                Parcel reply2 = Parcel.obtain();
                binder.transact(2, data2, reply2, 0);
                final IronbotInfo info = new IronbotInfo(reply2.readString());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvDevice.setText(info.toString());
                    }
                });
                return true;
            }
            return false;
        }
    }
}
