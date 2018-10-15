package com.zxdz.car.main.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.zxdz.car.App;
import com.zxdz.car.R;
import com.zxdz.car.base.utils.AudioPlayUtils;
import com.zxdz.car.base.utils.SwipingCardUtils;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.view.lock.BluetoothLock;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.functions.Action1;

/**
 * 拆除设备
 */

public class RemoveEquipmentActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @BindView(R.id.layout_wait_remove)
    LinearLayout mLayoutWaitRemove;

    @BindView(R.id.layout_remove_success)
    LinearLayout mLayoutRemoveSuccess;

    @BindView(R.id.layout_return)
    LinearLayout mLayoutReturn;

    @BindView(R.id.btn_return)
    Button mReturnButton;

    HeadsetPlugReceiver headsetPlugReceiver;

    private AudioManager audoManager;

    private boolean isFirstOpen = true;
    private AudioPlayUtils audioPlayUtils;

    @Override
    public int getLayoutId() {
        return R.layout.activity_wait_remove;
    }

    @Override
    public void init() {
        App.GravityListener_type=0;
        audioPlayUtils = new AudioPlayUtils(this, R.raw.qdcmjccsb);
        audioPlayUtils.play();

        mToolBar.setTitle("等待拆除设备");
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
        RxView.clicks(mLayoutReturn).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (SwipingCardUtils.swipecardhelp(RemoveEquipmentActivity.this).getArray(4,0) == 1){
                    Intent intent = new Intent(RemoveEquipmentActivity.this, ReturnRefreshCardActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent cfIntent = new Intent(RemoveEquipmentActivity.this, InstallConfirmActivity.class);
                    cfIntent.putExtra("confirm_step", 3);
                    startActivity(cfIntent);
                    finish();
                }

               /* new SweetAlertDialog(RemoveEquipmentActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("设备已拆除，准备交还?")
                        .setContentText("确认行程结束，设备交还刷卡")
                        .setConfirmText("刷卡")
                        .setCancelText("取消")
                        .setCustomImage(R.mipmap.card_icon)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                Intent intent = new Intent(RemoveEquipmentActivity.this, ReturnRefreshCardActivity.class);
                                startActivity(intent);
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .show();*/
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
                if (intent.hasExtra("state")) {
                    if (intent.getIntExtra("state", 0) == 0) {
                        if(!isFirstOpen) {
                            ToastUtils.showLong("设备已拆除");
                            mLayoutWaitRemove.setVisibility(View.GONE);
                            mLayoutRemoveSuccess.setVisibility(View.VISIBLE);

                            Intent cfIntent = new Intent(RemoveEquipmentActivity.this, InstallConfirmActivity.class);
                            intent.putExtra("confirm_step", 3);
                            startActivity(cfIntent);
                            finish();

                        }
                        isFirstOpen = false;
                    }

                    if (intent.getIntExtra("state", 0) == 1) {
                        if (!isFirstOpen) {
                            ToastUtils.showLong("设备已安装");
                        }
                        isFirstOpen = false;
                        /*mLayoutWaitRemove.setVisibility(View.GONE);
                        mLayoutRemoveSuccess.setVisibility(View.VISIBLE);
                        mLayoutReturn.setVisibility(View.VISIBLE);*/
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
