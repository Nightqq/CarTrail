package com.zxdz.car.main.view.setting;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.suke.widget.SwitchButton;
import com.zxdz.car.R;
import com.zxdz.car.base.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartSettingActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    @BindView(R.id.start_set_1)
    SwitchButton startSet1;
    @BindView(R.id.start_set_2)
    SwitchButton startSet2;
    @BindView(R.id.start_set_3)
    SwitchButton startSet3;

    @Override
    public void init() {
        ButterKnife.bind(this);
        mToolBar.setTitle("自启动设定");
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationIcon(R.mipmap.back_icon);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        startSet1.setChecked(true);
        startSet2.setChecked(true);
        startSet3.setChecked(true);
        startSet1.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

            }
        });
        startSet2.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

            }
        });
        startSet3.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {

            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_start_setting;
    }
}
