package com.zxdz.car.main.view.setting;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
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
    private SharedPreferences sp;

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

        sp = getApplicationContext().getSharedPreferences("qq", Context.MODE_PRIVATE);
        boolean startself = sp.getBoolean("startself", true);
        boolean start_set_2 = sp.getBoolean("start_set_2", true);
        LogUtils.a("qweqq获取", start_set_2);
        startSet1.setChecked(startself);
        startSet2.setChecked(start_set_2);
        startSet3.setChecked(true);
        startSet1.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("startself",isChecked);
                edit.commit();
            }
        });
        startSet2.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                LogUtils.a("qweqq设置", isChecked);

                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("start_set_2", isChecked);
                edit.commit();
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
