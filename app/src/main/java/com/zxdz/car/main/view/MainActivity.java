package com.zxdz.car.main.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.acker.simplezxing.activity.CaptureActivity;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding.view.RxView;
import com.kk.securityhttp.domain.ResultInfo;
import com.zxdz.car.App;
import com.zxdz.car.R;
import com.zxdz.car.base.helper.AreaHelper;
import com.zxdz.car.base.helper.CarTravelHelper;
import com.zxdz.car.base.helper.TerminalInfoHelper;
import com.zxdz.car.base.utils.AudioPlayUtils;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.contract.UploadInfoContract;
import com.zxdz.car.main.model.domain.AreaInfo;
import com.zxdz.car.main.model.domain.Constant;
import com.zxdz.car.main.model.domain.QrCodeToMAC;
import com.zxdz.car.main.model.domain.TerminalInfo;
import com.zxdz.car.main.presenter.UploadInfoPresenter;
import com.zxdz.car.main.service.UploadDataService;
import com.zxdz.car.main.utils.BlueToothHelper;
import com.zxdz.car.main.utils.BlueToothUtils;
import com.zxdz.car.main.view.lock.AuthBlueLinkActivity;
import com.zxdz.car.main.view.lock.BlueToothActivity;
import com.zxdz.car.main.view.lock.OpenCardActivity;
import com.zxdz.car.main.view.setting.PasswordValidataActivity;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.functions.Action1;

/**
 * Created by super on 2017/10/16.
 */

public class MainActivity extends BaseActivity<UploadInfoPresenter> implements UploadInfoContract.View {
    @BindView(R.id.title_text)
    TextView titleText;
    @BindView(R.id.layout_setting)
    ImageView mSettingLayout;
    @BindView(R.id.btn_read_card)
    Button mReadCardButton;
    @BindView(R.id.btn_read_ic_card)
    Button mReadIcCardButton;
    @BindView(R.id.layout_car_in)
    LinearLayout mCardInLayout;
    @BindView(R.id.layout_data_upload)
    LinearLayout mUploadLayout;
    @BindView(R.id.layout_bluetooth_lock)
    LinearLayout mBlueToothLayout;
    @BindView(R.id.iv_upload_loading)
    ImageView mUploadLoadingImageView;
    @BindView(R.id.iv_upload_done)
    ImageView mUploadDoneImageView;
    @BindView(R.id.tv_upload_hit)
    TextView mUploadHitTextView;

    private AreaInfo areaInfo;

    private TerminalInfo terminalInfo;

    private UpdateInfoBroadcastReceiver updateInfoBroadcastReceiver;
    private AudioPlayUtils audioPlayUtils;
    private SweetAlertDialog initDialog;
    private int flag = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    private Handler handler = new Handler();

    Intent intentService;
    private int end;


