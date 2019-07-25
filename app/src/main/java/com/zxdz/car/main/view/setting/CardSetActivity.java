package com.zxdz.car.main.view.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.kk.securityhttp.domain.ResultInfo;
import com.suke.widget.SwitchButton;
import com.zxdz.car.App;
import com.zxdz.car.R;
import com.zxdz.car.base.utils.SwipingCardUtils;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.contract.CardSetContract;
import com.zxdz.car.main.model.domain.Constant;
import com.zxdz.car.main.presenter.CardSetPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by iflying on 2017/11/10.
 * 刷卡设定
 */

public class CardSetActivity extends BaseActivity<CardSetPresenter> implements CardSetContract.View {

    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.rb_id)
    RadioButton rbId;
    @BindView(R.id.rb_ic)
    RadioButton rbIc;
    @BindView(R.id.carin_admin)
    SwitchButton carinAdmin;
    @BindView(R.id.carin_police)
    SwitchButton carinPolice;
    @BindView(R.id.carin_driver)
    SwitchButton carinDriver;
    @BindView(R.id.equipinstall_admin)
    SwitchButton equipinstallAdmin;
    @BindView(R.id.equipinstall_police)
    SwitchButton equipinstallPolice;
    @BindView(R.id.equipinstall_driver)
    SwitchButton equipinstallDriver;
    @BindView(R.id.lockcar_admin)
    SwitchButton lockcarAdmin;
    @BindView(R.id.lockcar_police)
    SwitchButton lockcarPolice;
    @BindView(R.id.lockcar_driver)
    SwitchButton lockcarDriver;
    @BindView(R.id.openlock_admin)
    SwitchButton openlockAdmin;
    @BindView(R.id.openlock_police)
    SwitchButton openlockPolice;
    @BindView(R.id.openlock_driver)
    SwitchButton openlockDriver;
    @BindView(R.id.returnequip_admin)
    SwitchButton returnequipAdmin;
    @BindView(R.id.returnequip_police)
    SwitchButton returnequipPolice;
    @BindView(R.id.returnequip_driver)
    SwitchButton returnequipDriver;
    @BindView(R.id.isretention)
    SwitchButton Misretention;
    @BindView(R.id.test)
    SwitchButton test;


    private int currentType = 0;//当前操作的参数
    private SwitchButton[][] sb;
    private SharedPreferences sp;
    private boolean isretention;
    private boolean isclickone;
    private boolean istest;

    @Override
    public int getLayoutId() {
        return R.layout.activity_card_set;
    }

    @Override
    public void init() {
        mToolBar.setTitle("刷卡设定");
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationIcon(R.mipmap.back_icon);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mPresenter = new CardSetPresenter(this, this);
        sb = new SwitchButton[][]{{carinAdmin, carinPolice, carinDriver}, {equipinstallAdmin, equipinstallPolice, equipinstallDriver}, {lockcarAdmin, lockcarPolice, lockcarDriver}, {openlockAdmin, openlockPolice, openlockDriver}, {returnequipAdmin, returnequipPolice, returnequipDriver}};
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                final int finalI = i;
                final int finalJ = j;
                sb[i][j].setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                        SwipingCardUtils.swipecardhelp(getApplicationContext()).setArray(finalI, finalJ);
                    }
                });
            }
        }


        setPunchInState();
        sp = getSharedPreferences("spUtils", Context.MODE_PRIVATE);
        isretention = sp.getBoolean("isretention", false);
        Misretention.setChecked(isretention);
        istest = sp.getBoolean("test", false);
        test.setChecked(isretention);
        test.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("test", !istest);
                edit.commit();
            }
        });
        Misretention.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("isretention", !isretention);
                edit.commit();
            }
        });


    }

    /**
     * 设置默认的刷卡状态
     */
    public void setPunchInState() {
        int[][] allArray = SwipingCardUtils.swipecardhelp(this).getAllArray();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 3; j++) {
                if (allArray[i][j] == 1) {
                    sb[i][j].setChecked(true);
                } else {
                    sb[i][j].setChecked(false);
                }
            }
        }
        if (App.readCardType == Constant.TYPE_ID) {
            rbId.setChecked(true);
        } else if (App.readCardType == Constant.TYPE_IC) {
            rbIc.setChecked(true);
        }
    }

    //上传网络
    public void updateCardSetState(int type, int upValue, int areaId) {
        mPresenter.updateCardSet(type, upValue, areaId);
    }

    @Override
    public void showUpdateCardState(ResultInfo resultInfo) {
        ToastUtils.showLong("设定成功");
        LogUtils.e("update state --->" + currentType);
    }

    @Override
    public void hideStateView() {
    }

    @Override
    public void showNoNet() {
        ToastUtils.showLong("设定刷卡失败");
    }


    @OnClick({R.id.rb_ic, R.id.rb_id})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rb_id:
                App.isRFID = true;
                App.readCardType = Constant.TYPE_ID;
                SPUtils.getInstance().put(Constant.READ_CARD_TYPE, Constant.TYPE_ID);
                break;
            case R.id.rb_ic:
                App.isRFID = false;
                App.readCardType = Constant.TYPE_IC;
                SPUtils.getInstance().put(Constant.READ_CARD_TYPE, Constant.TYPE_IC);
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
