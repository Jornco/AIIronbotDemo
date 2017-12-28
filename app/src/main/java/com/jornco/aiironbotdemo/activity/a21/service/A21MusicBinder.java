package com.jornco.aiironbotdemo.activity.a21.service;

import android.media.MediaPlayer;
import android.os.RemoteException;

import com.jornco.aiironbotdemo.IApplication;
import com.jornco.aiironbotdemo.R;
import com.jornco.aiironbotdemo.ble.IMusic;

/**
 * Created by kkopite on 2017/12/28.
 */

public class A21MusicBinder extends IMusic.Stub {

    private MediaPlayer mMediaPlayer;

    @Override
    public void playMusic() throws RemoteException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mMediaPlayer !=null && mMediaPlayer.isPlaying()) {
                    return;
                }
                mMediaPlayer = MediaPlayer.create(IApplication.getInstance(), R.raw.music);
                mMediaPlayer.start();
            }
        }).start();
    }

    @Override
    public void stopMusic() throws RemoteException {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }
    }
}
