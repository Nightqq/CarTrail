package com.zxdz.car.main.view;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zxdz.car.App;
import com.zxdz.car.R;
import com.zxdz.car.base.helper.CarTravelHelper;
import com.zxdz.car.base.utils.AudioPlayUtils;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.contract.PersionInfoContract;
import com.zxdz.car.main.model.domain.PoliceInfoAll;
import com.zxdz.car.main.presenter.PersionInfoPresenter;
import com.zxdz.car.main.service.UploadDataService;
import com.zxdz.car.main.utils.BlueToothHelper;
import com.zxdz.car.main.utils.BlueToothUtils;

import java.util.Date;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by super on 2017/10/22.
 * 带车干警刷卡界面
 */

public class PoliceCardActivity extends BaseActivity<PersionInfoPresenter> implements PersionInfoContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @BindView(R.id.btn_success)
    Button mSuccessButton;

    @BindView(R.id.layout_card_read_wait)
    LinearLayout mReadWaitLayout;

    @BindView(R.id.layout_card_info)
    LinearLayout mCardInfoLayout;

    @BindView(R.id.layout_next)
    LinearLayout mNextLayout;

    @BindView(R.id.tv_car_number)
    TextView mCardNumberTextView;

    @BindView(R.id.tv_car_name)
    TextView tvCarNameTextView;

    @BindView(R.id.tv_alarm)
    TextView tvAlarmTextView;

    @BindView(R.id.tv_dept)
    TextView tvDeptTextView;

    AudioPlayUtils audioPlayUtils;

    private boolean flag = true;//跳转标准，避免多次多卡触发重复跳转
    private boolean flag2 = true;
    private Intent intentService;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    public int getLayoutId() {
        return R.layout.activity_police_card;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void init() {
        App.SWIPE_STEP = 2;
        mToolBar.setTitle("带车民警刷卡");
        setSupportActionBar(mToolBar);
     /*   mToolBar.setNavigationIcon(R.mipmap.back_icon);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
        mToolBar.setOnMenuItemClickListener(onMenuItemClick);

        mPresenter = new PersionInfoPresenter(this, this);
        intentService = new Intent(this, UploadDataService.class);

        audioPlayUtils = new AudioPlayUtils(this, R.raw.lysljcg_qdcmjqrlysk);
        audioPlayUtils.play();
        BlueToothHelper.getBlueHelp().setReceiverMode(new BlueToothUtils.receiveCardIDListener() {
            @Override
            public void receiveCardID(String str) {
                showCardNumber(str);
            }
        });

        /*//带领干警刷卡(模拟刷卡成功)
        RxView.clicks(mSuccessButton).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                handler.removeCallbacks(runnable);
                if (App.DRIVER_SWIPE == 0) {
                    Intent intent = new Intent(PoliceCardActivity.this, DriverSwipeCardActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(PoliceCardActivity.this, InputCarInfoActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
        });*/

        //模拟测试步骤，后期删除
        //TODO
        // mNextLayout.setVisibility(View.VISIBLE);
    }

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.action_change:
                    new SweetAlertDialog(PoliceCardActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                            .setTitleText("是否更换带车民警?")
                            .setContentText("更换带车民警，请先刷管理员卡")
                            .setConfirmText("有卡")
                            .setCancelText("无卡")
                            .setCustomImage(R.mipmap.card_icon)
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                    /*Intent intent = new Intent(CarAdminActivity.this, DriverSwipeCardActivity.class);
                                    startActivity(intent);*/
                                    ToastUtils.showLong("与前面刷卡刷卡类似，待完善");
                                }
                            })
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sweetAlertDialog) {
                                    sweetAlertDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                    break;
            }
            return true;
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

   /* @Override
    protected void onResume() {
        super.onResume();
        if (App.readCardType == 1) {
            new OpenT().start();
        } else {
            new InitT().start();
        }
    }

    @Subscribe(
            thread = EventThread.MAIN_THREAD,
            tags = {
                    @Tag(Constant.READ_CARD_NUMBER)
            }
    )*/

    /**
     * 显示读取到的卡号
     */
    public void showCardNumber(String carNumber) {
        if (App.SWIPE_STEP == 2) {
            if (StringUtils.isEmpty(carNumber)) {
                ToastUtils.showLong("卡号读取错误，请重试");
                return;
            }
            //去除卡号中的空格
            carNumber = carNumber.replaceAll(" ", "");

            mCardNumberTextView.setText(carNumber);

            mReadWaitLayout.setVisibility(View.GONE);
            //存储卡号到本地数据库
            saveAdminCard(carNumber);
            //保存干警卡号，后期更换干警判断
//            SharedPreferences sp = getSharedPreferences("activity", Context.MODE_WORLD_READABLE);
//            SharedPreferences.Editor edit = sp.edit();
//            edit.putString("policeNum", carNumber);
//            edit.commit();

            mCardInfoLayout.setVisibility(View.VISIBLE);
            //mNextLayout.setVisibility(View.VISIBLE);

            //----TODO 临时代码
            /*String path =  Environment.getExternalStorageDirectory().getPath();
            Bitmap tempBitmap = BitmapFactory.decodeFile(path + "/test.jpg");
            saveImageInSDCard(SwitchUtils.getBitmapByte(tempBitmap));*/
            //-----
            handler.postDelayed(runnable, 2000);
            //根据读取的卡号查询人员信息
            mPresenter.getPersionInfo(carNumber);
        }
    }

    /**
     * 读卡成功保存【带车干警卡号】
     *
     * @param cardNumber
     */
    public void saveAdminCard(String cardNumber) {
        CarTravelHelper.carTravelRecord .setDLGJ_LYKH(cardNumber);
        CarTravelHelper.carTravelRecord .setDLGJ_LYSJ(new Date());
        CarTravelHelper.carTravelRecord .setZT(10);//领用
        CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord );
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
            tvCarNameTextView.setText(persionInfo.getDLGJ_XM());
            tvAlarmTextView.setText(persionInfo.getDLGJ_JH());
            tvDeptTextView.setText(persionInfo.getDLGJ_BMMC());
        }
        if (CarTravelHelper.carTravelRecord != null) {
            CarTravelHelper.carTravelRecord .setDLGJ_LYXM(persionInfo.getDLGJ_XM());
            CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord );
        }
        //上传主信息记录
        App.UPLOAD_STEP = 1;
        if (flag2) {
            flag2 = false;
            LogUtils.a("开始保存数据");
            startService(intentService);
        }

        if (App.DRIVER_SWIPE == 0) {
            if (flag) {
                flag = false;
                handler.postDelayed(runnable, 2000);
            }
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mSuccessButton.callOnClick();
        }
    };


    /**
     * 保存图片信息到本地
     *
     * @param imageinfos
     */
    public void saveImageInSDCard(String imageinfos) {
        if (CarTravelHelper.carTravelRecord != null) {
            CarTravelHelper.carTravelRecord.setGJPZPNG(imageinfos);
            CarTravelHelper.carTravelRecord.setGJPZSJ(new Date());
            CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
            LogUtils.e("已拍照完成，图片信息保存到本地--->" + imageinfos);
        }
    }

}
