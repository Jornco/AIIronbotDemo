package com.jornco.aiironbotdemo.ble.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kkopite on 2017/12/24.
 */

public class IronbotCode {

    private List<String> codes = new ArrayList<>();
    private String data = "";

    public String getData() {
        return data;
    }

    public List<String> getCodes() {
        return codes;
    }

    public static IronbotCode create(String code) {
        IronbotCode ironbotCode = new IronbotCode();
        ironbotCode.data = code;
        ironbotCode.codes = sqlit(code);
        return ironbotCode;
    }

    private static List<String> sqlit(String msg) {
        List<String> codes = new ArrayList<>();
        int length = msg.length();
        if (length <= 20) {
            codes.add(msg);
        } else {
            int index = 0;
            while (index < length) {
                if (index + 20 > length) {
                    codes.add(msg.substring(index, length));
                } else {
                    codes.add(msg.substring(index, index + 20));
                }
                index += 20;
            }
        }
        return codes;
    }
}
