package com.zxdz.car.main.view;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.huayu.falconroid.utils.FalconException;
import com.huayu.halio.Tag125K;
import com.jakewharton.rxbinding.view.RxView;
import com.zxdz.car.R;
import com.zxdz.car.base.helper.CardHelper;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.model.domain.CardInfo;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * Created by super on 2017/10/18.
 */

public class ReadCardActivity extends BaseActivity {

    private boolean mbQueryExitFlag = false;

    private static final String TAG = "ReadCardActivity";

    private static final String KEY_MSG = "Message";

    private static final int MESSAGE_UPDATE_CONNECT_STATUS = 1;

    private static final int MESSAGE_UPDATE_UID = 2;

    private static final int MESSAGE_UPDATE_WAIT = 3;

    private ToneGenerator mToneGenerator;

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @BindView(R.id.tv_lb_state)
    TextView mLbStatusTextView;

    @BindView(R.id.tv_uid)
    TextView mLbUidTextView;

    @BindView(R.id.iv_waiting)
    ImageView mWaitImageView;

    @BindView(R.id.tv_read_card_number)
    TextView mReadCardTextView;

    @BindView(R.id.btn_delete_card)
    Button mDeleteButton;

    private Tag125K sTag125k = new Tag125K(); //static

    private CardInfo cardInfo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_read_card;
    }

    @Override
    public void init() {
        mToolBar.setTitle("ID卡刷卡测试");
        setSupportActionBar(mToolBar);
        /*mToolBar.setNavigationIcon(R.mipmap.back_icon);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

        mToneGenerator = new ToneGenerator(AudioManager.STREAM_SYSTEM, 100);
        RxView.clicks(mDeleteButton).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (cardInfo != null) {
                    CardHelper.deleteCardInfoInDB(cardInfo);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        new OpenT().start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mbQueryExitFlag = true;
        sTag125k.closeTag125KPower();
        sTag125k.closeTag125KPort();
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

    private void playTone() {
        if (mToneGenerator != null) {
            mToneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP2, 100);
        }
    }

    private class QueryUidT extends Thread {
        public void run() {
            mbQueryExitFlag = false;
            try {
                while (!mbQueryExitFlag) {
                    byte[] bUid = null;
                    try {
                        bUid = sTag125k.readTag125KUid();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                        LogUtils.e(TAG, "readTag125KUid Exception:" + e.toString());
                    }
                    if (bUid != null) {
                        StringBuffer sb = new StringBuffer();
                        LogUtils.e(TAG, bUid.length + "");
                        for (int i = 0; i < bUid.length; i++) {
                            sb.append(" ");
                            sb.append(Integer.toHexString(bUid[i] & 0xFF));
                        }

                        showUid(sb.toString());
                        //play tone
                        playTone();

                        try {
                            this.sleep(200);//1000
                        } catch (java.lang.InterruptedException ie) {
                        }

                    } else {
                        showWait();
                    }

                }
            } catch (Exception e) {
                LogUtils.e("Read id card QueryUidT error --->" + e.getMessage());
            }
        }
    }

    private class OpenT extends Thread {
        public void run() {

            sTag125k.closeTag125KPower();

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            sTag125k.openTag125KPower();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {
                sTag125k.openTag125KPort();
            } catch (FalconException fae) {
                LogUtils.e(TAG, "openTag125KPort Exception:" + fae.toString());
            }

            new QueryUidT().start();
        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Bundle bundle = msg.getData();
            String info = bundle.getString(KEY_MSG);
            switch (msg.what) {
                case MESSAGE_UPDATE_CONNECT_STATUS: {
                    mLbStatusTextView.setText(info);
                }
                break;
                case MESSAGE_UPDATE_UID: {
                    mLbUidTextView.setText(info);
                    mWaitImageView.setVisibility(View.GONE);

                    //存储卡号到本地数据库
                    CardInfo cardInfo = new CardInfo();
                    cardInfo.setAdminCardNumber(info);
                    CardHelper.saveCardInfoToDB(cardInfo);

                    //从本地数据库读取卡号
                    List<CardInfo> cardInfoList = CardHelper.getCardInfoListFromDB();
                    if (cardInfoList != null && cardInfoList.size() > 0) {
                        cardInfo = cardInfoList.get(0);
                        mReadCardTextView.setText(cardInfo.getAdminCardNumber());
                    }
                    break;
                }
                case MESSAGE_UPDATE_WAIT: {
                    //	mLbUidTextView.setVisibility(View.GONE);
                    //	mWaitImageView.setVisibility(View.VISIBLE);
                }
                break;
            }
        }
    };
}
