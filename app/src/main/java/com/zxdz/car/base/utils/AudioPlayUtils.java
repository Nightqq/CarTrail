package com.zxdz.car.base.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.widget.PopupWindow;

import com.blankj.utilcode.util.LogUtils;

/**
 * Created by admin on 2017/11/19.
 * 声音播放类
 */

public class AudioPlayUtils {

    private static Context mContext = null;

    AudioManager audioManager;

    MediaPlayer playerSound;

    Thread playThread;

    private boolean isRun = true;

    /**
     * 播放声音的资源ID
     */
    private static int mRawId;

    private boolean isLoop = false;

    public static boolean isPLayComplete=false;
    private static AudioPlayUtils audioPlayUtils;

    private AudioPlayUtils() {
        audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMicrophoneMute(false);
        audioManager.setSpeakerphoneOn(true);
        //使用扬声器播放，即使已经插入耳机
        audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
    }

    public static AudioPlayUtils getAudio(Context context, int rawId) {
        mRawId = rawId;
        mContext = context;
        if (audioPlayUtils == null) {
            audioPlayUtils = new AudioPlayUtils();
        }

        return audioPlayUtils;
    }


    class PlayThread implements Runnable {
        @Override
        public void run() {
            if (isPLayComplete) {
                if (audioManager.isSpeakerphoneOn()) {
                    //  LogUtils.e("扬声器打开了1");
                } else {
                    audioManager.setSpeakerphoneOn(false);
                    //   LogUtils.e("扬声器关闭了");
                    if (audioManager.isSpeakerphoneOn()) {
                        //  LogUtils.e("扬声器打开了2");
                    } else {
                        //  LogUtils.e("扬声器还是没打开");
                    }
                }
                playerSound = MediaPlayer.create(mContext, mRawId);
                playerSound.setLooping(isLoop);
                playerSound.start();
                playerSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (playerSound != null) {
                            playerSound.release();
                        }
                        LogUtils.a("语音lll结束");
                        // LogUtils.a( isPLayComplete);
                        isLoop = false;
                        isPLayComplete=false;
                    }
                });
            }
        }
    }

  /*  private boolean isPLayComplete() {//循环播放是否运行
        return isPLayComplete;
    }

    private void setPLayComplete(boolean PLayComplete) {
        isPLayComplete = PLayComplete;
    }*/

    public void play() {
        if (isPLayComplete){
            return;
        }
        isPLayComplete = true;
        playThread = new Thread(new PlayThread());
        playThread.run();
    }

    public void play(boolean flag) {
        play();
        isLoop = flag;
    }

    public void stop() {
        isPLayComplete = false;
        if (playerSound != null) {
            if (isLoop) {
                isLoop = false;
                try {
                    playerSound.setLooping(false);
                    playerSound.stop();
                } catch (IllegalStateException e) {
                    LogUtils.e("语音lll异常");
                }
            }
        }
        if (playThread != null && playThread.isAlive()) {
            playThread.interrupt();
        }
        audioManager = null;
        playerSound = null;
        audioPlayUtils = null;
    }
}