    @Override
    public void init() {
        end = getIntent().getIntExtra("end", 0);
        if (end == 1) {
            audioPlayUtils = new AudioPlayUtils(this, R.raw.gclcjs);
            audioPlayUtils.play();
        }
        Glide.with(this).load(R.mipmap.upload_loading).into(mUploadLoadingImageView);
        mPresenter = new UploadInfoPresenter(this, this);
        intentService = new Intent(MainActivity.this, UploadDataService.class);

        //设置是否继续任务
        setLastStep();

        setCardType();
        String androidID = DeviceUtils.getAndroidID();
        Log.e("串口号", androidID);

        //车辆进入
        RxView.clicks(mCardInLayout).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(MainActivity.this, CaptureActivity.class);
                startActivityForResult(intent, CaptureActivity.REQ_CODE);
               /* String title = "";
                String content = "";
                if (App.ADMIN_SWIPE == 0) {
                    title = "请刷管理员授权卡";
                    content = "请管理员刷卡认证";
                    swipeCard(1, title, content);
                    return;
                } else
                    {
                    if (App.POLICE_SWIPE == 0) {
                        title = "请带领干警刷卡";
                        content = "请带领干警刷卡认证";
                        swipeCard(2, title, content);
                        return;
                    } else {
                        if (App.DRIVER_SWIPE == 0) {
                            title = "请驾驶员刷卡";
                            content = "请驾驶员刷卡认证";
                            swipeCard(3, title, content);
                            return;
                        } else {
                            //都不刷卡，则直接跳转到录入信息界面，数据上传？
                            Intent intent = new Intent(MainActivity.this, InputCarInfoActivity.class);
                            startActivity(intent);
                        }
                    }
                }*/
            }
        });

        RxView.clicks(mUploadHitTextView).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("流程是否全部完成，开始上传数据?")
                        .setContentText("数据上传请保持网络通畅")
                        .setConfirmText("确认")
                        .setCancelText("取消")
                        .setCustomImage(R.mipmap.card_icon)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                //数据上传
                                //uploadDataInfo();
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .show();
                /*Intent intent = new Intent(MainActivity.this, AudioActivity.class);
                startActivity(intent);*/
            }
        });

        RxView.clicks(mBlueToothLayout).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
               /* Intent intent = new Intent(MainActivity.this, OpenLockActivity.class);
                startActivity(intent);*/
                Intent intent = new Intent(MainActivity.this, AuthBlueLinkActivity.class);
                intent.putExtra("police_card", "140B2226");
                intent.putExtra("blue_step", 1);
                startActivity(intent);
            }
        });

        RxView.clicks(mSettingLayout).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(MainActivity.this, PasswordValidataActivity.class);
                startActivity(intent);
            }
        });
        // 动态注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.UPLOAD_INFO_RESULT);
        updateInfoBroadcastReceiver = new UpdateInfoBroadcastReceiver();
        registerReceiver(updateInfoBroadcastReceiver, filter);
    }

    String mac = "";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CaptureActivity.REQ_CODE:
                switch (resultCode) {
                    case RESULT_OK:
                        String stringExtra = data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT);
                        LogUtils.a(stringExtra);
                        mac= QrCodeToMAC.getMAC(stringExtra);
                        checkOpenLock("连接中");
                        BlueToothHelper.getBlueHelp().closeAll();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                              BlueToothHelper.getBlueHelp().ConnectedDevices(mac, new BlueToothUtils.ConnectedDevicesListenter() {
                                  @Override
                                  public void connectenDevice(int i) {
                                      if (i == 1) { //连接成功跳转管理员刷卡页面
                                          if (initDialog!=null&&initDialog.isShowing()){
                                              initDialog.dismiss();
                                          }
                                          startActivity(new Intent(MainActivity.this, AuthCardActivity.class));
                                      }
                                  }
                              },true);
                            }
                        },2000);

                        break;
                    case RESULT_CANCELED:
                       /* String stringExtra2 = data.getStringExtra(CaptureActivity.EXTRA_SCAN_RESULT);
                        LogUtils.a(stringExtra2);*/
                        break;
                }
                break;
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        App.IsNewLS=false;
        uploadDataInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (TerminalInfoHelper.getTerminalInfoListFromDB() != null && TerminalInfoHelper.getTerminalInfoListFromDB().size() > 0) {
            terminalInfo = TerminalInfoHelper.getTerminalInfoListFromDB().get(0);
        }
        if (terminalInfo != null) {
            areaInfo = AreaHelper.getAreaInfoByParams(terminalInfo.getZdjId(), terminalInfo.getAreaId());
        }
        //根据设备获取是否读卡的信息
        // TODO: 2018/8/20
