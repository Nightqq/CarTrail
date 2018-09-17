package com.zxdz.car.main.view;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.zxdz.car.R;
import com.zxdz.car.base.view.BaseActivity;

import butterknife.BindView;

/**
 * Created by iflying on 2017/10/29.
 */

public class ReadIcCardActivity extends BaseActivity {

    private static final String TAG = "ReadIcCardActivity";

    private static final String KEY_MSG = "Message";

    private static final int MESSAGE_UPDATE_CONNECT_STATUS = 1;

    private static final int MESSAGE_UPDATE_UID = 2;

    private static final int MESSAGE_UPDATE_WAIT = 3;

    private boolean mbQueryExitFlag = false;

    private ToneGenerator mToneGenerator;

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @BindView(R.id.tv_state)
    TextView mLbStatus;

    @BindView(R.id.tv_ic_card_number)
    TextView mLbUid;

    @BindView(R.id.ic_read_card_wait)
    ImageView mImage;

    @Override
    public int getLayoutId() {
        return R.layout.activity_read_ic_card;
    }

    @Override
    public void init() {
        mToolBar.setTitle("IC卡刷卡测试");
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationIcon(R.mipmap.back_icon);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mToneGenerator = new ToneGenerator(AudioManager.STREAM_SYSTEM, 100);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new InitT().start();
    }

    private class InitT extends Thread {
        public void run() {
            com.halio.Rfid.closeCommPort();
            com.halio.Rfid.powerOff();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }

            com.halio.Rfid.powerOn();
            com.halio.Rfid.openCommPort();

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {

                e.printStackTrace();
            }

            new ConnectT().start();
        }
    }

    public void onDestroy() {
        super.onDestroy();
        mbQueryExitFlag = true;
        com.halio.Rfid.closeCommPort();
        com.halio.Rfid.powerOff();
        LogUtils.e("test", "onDestroy");
    }

    private String getResString(int id) {
        return this.getString(id);
    }


    private void updateStatus(String info) {
        Message msg = new Message();
        msg.what = MESSAGE_UPDATE_CONNECT_STATUS;
        Bundle bundle = new Bundle();
        bundle.putString(KEY_MSG, info);
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    private void showUid(String uid) {
        Message msg = new Message();
        msg.what = MESSAGE_UPDATE_UID;
        Bundle bundle = new Bundle();
        bundle.putString(KEY_MSG, uid);
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    private void showWait() {
        Message msg = new Message();
        msg.what = MESSAGE_UPDATE_WAIT;
        Bundle bundle = new Bundle();
        bundle.putString(KEY_MSG, "");
        msg.setData(bundle);
        mHandler.sendMessage(msg);
    }

    private String getNativeString(int id) {
        return this.getString(id);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String info = bundle.getString(KEY_MSG);

            switch (msg.what) {
                case MESSAGE_UPDATE_CONNECT_STATUS:
                    mLbStatus.setText(info);
                    break;
                case MESSAGE_UPDATE_UID:
                    mLbUid.setText("读取的卡号：" + info);
                    mLbUid.setVisibility(View.VISIBLE);
                    mImage.setVisibility(View.GONE);
                    break;
                case MESSAGE_UPDATE_WAIT:
                    mLbUid.setVisibility(View.GONE);
                    mImage.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    private void playTone() {
        if (mToneGenerator != null) {
            mToneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP2, 100);
        }
    }


    private boolean readData(byte[] UID) {

        if (!com.halio.Rfid.PcdConfigISOType((byte) 'A')) {
            LogUtils.e("test", "PcdConfigISOType fail");
            return false;
        }

        long start1 = System.currentTimeMillis();
        byte[] tagType = new byte[2];
        if (!com.halio.Rfid.PcdRequest((byte) 0x52, tagType)) {
//			LogUtils.e("test", "PcdRequest fail");
            LogUtils.e("test", "PcdRequest fail:" + (System.currentTimeMillis() - start1));
            return false;
        }

        LogUtils.e("test", "PcdRequest succ:" + (System.currentTimeMillis() - start1));
        long start2 = System.currentTimeMillis();

        if (!com.halio.Rfid.PcdAnticoll(UID)) {
//			LogUtils.e("test", "PcdAnticoll fail");
            LogUtils.e("test", "PcdAnticoll fail:" + (System.currentTimeMillis() - start2));
            return false;
        }
        LogUtils.e("test", "PcdAnticoll succ:" + (System.currentTimeMillis() - start2));
        return true;
    }

    private class QueryICUidT extends Thread {
        public void run() {
            mbQueryExitFlag = false;
            try {
                while (!mbQueryExitFlag) {
                    byte[] bUid = new byte[4];
                    if (readData(bUid)) {
                        StringBuffer sb = new StringBuffer();
                        for (int i = 0; i < bUid.length; i++) {
                            String string = Integer.toHexString(bUid[i] & 0xFF);
                            sb.append(" ");
                            sb.append(string.length() < 2 ? "0" + string : string);
                        }

                        showUid(sb.toString());
                        playTone();
                    } else {
                        showWait();

                    }
                }
            } catch (Exception e) {
                LogUtils.e("Read ic card QueryICUidT error " + e.getMessage());
            }
        }
    }

    private class ConnectT extends Thread {
        public void run() {
            byte[] bVersion = new byte[16];
            int iVersionLength = com.halio.Rfid.getHwVersion(bVersion);
            if (iVersionLength > 0) {
                updateStatus(new String(bVersion, 0, iVersionLength));
                new QueryICUidT().start();
            } else {
                updateStatus("Connect Fail,getVersionFail!");
            }

        }
    }
}
