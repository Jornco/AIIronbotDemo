package com.jornco.aiironbotdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.jornco.aiironbotdemo.R;
import com.jornco.aiironbotdemo.ble.A1IronbotSearcher;
import com.jornco.aiironbotdemo.ble.A3IronbotService;
import com.jornco.aiironbotdemo.ble.A3IronbotSession;
import com.jornco.aiironbotdemo.ble.device.IronbotInfo;
import com.jornco.aiironbotdemo.ble.scan.IronbotSearcherCallback;

public class A3Activity extends AppCompatActivity implements View.OnClickListener, IronbotSearcherCallback {

    private static final String TAG = "A3Activity";
    private Button mBtnScan;
    private Button mBtnStop;
    private Button mBtnConn;

    private A1IronbotSearcher mSearcher;
    private IronbotInfo mServiceInfo;

    private A3IronbotService mIronbotService;
    private A3IronbotSession mIronbotSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a3);
        initView();

        mSearcher = new A1IronbotSearcher();
    }

    private void initView() {
        mBtnScan = (Button) findViewById(R.id.btn_scan);
        mBtnStop = (Button) findViewById(R.id.btn_stop);
        mBtnConn = (Button) findViewById(R.id.btn_conn);

        mBtnScan.setOnClickListener(this);
        mBtnStop.setOnClickListener(this);
        mBtnConn.setOnClickListener(this);
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
                if (mIronbotService == null) {
                    return;
                }
                // 获取一个蓝牙连接
                mIronbotSession = mIronbotService.getSession(this);
                break;
        }
    }

    @Override
    public void onIronbotFound(IronbotInfo info) {
        Log.e(TAG, "onIronbotFound: " + info.toString());
        mIronbotService = new A3IronbotService(info);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSearcher.stopScan();
    }
}
