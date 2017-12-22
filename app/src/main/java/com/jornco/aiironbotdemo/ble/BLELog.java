package com.jornco.aiironbotdemo.ble;

import android.util.Log;

/**
 * Created by kkopite on 2017/12/22.
 */

public class BLELog {

    private static final String TAG = "BLELog";

    public static void log(String msg){
        Log.e(TAG, "log: " + msg);
    }
}
