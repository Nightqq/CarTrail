package com.zxdz.car.main.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jakewharton.rxbinding.view.RxView;
import com.zxdz.car.App;
import com.zxdz.car.R;
import com.zxdz.car.base.helper.AreaHelper;
import com.zxdz.car.base.helper.CardHelper;
import com.zxdz.car.base.helper.PoliceInfoAllHelper;
import com.zxdz.car.base.helper.RouteHelper;
import com.zxdz.car.base.helper.TerminalInfoHelper;
import com.zxdz.car.base.utils.AudioPlayUtils;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.contract.SettingInfoContract;
import com.zxdz.car.main.model.domain.AreaInfo;
import com.zxdz.car.main.model.domain.CardInfo;
import com.zxdz.car.main.model.domain.Constant;
import com.zxdz.car.main.model.domain.PoliceInfoAll;
import com.zxdz.car.main.model.domain.SettingInfo;
import com.zxdz.car.main.model.domain.TerminalInfo;
import com.zxdz.car.main.presenter.SettingInfoPresenter;
import com.zxdz.car.main.view.setting.PasswordValidataActivity;
import com.zxdz.car.main.view.setting.ServerIPActivity;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.functions.Action1;

/**
 * Created by iflying on 2017/11/8.
 */

public class InitActivity extends BaseActivity<SettingInfoPresenter> implements SettingInfoContract.View {

    @BindView(R.id.btn_init_setting)
    Button mInitSettingButton;

    private SweetAlertDialog initDialog;

    private SweetAlertDialog initSettingDialog;

    private SweetAlertDialog errorDialog;

    private boolean isInitEquSuccess = false;

    @Override
    public int getLayoutId() {
        return R.layout.activity_init;
    }