//        if (areaInfo != null) {
//            App.ADMIN_SWIPE = areaInfo.getAdminPunchIn();
//            App.POLICE_SWIPE = areaInfo.getPolicePunChIn();
//            App.DRIVER_SWIPE = areaInfo.getDriverPunchIn();
//        }
        App.ADMIN_SWIPE = 0;
        App.POLICE_SWIPE = 0;
        App.DRIVER_SWIPE = 0;
    }
    /**
     * 进入主界面，首先判断是否有未完成的记录
     */
    public void setLastStep() {
        if (CarTravelHelper.getCarTravelRecord() != null) {
            CarTravelHelper.carTravelRecord = CarTravelHelper.getCarTravelRecord();
            App.LSID=new Long(CarTravelHelper.carTravelRecord.getLS_ID());
            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText("操作继续")
                    .setContentText("监测到有未完成的记录，是否继续上次行程？")
                    .setConfirmText("继续")
                    .setCancelText("取消")
                    .setCustomImage(R.mipmap.card_icon)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            int state = CarTravelHelper.getCarTravelRecord().getZT();
                            Intent intent = null;
                            switch (state) {
                                case 0:
                                    App.UPLOAD_STEP = 1;
                                    App.SWIPE_STEP = 1;
                                    intent = new Intent(MainActivity.this, AuthCardActivity.class);
                                    break;
                                case 10:
                                    App.UPLOAD_STEP = 1;
                                    App.SWIPE_STEP = 2;
                                    intent = new Intent(MainActivity.this, InstallWaitActivity.class);
                                    break;
                                case 11:
                                    App.UPLOAD_STEP = 2;
                                    App.SWIPE_STEP = 3;
                                    intent = new Intent(MainActivity.this, InstallWaitActivity.class);
                                    break;
                                case 20:
                                    App.UPLOAD_STEP = 3;
                                    App.SWIPE_STEP = 4;
                                    intent = new Intent(MainActivity.this, CarTrailActivity.class);
                                    intent.putExtra("car_trail", 1);
                                    break;
                                case 30:
                                    //暂无
                                    break;
                                case 40:
                                    App.UPLOAD_STEP = 4;
                                    App.SWIPE_STEP = 5;
                                    intent = new Intent(MainActivity.this, BlueToothActivity.class);
                                    intent.putExtra("blue_step", 1);
                                    break;
                                case 45:
                                    App.UPLOAD_STEP = 5;
                                    App.SWIPE_STEP = 5;
                                    intent = new Intent(MainActivity.this, OpenCardActivity.class);
                                    break;
                                case 50:
                                    App.UPLOAD_STEP = 5;
                                    App.SWIPE_STEP = 6;
                                    intent = new Intent(MainActivity.this, CarTrailActivity.class);
                                    intent.putExtra("car_trail", 2);
                                    break;
                               /* case 70:
                                    App.UPLOAD_STEP = 6;
                                    App.SWIPE_STEP = 7;
                                    intent = new Intent(MainActivity.this, InstallConfirmActivity.class);
                                    intent.putExtra("confirm_step", 3);
                                    break;*/
                                default:
                                    break;
                            }
                            final Intent finalIntent = intent;
                            checkOpenLock("重新连接中");
                            BlueToothHelper.getBlueHelp().closeAll();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    BlueToothHelper.getBlueHelp().ConnectedDevices(mac, new BlueToothUtils.ConnectedDevicesListenter() {
                                        @Override
                                        public void connectenDevice(int i) {
                                            if (i == 1) { //连接成功跳转管理员刷卡页面
                                                if (initDialog!=null&&initDialog.isShowing()){
                                                    initDialog.dismiss();
                                                }
                                                if (finalIntent != null) {
                                                    startActivity(finalIntent);
                                                }
                                            }
                                        }
                                    },false);
                                }
                            },2000);
                        }
                    })

                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            //此处不让用户取消，智能继续上一次的任务

                            //删除部分未上传，未获取流水id的数据
                            //List<CarTravelRecord> carTravelRecordListFromDB = CarTravelHelper.getCarTravelRecordListFromDB();

                        }
                    })
                    .show();
        }
    }

    /**
     * 设置读卡的类型
     */
    public void setCardType() {
        int cardType = SPUtils.getInstance().getInt(Constant.READ_CARD_TYPE, Constant.NOT_SELECT_TYPE_ID);
        App.readCardType = cardType;
        if (cardType == Constant.NOT_SELECT_TYPE_ID) {
            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText("请先设置读卡类型")
                    .setContentText("确保读卡正确，请首先设置读卡类型")
                    .setConfirmText("ID卡")
                    .setCancelText("IC卡")
                    .setCustomImage(R.mipmap.card_icon)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            App.isRFID = true;
                            App.readCardType = Constant.TYPE_ID;
                            SPUtils.getInstance().put(Constant.READ_CARD_TYPE, Constant.TYPE_ID);
                        }
                    })
                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            sweetAlertDialog.dismissWithAnimation();
                            App.isRFID = false;
                            App.readCardType = Constant.TYPE_IC;
                            SPUtils.getInstance().put(Constant.READ_CARD_TYPE, Constant.TYPE_IC);
                        }
                    })
                    .show();
        }
    }

    /**
     * 数据上传
     */
    public void uploadDataInfo() {
        //TODO
        //上传数据
        if (CarTravelHelper.carTravelRecord != null) {
            mUploadHitTextView.setText("数据正在上传···");
            startService(intentService);
            ToastUtils.showLong("数据上传中");
            mUploadLoadingImageView.setVisibility(View.VISIBLE);
            mUploadDoneImageView.setVisibility(View.GONE);
        } else {
            ToastUtils.showLong("未检测到流水信息，数据已经全部上传");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    /**
     * 定义广播接收器
     */
    private class UpdateInfoBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //更新数据上传结果
            int resultCode = intent.getExtras().getInt("result_code");
            LogUtils.e("uploadResult --->" + resultCode);
            //同步完成
            if (resultCode == 0) {
                //ToastUtils.showLong("数据上传完成");
                mUploadLoadingImageView.setVisibility(View.GONE);
                mUploadDoneImageView.setVisibility(View.VISIBLE);
                mUploadHitTextView.setText("数据上传完成");
            }
            //同步中
            if (resultCode == 1) {
                mUploadLoadingImageView.setVisibility(View.VISIBLE);
                mUploadDoneImageView.setVisibility(View.GONE);
                mUploadHitTextView.setText("数据正在上传···");
            }
        }
    }

    @Override
    public void hideStateView() {

    }

    @Override
    public void showNoNet() {

    }

    @Override
    public void loadCarRecordUpload(ResultInfo resultInfo) {
        ToastUtils.showLong("数据上传成功");
    }

    @Override
    public void loadTrailUpload(ResultInfo resultInfo) {
        ToastUtils.showLong("数据上传成功");
    }

    @Override
    public void loadWarnUpload(ResultInfo resultInfo) {
        ToastUtils.showLong("数据上传成功");
    }

    @Override
    public void loadChangeUpload(ResultInfo resultInfo) {
        ToastUtils.showLong("数据上传成功");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (updateInfoBroadcastReceiver != null) {
            unregisterReceiver(updateInfoBroadcastReceiver);
        }
    }
    public void checkOpenLock(String msg) {
        initDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        initDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        initDialog.setTitleText(msg);
        initDialog.setCancelable(false);
        initDialog.show();
        initDialog.setCanceledOnTouchOutside(true);
    }
}
