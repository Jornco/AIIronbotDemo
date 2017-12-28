package com.jornco.aiironbotdemo.util;

import com.jornco.aiironbotdemo.ble.common.IronbotCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kkopite on 2017/12/25.
 */

public class IronbotCodeUtil {

    public static final String COLOR_CMD = "#B%d,%d,%d,*";

    public static IronbotCode createRandomLED() {
        int r = getRandom(0, 255);
        int g = getRandom(0, 255);
        int b = getRandom(0, 255);
        String cmd = String.format(COLOR_CMD, r, g, b);
        return IronbotCode.create(cmd);
    }

    public static List<String> createRandomAction () {
        int size = getRandom(5, 10);
        List<String> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(createRandomActor());
        }
        return list;
    }

    public static String createRandomActor() {
        StringBuilder sb = new StringBuilder();
        sb.append("#A");
        for (int i = 0; i < 10; i++) {
            sb.append(i)
                    .append(",")
                    .append(getRandom(500, 2500))
                    .append(",")
                    .append("1000")
                    .append(",");
        }
        sb.append("*");
        return sb.toString();
    }

    private static int getRandom(int min, int max) {
        return (int) (Math.floor(Math.random() * (max - min)) + min + 1);
    }
}
