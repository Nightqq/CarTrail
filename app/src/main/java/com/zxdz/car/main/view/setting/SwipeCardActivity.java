package com.zxdz.car.main.view.setting;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.RadioButton;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.zxdz.car.App;
import com.zxdz.car.R;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.model.domain.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SwipeCardActivity extends BaseActivity {


    @BindView(R.id.swipecard_toolbar)
    Toolbar swipecardToolbar;
    @BindView(R.id.swipecard_id)
    RadioButton swipecardId;
    @BindView(R.id.swipecard_ic)
    RadioButton swipecardIc;

    @Override
    public void init() {
        ButterKnife.bind(this);
        basetoobar(swipecardToolbar, R.string.swipe_setting);
        Log.e("读卡",App.readCardType+"");
        if (App.readCardType==Constant.TYPE_ID){
            swipecardId.setChecked(true);
        }else if (App.readCardType==Constant.TYPE_IC){
            swipecardIc.setChecked(true);
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_swipe_card;
    }
    @OnClick({R.id.swipecard_id, R.id.swipecard_ic})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.swipecard_id:
                App.isRFID = true;
                App.readCardType = Constant.TYPE_ID;
                SPUtils.getInstance().put(Constant.READ_CARD_TYPE, Constant.TYPE_ID);
                break;
            case R.id.swipecard_ic:
                App.isRFID = false;
                Log.e("设置读卡1",App.readCardType+"");
                App.readCardType = Constant.TYPE_IC;
                SPUtils.getInstance().put(Constant.READ_CARD_TYPE, Constant.TYPE_IC);
                Log.e("设置读卡2",App.readCardType+"");
                break;
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

}
