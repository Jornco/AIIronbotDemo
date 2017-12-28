package com.jornco.aiironbotdemo.activity.a19;

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
import com.jornco.aiironbotdemo.activity.a19.service.A19MyService;
import com.jornco.aiironbotdemo.ble.ISearchIronbot;
import com.jornco.aiironbotdemo.ble.IronbotService;
import com.jornco.aiironbotdemo.ble.IronbotSession;
import com.jornco.aiironbotdemo.ble.common.IronbotCode;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;
import com.jornco.aiironbotdemo.util.IronbotCodeUtil;

public class A19Activity extends AppCompatActivity implements View.OnClickListener {
    private IronbotInfo mDeviceInfo;

    private Button mBtnScan;
    private Button mBtnStop;
    private Button mBtnFind;
    private Button mBtnSend;
    private TextView mTvDevice;

    private IBinder mCallbackBinder;
    private ISearchIronbot proxy;
    private IronbotService mClientService;
    private IronbotSession mClientSession;

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
        setContentView(R.layout.activity_a19);
        initView();
        bindService(new Intent(this, A19MyService.class), mConnection, BIND_AUTO_CREATE);
        mCallbackBinder = new ActBinder();
    }

    private void initView() {
        mBtnScan = (Button) findViewById(R.id.btn_scan);
        mBtnStop = (Button) findViewById(R.id.btn_stop);
        mBtnFind = (Button) findViewById(R.id.btn_find);
        mBtnSend = (Button) findViewById(R.id.btn_send);
        mTvDevice = (TextView) findViewById(R.id.tv_device);

        mBtnScan.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
        mBtnFind.setOnClickListener(this);
        mBtnSend.setOnClickListener(this);
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

                    // A18SrvBinder
                    mClientService = IronbotService.Stub.asInterface(service);

                    // A18SessionBinder
                    IBinder ib2 = mClientService.getSession();
                    mClientSession = IronbotSession.Stub.asInterface(ib2);
                    String infoXml = mClientSession.getServiceInfoXml();
                    IronbotInfo info = new IronbotInfo(infoXml);
                    mTvDevice.setText(info.toString());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_send:
                if (mClientSession == null) {
                    return;
                }
                IronbotCode code = IronbotCodeUtil.createRandomLED();
                try {
                    mClientSession.sendMsg(code.toXml(), mCallbackBinder);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
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
                // callback from search ironbot
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
                final String s = data.readString();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvDevice.setText(s);
                    }
                });
            }
            return false;
        }
    }
}
