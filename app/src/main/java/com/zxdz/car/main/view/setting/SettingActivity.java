package com.zxdz.car.main.view.setting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.zxdz.car.R;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.base.view.PasswardView;
import com.zxdz.car.main.service.UploadDataService;
import com.zxdz.car.main.utils.UploadInfoUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolBar;


    @Override
    public int getLayoutId() {
        return R.layout.activity_setting;
    }

    @Override
    public void init() {
        ButterKnife.bind(this);
        mToolBar.setTitle("系统设置");
        setSupportActionBar(mToolBar);
        mToolBar.setNavigationIcon(R.mipmap.back_icon);
        mToolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static int i=0;
    @OnClick({R.id.setting_admin, R.id.setting_area, R.id.setting_swipe_card, R.id.setting_server_ip, R.id.setting_change, R.id.layout_gps_upload_interval})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.setting_admin:
                i=1;
                Intent intent = new Intent(this, UploadDataService.class);
                startService(intent);
                //startact(this, AdminCardActivity.class);
                break;
            case R.id.setting_area:
                startact(this, AreaLocusActivity.class);
                break;
            case R.id.setting_swipe_card:
                startact(this, CardSetActivity.class);
                break;
            case R.id.setting_server_ip:
                startact(this, ServerIPActivity.class);
                break;
            case R.id.setting_change:
                startact(this, ChangeActivity.class);
                break;
            case R.id.layout_gps_upload_interval:
                //TODO 待完成
                //默认5秒
                break;
            default:
                break;
        }
    }

}
