package com.zxdz.car.main.view.lock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.zxdz.car.App;
import com.zxdz.car.R;
import com.zxdz.car.base.helper.CarTravelHelper;
import com.zxdz.car.base.utils.AudioPlayUtils;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.contract.PersionInfoContract;
import com.zxdz.car.main.model.domain.DriverInfo;
import com.zxdz.car.main.model.domain.OpenLockInfo;
import com.zxdz.car.main.model.domain.PersionInfo;
import com.zxdz.car.main.model.domain.PoliceInfoAll;
import com.zxdz.car.main.presenter.PersionInfoPresenter;
import com.zxdz.car.main.service.RequestOpenLockService;
import com.zxdz.car.main.service.UploadDataService;
import com.zxdz.car.main.utils.BlueToothHelper;
import com.zxdz.car.main.utils.BlueToothUtils;
import com.zxdz.car.main.utils.UploadInfoUtil;
import com.zxdz.car.main.view.CarTrailActivity;
import com.zxdz.car.main.view.RemoveEquipmentActivity;

import java.util.Date;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.zxdz.car.main.service.RequestOpenLockService.port;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class OpenCardActivity extends BaseActivity<PersionInfoPresenter> implements PersionInfoContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.tv_car_name)
    TextView tvCarName;
    @BindView(R.id.tv_car_number)
    TextView tvCarNumber;
    @BindView(R.id.tv_auth_state)
    TextView tvAuthState;
    @BindView(R.id.layout_card_info)
    LinearLayout layoutCardInfo;
    @BindView(R.id.layout_card_read_wait)
    LinearLayout layoutCardReadWait;
    @BindView(R.id.btn_next_success)
    Button btnNextSuccess;
    @BindView(R.id.layout_next)
    LinearLayout layoutNext;
    @BindView(R.id.open_working)
    TextView openWorking;
    @BindView(R.id.bb_text)
    TextView bbtext;

    private String cardNum = "无";
    private SweetAlertDialog initDialog;
    private boolean flag = true;
    private boolean tag = false;//非法开锁标志
    private Intent intentService;


    private String mcarNumber;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String obj = (String) msg.obj;
            LogUtils.a("mHandler" + obj);
            if (obj.equals("开锁成功")) {
                if (msg.what == 2) {
                    if (step == 1) {
                        App.GravityListener_type = 1;//开启手持机移动报警
                        Intent intent2 = new Intent(OpenCardActivity.this, CarTrailActivity.class);
                        intent2.putExtra("car_trail", 2);
                        startActivity(intent2);
                        //根据读取的卡号查询人员信息
                        if (App.Lock_car_num == 1) {//第一次离开装卸区
                            savedate(2);
                            Log.e("requestinfo", "请求干警信息");
                            mPresenter.getPersionInfo(cardNum);
                            if (!TextUtils.equals(cardNum, null)) {
                                mcarNumber = cardNum;
                                // mHandler.postDelayed(mRunnable, 500);
                            }
                        }
                        finish();
                        return;
                    } else if (step == 2) {
                        Intent intent = new Intent(OpenCardActivity.this, RemoveEquipmentActivity.class);
                        startActivity(intent);
                        finish();
                        return;
                    } else {
                        LogUtils.a("异常step=" + step);
                    }
                }
               /* if (flag) {
                    flag = false;
                    callPolice(1,"强拆锁");

                    tag = true;
                    openWorking.setText("非法开锁，报警中");
                }*/
            } else if (obj.equals("上锁成功")) {
                openWorking.setText("工作中,工作完成后请刷卡");
                if (tag) {
                    callPolice(2, "强拆锁");
                }
                flag = true;
            } else if (obj.equals("失败成功")) {
                openlock(2);
            }
        }
    };

    private int step;
    private boolean statecallpolice = false;
    private UploadInfoUtil uploadInfoUtil;
    private Intent intent = null;
    private boolean isOpenAble = true;

    @Override
    public int getLayoutId() {
        return R.layout.activity_open_card;
    }

    @Override
    public void init() {
        setSupportActionBar(mToolBar);
        mToolBar.setTitle("开锁拆除");
        //step = getIntent().getIntExtra("blue_step", 0);
        bbtext.setText("开锁：请带车民警刷卡");
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            step = bundle.getInt("blue_step");
        }
        if (step == 2) {
            openWorking.setText("滞留区等待中，结束后请刷卡开锁");
        }
        AudioPlayUtils.getAudio(this, R.raw.zpscwc_qdcmjjkzqfhyc).play();//照片上传完成，请带车民警将控制器放回原处

        SharedPreferences sp = getSharedPreferences("OpenLockStyle", MODE_PRIVATE);
        int open_style = sp.getInt("open_style", 2);
        requestOpenLock(open_style);
        //开启强拆报警监测
        BlueToothHelper.getBlueHelp().openCallPolices(new BlueToothUtils.openCallPoliceListener() {
            @Override
            public void openCallPolice() {
                if (!statecallpolice) {
                    statecallpolice = true;
                    //policeingunclick();
                    AudioPlayUtils.getAudio(OpenCardActivity.this, R.raw.baojing).play(true);
                    callPolice(1, "强拆锁");
                }
            }
        });
        mPresenter = new PersionInfoPresenter(this, this);
        intentService = new Intent(this, UploadDataService.class);
    }

    private void requestOpenLock(final int isrequest) {//true为请求远程开锁
        if (isrequest == 3 && step == 1){
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isOpenAble = false;
                }
            }, 300000);
        }else {
            isOpenAble = false;
        }
        BlueToothHelper.getBlueHelp().setReceiverMode(new BlueToothUtils.receiveCardIDListener() {
            @Override
            public void receiveCardID(final String str) {
                if (statecallpolice) {//刷完卡解除报警
                    AudioPlayUtils.getAudio(OpenCardActivity.this, 0).stop();
                    statecallpolice = false;
                    callPolice(2, "强拆锁");
                } else {
                    if (isrequest == 1) {
                        checkOpenLock("请求远程开锁中。。。");
                        RequestOpenLockService.listener = new RequestOpenLockService.RequestOpenLockListenerserve() {
                            @Override
                            public void successful() {
                                checkOpenLock("请求远程开锁成功！");
                                showCardNumber(str);
                            }
                        };
                        if (intent == null) {
                            intent = new Intent(OpenCardActivity.this, RequestOpenLockService.class);
                        }
                        startService(intent);
                        final OpenLockInfo openLockInfo = new OpenLockInfo();
                        openLockInfo.setIP(App.phoneIP);
                        openLockInfo.setPort(port + "");
                        if (uploadInfoUtil == null) {
                            uploadInfoUtil = new UploadInfoUtil(OpenCardActivity.this);
                        }
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                uploadInfoUtil
                                        .uploadRequestOpenLock(openLockInfo, new UploadInfoUtil.RequestOpenLockListener() {
                                            @Override
                                            public void successful() {
//                                    RequestOpenLockService.listener = new RequestOpenLockService.RequestOpenLockListenerserve() {
//                                        @Override
//                                        public void successful() {
//                                            showCardNumber(str);
//                                        }
//                                    };
                                                //startService( new Intent(OpenCardActivity.this,RequestOpenLockService.class));
                                            }
                                        });
                            }
                        }, 1000);
                    } else if (isrequest == 2){
                        showCardNumber(str);
                    }else {
                        AudioPlayUtils.getAudio(OpenCardActivity.this,R.raw.qwcgzhks).play();//请完成工作后开锁
                        showCardNumber(str);
                    }
                }
            }
        });
    }

    ;

    public void showCardNumber(String carNumber) {
        carNumber = carNumber.replaceAll(" ", "");
        if (judgment()) {
            return;
        }
        tvCarNumber.setText(carNumber);
        /*String getname = getname(carNumber);
        tvCarName.setText(getname);*/
//        layoutCardReadWait.setVisibility(View.GONE);
//        layoutCardInfo.setVisibility(View.VISIBLE);
//        layoutNext.setVisibility(View.VISIBLE);
//        tvAuthState.setText(getResources().getText(R.string.card_auth_success_text));
        cardNum = carNumber;
        openlock(2);
        /*Intent intent = new Intent(OpenCardActivity.this, BlueToothActivity.class);
        intent.putExtra("blue_step",2);
        startActivity(intent);
        finish();*/
    }

    private void openlock(final int i) {
        checkOpenLock("开锁中");
        //mHandler.postDelayed(runnable3, 10000);
        BlueToothHelper.getBlueHelp().openLock(new BlueToothUtils.OpenLockListenter() {
            @Override
            public void openable(String str) {
                // TODO: 2018/5/9 语音提示可以开锁
                //mHandler.removeCallbacks(runnable3);
                Message message = new Message();
                message.obj = str;
                message.what = i;
                mHandler.sendMessage(message);
                initDialog.dismissWithAnimation();
            }
        });
    }

    public void checkOpenLock(String msg) {
        try {
            if (initDialog == null) {
                initDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
                initDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                initDialog.setTitleText(msg);
                initDialog.setCancelable(false);
                initDialog.show();
                initDialog.setCanceledOnTouchOutside(true);
            } else {
                if (!initDialog.isShowing()) {
                    initDialog.setTitleText(msg);
                    initDialog.show();
                } else {
                    initDialog.dismissWithAnimation();
                    initDialog.setTitleText(msg);
                    initDialog.show();
                }
            }
        } catch (Exception e) {
            LogUtils.a("benbenbenebn");
        }
    }

    private void savedate(int i) {
        cardNum = cardNum.replaceAll(" ", "");
        if (i == 2) {//开锁
            CarTravelHelper.carTravelRecord.setDLGJ_KSSJ(new Date());
            CarTravelHelper.carTravelRecord.setDLGJ_KSKH(cardNum);
            CarTravelHelper.carTravelRecord.setZT(50);
            CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
            App.SWIPE_STEP = 6;
            //上传主信息记录
            App.UPLOAD_STEP = 5;
        }
    }

    private boolean judgment() {
        // TODO: 2017\12\22 0022  //是否已经安装设备，没有则语音提示请安装
        return isOpenAble;
    }

    @Override
    public void hideStateView() {

    }

    @Override
    public void showNoNet() {

    }

    @Override
    public void showPoliceInfoAll(PoliceInfoAll persionInfo) {
        if (persionInfo != null) {
            if (CarTravelHelper.carTravelRecord != null) {
                CarTravelHelper.carTravelRecord.setDLGJ_KSXM(persionInfo.getDLGJ_XM());
                CarTravelHelper.carTravelRecord.setDLGJ_KSJH(persionInfo.getDLGJ_JH());
                CarTravelHelper.carTravelRecord.setDLGJ_KSBM(persionInfo.getDLGJ_BMMC());
                CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
            }
        }
        startService(intentService);
        finish();
    }

    @Override
    public void showdriverInfo(DriverInfo driverInfo) {

    }

    @Override
    public void showPersionInfo(PersionInfo persionInfo) {
        if (persionInfo != null && persionInfo.getType() == 1) {//type = 0结果暂时添加
            tvCarName.setText(persionInfo.getName());
            tvCarNumber.setText(persionInfo.getAlarm());
            if (CarTravelHelper.carTravelRecord != null) {
                CarTravelHelper.carTravelRecord.setDLGJ_LYXM(persionInfo.getName());
                CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
            }
            startService(intentService);
        } else if (persionInfo != null && persionInfo.getType() == 2) {
            tvCarName.setText(persionInfo.getName());
            tvCarNumber.setText(persionInfo.getAlarm());
            startService(intentService);
        } else {
            startService(intentService);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        intent = null;
        initDialog.dismiss();
    }
}