    @Override
    public void init() {
        AudioPlayUtils.getAudio(this, R.raw.init).play();
        LogUtils.e("设备串号--->" + DeviceUtils.getAndroidID());
        mPresenter = new SettingInfoPresenter(this, this);
        initEquInfo();
        //初始化系统参数
        RxView.clicks(mInitSettingButton).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe(new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (isInitEquSuccess) {
                    initSettingInfo();
                } else {
                    initEquInfo();
                }
            }
        });
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        if (requestCode==1){
            initDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            initDialog.getProgressHelper().setBarColor(Color.parseColor("#48D9B2"));
            initDialog.setTitleText("控制器初始化");
            initDialog.setCancelable(false);
            initDialog.show();

            //请求接口
            mPresenter.initEquipment(DeviceUtils.getAndroidID());

            //TODO,测试代码
            //把本机的编号手动加入，便于测试
            CardInfo cardInfo = new CardInfo();
            cardInfo.setZdjId("101");
            cardInfo.setAdminCardNumber("802eef5198");
            cardInfo.setAdminName("管理员测试账号");
            cardInfo.setCardType(0);
            cardInfo.setRemark("测试卡");
            CardHelper.saveCardInfoToDB(cardInfo);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isInitEquSuccess){
            mInitSettingButton.callOnClick();
        }
    }

    /**
     * 初始化设备信息(理论上新设备只需要执行一次)
     */
    public void initEquInfo() {
        boolean isInitDevice = SPUtils.getInstance().getBoolean(Constant.INIT_DEVICE, false);
        if (!isInitDevice) {
            startActivityForResult(new Intent(this, ServerIPActivity.class),1);
        } else {
            App.ZDJID = DeviceUtils.getAndroidID();
            isInitEquSuccess = true;
            ToastUtils.showLong("控制器初始化完成");
            App.readCardType = SPUtils.getInstance().getInt(Constant.READ_CARD_TYPE, Constant.NOT_SELECT_TYPE_ID);

        }
    }


    /**
     * 初始化系统参数信息
     */
    public void initSettingInfo() {
        boolean isInitSetting = false;
        if (!isInitSetting) {
            initSettingDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            initSettingDialog.getProgressHelper().setBarColor(Color.parseColor("#48D9B2"));
            initSettingDialog.setTitleText("初始化系统参数");
            initSettingDialog.setCancelable(false);
            initSettingDialog.show();
            mPresenter.getSettingInfo(App.ZDJID);
        } else {
            ToastUtils.showLong("系统参数初始化完成");
            Intent intent = new Intent(InitActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void hideStateView() {

    }

    @Override
    public void showNoNet() {
        if (initDialog != null && initDialog.isShowing()) {
            initDialog.dismissWithAnimation();
            errorDialog(1);
        }

        if (initSettingDialog != null && initSettingDialog.isShowing()) {
            initSettingDialog.dismissWithAnimation();
            errorDialog(2);
        }
    }

    public void errorDialog(final int type) {
        String content = "";
        if (type == 1) {
            content = "【控制器】初始化失败，请重试";
        } else {
            content = "【系统参数】初始化失败，请重试";
        }
        errorDialog = new SweetAlertDialog(InitActivity.this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText("重新初始化")
                .setContentText(content)
                .setConfirmText("重试")
                .setCancelText("跳过")
                .setCustomImage(R.mipmap.card_icon)
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        if (type == 1) {
                            initEquInfo();
                        } else {
                            initSettingInfo();
                        }
                    }
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                        Intent intent = new Intent(InitActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        errorDialog.show();
        errorDialog.setCanceledOnTouchOutside(true);
    }

    @Override
    public void loadInitInfo(TerminalInfo terminalInfo) {
        isInitEquSuccess = true;
        App.ZDJID = terminalInfo.getZdjId();
        ToastUtils.showLong("初始化控制器信息成功");
        LogUtils.e("初始化控制器信息成功--->" + terminalInfo.getZdjId());
        SPUtils.getInstance().put(Constant.INIT_DEVICE, true);
        initDialog.dismissWithAnimation();
    }

    @Override
    public void loadSettingInfo(final SettingInfo settingInfo) {
        ToastUtils.showLong("系统参数初始化完成");
        initSettingDialog.dismiss();
        SPUtils.getInstance().put(Constant.INIT_SETTING_DATE, true);
        if (settingInfo != null) {
            App.QYID = settingInfo.getAreaId();
            App.ZDJID = settingInfo.getZdjId();
            new Thread(new Runnable() {//保存民警卡号
                @Override
                public void run() {
                    PoliceInfoAllHelper.deleteAllPoliceInfoAllList();
                    List<PoliceInfoAll> dlgj = settingInfo.getDlgj();
                    if (dlgj != null && dlgj.size()>0) {//保存民警卡号到本地
                        LogUtils.a("民警数量" + dlgj.size() + "卡号：" + dlgj.get(0).getDLGJ_KH());
                        PoliceInfoAllHelper.savePoliceInfoAllListToDB(dlgj);
                    }
                }
            }).start();
            //设备信息保存
            TerminalInfo terminalInfo = new TerminalInfo();
            terminalInfo.setZdjId(settingInfo.getZdjId());
            terminalInfo.setZdjName(settingInfo.getZdjName());
            terminalInfo.setSimCardNumber(settingInfo.getSimCardNumber());
            terminalInfo.setEquImel(settingInfo.getEquImel());
            terminalInfo.setAreaId(settingInfo.getAreaId());
            terminalInfo.setServerIp(settingInfo.getServerIp());
            //TODO 此处待定
            terminalInfo.setUseState(settingInfo.getUseState());
            terminalInfo.setRemark(settingInfo.getRemark());
            TerminalInfoHelper.saveTerminalInfoToDB(terminalInfo);
            //保存管理员卡号
            if (settingInfo.getAdmins() != null && settingInfo.getAdmins().size() > 0) {
                CardHelper.deleteAllCardInfoList();
                CardHelper.saveCardInfoListToDB(settingInfo.getAdmins());
            }
            //保存区域路线信息
            if (settingInfo.getRoutes() != null && settingInfo.getRoutes().size() > 0) {
                RouteHelper.saveRouteInfoListToDB(settingInfo.getRoutes());
            }
            //保存区域信息到数据库
            if (!StringUtils.isEmpty(App.ZDJID) && settingInfo.getAreas() != null && settingInfo.getAreas().size() > 0) {
                AreaInfo areaInfo = settingInfo.getAreas().get(0);
                areaInfo.setZdjId(App.ZDJID);
                AreaHelper.saveAreaInfoToDB(areaInfo);
            }
        }
        Intent intent = new Intent(InitActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.init_setting)
    public void onViewClicked() {
        Intent intent = new Intent(this, PasswordValidataActivity.class);
        startActivity(intent);
    }
}
