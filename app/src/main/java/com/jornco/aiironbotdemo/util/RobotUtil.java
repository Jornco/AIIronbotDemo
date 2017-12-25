package com.jornco.aiironbotdemo.util;

import com.jornco.aiironbotdemo.ble.common.IronbotCode;

/**
 * Created by kkopite on 2017/12/25.
 */

public class RobotUtil {

    public static final String COLOR_CMD = "#B%d,%d,%d,*";

    public static IronbotCode createRandomLED() {
        int r = getRandom(0, 255);
        int g = getRandom(0, 255);
        int b = getRandom(0, 255);
        String cmd = String.format(COLOR_CMD, r, g, b);
        return IronbotCode.create(cmd);
    }

    private static int getRandom(int min, int max) {
        return (int) (Math.floor(Math.random() * (max - min)) + min + 1);
    }
}
