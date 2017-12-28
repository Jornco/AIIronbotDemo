// IMusic.aidl
package com.jornco.aiironbotdemo.ble;

// Declare any non-default types here with import statements

interface IMusic {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    // 播放音樂
    void playMusic();
    // 結束播放
    void stopMusic();
}
