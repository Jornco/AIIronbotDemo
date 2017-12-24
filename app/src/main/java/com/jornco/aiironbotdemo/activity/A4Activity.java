package com.jornco.aiironbotdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.jornco.aiironbotdemo.R;
import com.jornco.aiironbotdemo.ble.A1IronbotSearcher;
import com.jornco.aiironbotdemo.ble.A3IronbotService;
import com.jornco.aiironbotdemo.ble.A3IronbotSession;
import com.jornco.aiironbotdemo.ble.A4BLEService;
import com.jornco.aiironbotdemo.ble.A4BLESession;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;
import com.jornco.aiironbotdemo.ble.scan.IronbotSearcherCallback;

import java.util.ArrayList;
import java.util.List;

public class A4Activity extends AppCompatActivity implements View.OnClickListener, IronbotSearcherCallback {

    private static final String TAG = "A4Activity";

    private Button mBtnScan;
    private Button mBtnStop;
    private Button mBtnConn;
    private TextView mTvDevice;

    private A1IronbotSearcher mSearcher;
    private List<A4BLEService> mServiceList;

    private A4BLEService mService;
    private A4BLESession mSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a4);
        initView();
    }

    private void initView() {
        mBtnScan = (Button) findViewById(R.id.btn_scan);
        mBtnStop = (Button) findViewById(R.id.btn_stop);
        mBtnConn = (Button) findViewById(R.id.btn_conn);
        mTvDevice = (TextView) findViewById(R.id.tv_device);

        mBtnScan.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
        mBtnConn.setOnClickListener(this);

        mSearcher = new A1IronbotSearcher();
        mServiceList = new ArrayList<>();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_scan:
                if (mSearcher.isEnable()) {
                    mSearcher.searchIronbot(this);
                } else {
                    mSearcher.enable();
                }
                break;
            case R.id.btn_stop:
                mSearcher.stopScan();
                break;
            case R.id.btn_conn:
                if (mServiceList.isEmpty()) {
                    Toast.makeText(this, "当前没有扫描到设备", Toast.LENGTH_SHORT).show();
                    return;
                }
                mService = mServiceList.get(0);
                mSession = mService.getSession(this);
                break;
        }
    }

    @Override
    public void onIronbotFound(IronbotInfo info) {
        Log.e(TAG, "onIronbotFound: " + info.toString());
        for (A4BLEService service : mServiceList) {
            String address = service.getInfo().getAddress();
            if (address != null && address.equals(info.getAddress())) {
                return;
            }
        }
        mServiceList.add(new A4BLEService(info));
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSearcher.stopScan();
    }
}
