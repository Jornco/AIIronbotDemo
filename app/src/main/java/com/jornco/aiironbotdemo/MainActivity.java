package com.jornco.aiironbotdemo;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jornco.aiironbotdemo.activity.a1.A1SearchActivity;
import com.jornco.aiironbotdemo.activity.a10.A10SearchActivity;
import com.jornco.aiironbotdemo.activity.a11.A11SearchActivity;
import com.jornco.aiironbotdemo.activity.a12.A12SearchActivity;
import com.jornco.aiironbotdemo.activity.a13.A13SearchActivity;
import com.jornco.aiironbotdemo.activity.a14.A14SearchActivity;
import com.jornco.aiironbotdemo.activity.a15.A15SearchActivity;
import com.jornco.aiironbotdemo.activity.a16.A16SearchActivity;
import com.jornco.aiironbotdemo.activity.a17.A17SearchActivity;
import com.jornco.aiironbotdemo.activity.a18.A18Activity;
import com.jornco.aiironbotdemo.activity.a19.A19Activity;
import com.jornco.aiironbotdemo.activity.a2.A2SearchActivity;
import com.jornco.aiironbotdemo.activity.a20.A20Activity;
import com.jornco.aiironbotdemo.activity.a21.A21Activity;
import com.jornco.aiironbotdemo.activity.a3.A3Activity;
import com.jornco.aiironbotdemo.activity.a4.A4Activity;
import com.jornco.aiironbotdemo.activity.a5.A5Activity;
import com.jornco.aiironbotdemo.activity.a6.A6Activity;
import com.jornco.aiironbotdemo.activity.a7.A7Activity;
import com.jornco.aiironbotdemo.activity.a8.A8SearchActivity;
import com.jornco.aiironbotdemo.activity.a9.A9SearchActivity;

import java.util.LinkedHashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final int PERMISSION_REQUEST_COARSE_LOCATION = 0x111;

    private final LinkedHashMap<String, Class<? extends Activity>> buttons = new LinkedHashMap<String, Class<? extends Activity>>() {
        {
            put("A1", A1SearchActivity.class);
            put("A2", A2SearchActivity.class);
            put("A3", A3Activity.class);
            put("A4", A4Activity.class);
            put("A5", A5Activity.class);
            put("A6", A6Activity.class);
            put("A7", A7Activity.class);
            put("A8", A8SearchActivity.class);
            put("A9", A9SearchActivity.class);
            put("A10", A10SearchActivity.class);
            put("A11", A11SearchActivity.class);
            put("A12", A12SearchActivity.class);
            put("A13", A13SearchActivity.class);
            put("A14", A14SearchActivity.class);
            put("A15", A15SearchActivity.class);
            put("A16", A16SearchActivity.class);
            put("A17", A17SearchActivity.class);
            put("A18", A18Activity.class);
            put("A19", A19Activity.class);
            put("A20", A20Activity.class);
            put("A21", A21Activity.class);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initPermission();
    }

    private void initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    || (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
                    ) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSION_REQUEST_COARSE_LOCATION);
            }
        }
    }

    private void initView() {
        ViewGroup container = (ViewGroup) findViewById(R.id.list);
        for (final Map.Entry<String, Class<? extends Activity>> entry : buttons.entrySet()) {
            Button button = new Button(this);
            button.setText(entry.getKey());
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this, entry.getValue()));
                }
            });
            container.addView(button);
        }
    }
}
