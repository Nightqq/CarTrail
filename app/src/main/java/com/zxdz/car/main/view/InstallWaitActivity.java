package com.zxdz.car.main.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.zxdz.car.App;
import com.zxdz.car.R;
import com.zxdz.car.base.utils.AudioPlayUtils;
import com.zxdz.car.base.utils.SwipingCardUtils;
import com.zxdz.car.base.view.BaseActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.functions.Action1;

/**
 * Created by admin on 2017/10/29.
 */

public class InstallWaitActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @BindView(R.id.layout_install_wait)
    LinearLayout mLayoutInstallWait;

    @BindView(R.id.layout_over)
    LinearLayout mLayoutOver;

    @BindView(R.id.tv_install_over)
    TextView mInstallOverTextView;

    @BindView(R.id.layout_success)
    LinearLayout mLayoutSuccess;

    @BindView(R.id.btn_install_success)
    Button mInstallSuccessButton;

    HeadsetPlugReceiver headsetPlugReceiver;

    private AudioManager audoManager;

    private SweetAlertDialog confirmDialog;

    AudioPlayUtils audioPlayUtils;

    private boolean isFirstOpen = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_install_wait;
    }

    @Override
    public void init() {
        audioPlayUtils = new AudioPlayUtils(this, R.raw.qdcmjazgksb);
        audioPlayUtils.play();
        mToolBar.setTitle("等待安装设备");
        setSupportActionBar(mToolBar);
      /*  mToolBar.setNavigationIcon(R.mipmap.back_icon);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

        registerHeadsetPlugReceiver();
        audoManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        RxView.clicks(mLayoutSuccess).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = null;
                if (SwipingCardUtils.swipecardhelp(InstallWaitActivity.this).getArray(1, 1) == 1) {
                    intent = new Intent(InstallWaitActivity.this, InstallConfirmActivity.class);
                    intent.putExtra("confirm_step", 1);
                } else {
                    intent = new Intent(InstallWaitActivity.this, CarTrailActivity.class);
                    App.GravityListener_type = 1;
                    App.SWIPE_STEP = 4;
                    App.UPLOAD_STEP = 3;
                    App.GravityListener_type = 1;
                    intent.putExtra("car_trail", 1);//进入时记录路线
                }
                startActivity(intent);
                finish();
              /*  confirmDialog = new SweetAlertDialog(InstallWaitActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("是否安装完毕?")
                        .setContentText("确认安装完成，开始记录轨迹")
                        .setConfirmText("确认")
                        .setCancelText("取消")
                        .setCustomImage(R.mipmap.card_icon)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();


                                //安装完成，需要管理员刷卡来确认后，再进入下一步
                                Intent intent = new Intent(InstallWaitActivity.this, InstallConfirmActivity.class);
                                intent.putExtra("confirm_step",1);
                                startActivity(intent);

                                *//*Intent intent = new Intent(InstallWaitActivity.this, CarTrailActivity.class);
                                intent.putExtra("car_trail", 1);//进入时记录路线
                                startActivity(intent);*//*
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        });
                confirmDialog.show();*/

            }
        });
    }

    private void registerHeadsetPlugReceiver() {
        headsetPlugReceiver = new HeadsetPlugReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(headsetPlugReceiver, filter);
    }

    //监听
    public class HeadsetPlugReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_HEADSET_PLUG.equals(intent.getAction())) {
                LogUtils.e("state head --->" + intent.getIntExtra("state", 0));
                if (intent.hasExtra("state")) {
                    if (intent.getIntExtra("state", 0) == 0) {
                        if (!isFirstOpen) {
                            if (confirmDialog != null) {
                                confirmDialog.dismissWithAnimation();
                                mInstallOverTextView.setText("设备非法拔出，已报警记录");
                                ToastUtils.showLong("设备非法拔出，出发警报");
                                return;
                            }

                            ToastUtils.showLong("请安装设备");
                            mLayoutInstallWait.setVisibility(View.VISIBLE);
                            mLayoutOver.setVisibility(View.GONE);
                        }
                        isFirstOpen = false;
                    }
                    if (intent.getIntExtra("state", 0) == 1) {
                        if (!isFirstOpen) {
                            ToastUtils.showLong("设备已安装,请管理员刷卡确认");
                            mLayoutInstallWait.setVisibility(View.GONE);
                            mLayoutOver.setVisibility(View.VISIBLE);
                            mLayoutSuccess.setVisibility(View.VISIBLE);

                            //安装完成，需要管理员刷卡来确认后，再进入下一步
                            Intent cfIntent = new Intent(InstallWaitActivity.this, InstallConfirmActivity.class);
                            intent.putExtra("confirm_step", 1);
                            startActivity(cfIntent);
                            finish();
                        }
                        isFirstOpen = false;
                    }
                }
            }
        }
    }

    private void unregisterReceiver() {
        if (headsetPlugReceiver != null) {
            this.unregisterReceiver(headsetPlugReceiver);
            headsetPlugReceiver = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
