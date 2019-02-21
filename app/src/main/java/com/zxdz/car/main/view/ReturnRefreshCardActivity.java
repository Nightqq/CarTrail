package com.zxdz.car.main.view;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.zxdz.car.App;
import com.zxdz.car.R;
import com.zxdz.car.base.helper.CarTravelHelper;
import com.zxdz.car.base.utils.AudioPlayUtils;
import com.zxdz.car.base.utils.SwipingCardUtils;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.service.UploadDataService;
import com.zxdz.car.main.utils.BlueToothHelper;
import com.zxdz.car.main.utils.BlueToothUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.functions.Action1;

/**
 * 交还设备刷卡
 */

public class ReturnRefreshCardActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolBar;

    @BindView(R.id.btn_next)
    Button mNextButton;

    @BindView(R.id.tv_police_card_number)
    TextView mPoliceCardTextView;

    @BindView(R.id.tv_admin_card_number)
    TextView mAdminCardTextView;

    @BindView(R.id.layout_card_read_wait)
    LinearLayout mReadWaitLayout;

    @BindView(R.id.layout_refresh_card_info)
    LinearLayout mRefreshCardInfoLayout;

    @BindView(R.id.layout_police_card_info)
    LinearLayout mPoliceLayout;

    @BindView(R.id.layout_admin_card)
    LinearLayout mAdminLayout;

    @BindView(R.id.layout_next)
    LinearLayout mNextLayout;

    @BindView(R.id.tv_car_name)
    TextView tvCarName;
    private boolean flag = true;//跳转标准，避免多次多卡触发重复跳转
    private Intent intentserver;

    @Override
    public int getLayoutId() {
        return R.layout.activity_return_card;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void init() {
        AudioPlayUtils.getAudio(this, R.raw.qglryshsbqrsk).play();
        mToolBar.setTitle("确认行程结束");
        setSupportActionBar(mToolBar);
       /* mToolBar.setNavigationIcon(R.mipmap.back_icon);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/
        intentserver = new Intent(this, UploadDataService.class);
        BlueToothHelper.getBlueHelp().setReceiverMode(new BlueToothUtils.receiveCardIDListener() {
            @Override
            public void receiveCardID(String str) {
               showCardNumber(str);
            }
        });

        RxView.clicks(mNextButton).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                //ToastUtils.showLong("行程结束,可手动上传数据");
                if (SwipingCardUtils.swipecardhelp(ReturnRefreshCardActivity.this).getArray(4,1) == 1){
                    Intent cfIntent = new Intent(ReturnRefreshCardActivity.this, InstallConfirmActivity.class);
                    cfIntent.putExtra("confirm_step", 3);
                    startActivity(cfIntent);
                    finish();
                }else {
                    App.SWIPE_STEP = 7;
                    App.UPLOAD_STEP = 6;
                    AudioPlayUtils.getAudio(ReturnRefreshCardActivity.this, R.raw.gclcjs).play();
                    Intent intent = new Intent(ReturnRefreshCardActivity.this, InitReturnActivity.class);
                    startActivity(intent);
                    startService(intentserver);
                    finish();

                }
               /* new SweetAlertDialog(ReturnRefreshCardActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("带领干警刷卡?")
                        .setContentText("请带领干警刷卡")
                        .setConfirmText("刷卡")
                        .setCancelText("取消")
                        .setCustomImage(R.mipmap.card_icon)
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                Intent intent = new Intent(ReturnRefreshCardActivity.this, InstallConfirmActivity.class);
                                intent.putExtra("confirm_step", 4);
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
        if (App.SWIPE_STEP == 6) {
            if (StringUtils.isEmpty(carNumber)) {
                ToastUtils.showLong("卡号读取错误，请重试");
                return;
            }

            carNumber = carNumber.replaceAll(" ", "");
//            if (mNextLayout.getVisibility() == View.GONE) {
//
//                mPoliceCardTextView.setText(carNumber);
//                mReadWaitLayout.setVisibility(View.GONE);

                //存储卡号到本地数据库
                saveAdminCard(carNumber);
//                mRefreshCardInfoLayout.setVisibility(View.VISIBLE);
//                mNextLayout.setVisibility(View.VISIBLE);
//                mPoliceLayout.setVisibility(View.VISIBLE);
//                mAdminLayout.setVisibility(View.GONE);
//            } else {
//                mAdminCardTextView.setText(carNumber);
//                mReadWaitLayout.setVisibility(View.GONE);
                //存储卡号到本地数据库
            //    saveAdminCard(carNumber);
//                mPoliceLayout.setVisibility(View.GONE);
//                mAdminLayout.setVisibility(View.VISIBLE);
//                mNextLayout.setVisibility(View.VISIBLE);
//                mNextButton.setText("完成");
//            }

            if (flag) {
                mNextButton.callOnClick();
                flag = false;
            }
        }
    }

    /**
     * 读卡成功保存管理员卡号
     *
     * @param cardNumber
     */
    public void saveAdminCard(String cardNumber) {
        if (CarTravelHelper.carTravelRecord != null) {
            CarTravelHelper.carTravelRecord.setDLGJ_JHKH(cardNumber);
            CarTravelHelper.carTravelRecord.setDLGJ_JHSJ(new Date());
            CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
            LogUtils.e("读取到的卡号--->" + CarTravelHelper.carTravelRecord.getDLGJ_JHKH());
        }
    }

}
