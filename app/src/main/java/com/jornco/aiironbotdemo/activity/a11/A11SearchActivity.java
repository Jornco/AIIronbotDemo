package com.jornco.aiironbotdemo.activity.a11;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jornco.aiironbotdemo.R;
import com.jornco.aiironbotdemo.activity.a11.service.A11SearchProxy;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;
import com.jornco.aiironbotdemo.activity.a11.service.A11MyService;

public class A11SearchActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EVENT_FIND_DEVICE = "event_find_device";
    private static final String TAG = "A10SearchActivity";

    private IronbotInfo mDeviceInfo;


    private Button mBtnScan;
    private Button mBtnStop;
    private TextView mTvDevice;

//    private IBinder mCallbackBinder;
    private A11ISearch proxy;
    private BroadcastReceiver mReceiver = new MyIntentReceiver();
    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            mBinder = (A9MyBinder) service;
            proxy = new A11SearchProxy(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a11_search);
        initView();
        bindService(new Intent(this, A11MyService.class), mConnection, BIND_AUTO_CREATE);
//        mCallbackBinder = new ActBinder();
        registerReceiver(mReceiver, new IntentFilter(EVENT_FIND_DEVICE));
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
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
                if (proxy.isEnable()) {
                    proxy.searchIronbot();
                } else {
                    proxy.enable();
                }
                break;
            case R.id.btn_stop:
                if (proxy != null) {
                    proxy.stopScan();
                }
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (proxy != null) {
            proxy.stopScan();
        }
        unbindService(mConnection);
    }

    class MyIntentReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String infoXml = intent.getStringExtra("value");
            mDeviceInfo = new IronbotInfo(infoXml);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mTvDevice.setText(mDeviceInfo.toString());
                }
            });
        }
    }
}
