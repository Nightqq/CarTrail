package com.zxdz.car.main.view;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.widget.Button;

import com.blankj.utilcode.util.LogUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.zxdz.car.R;
import com.zxdz.car.base.view.BaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by iflying on 2017/11/17.
 */

public class AudioActivity extends BaseActivity {

    @BindView(R.id.btn_audio)
    Button mAudioButton;

    AudioManager audioManager;

    MediaPlayer playerSound;

    Thread playThread;

    private boolean isRun = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_audio;
    }

    @Override
    public void init() {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setMicrophoneMute(false);
        audioManager.setSpeakerphoneOn(true);// 使用扬声器播放，即使已经插入耳机
        audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
                //audioManager.setStreamVolume(AudioManager.STREAM_ALARM,200,1);
                RxView.clicks(mAudioButton).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                playThread = new Thread(new PlayThread());
                playThread.run();
            }
        });
    }

    class PlayThread implements Runnable {

        @Override
        public void run() {
            if (isRun) {
                if (audioManager.isSpeakerphoneOn()) {
                    LogUtils.e("扬声器打开了111");
                } else {
                    audioManager.setSpeakerphoneOn(false);
                    LogUtils.e("扬声器关闭了");
                    if (audioManager.isSpeakerphoneOn()) {
                        LogUtils.e("扬声器打开了222");
                    } else {
                        LogUtils.e("扬声器还是没打开");
                    }
                }
                
                playerSound = MediaPlayer.create(AudioActivity.this, R.raw.test);
                playerSound.start();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isRun = false;
        if (playerSound != null) {
            playerSound.stop();
        }
        audioManager = null;
        playerSound = null;
    }
}
