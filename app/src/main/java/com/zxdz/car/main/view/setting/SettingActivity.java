package com.zxdz.car.main.view.setting;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.zxdz.car.R;
import com.zxdz.car.base.helper.ServerIPHelper;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.bean.PingNetEntity;
import com.zxdz.car.main.model.domain.ServerIP;
import com.zxdz.car.main.service.UploadDataService;
import com.zxdz.car.main.utils.NetWorkUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class SettingActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolBar;
    private SweetAlertDialog initDialog;


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
    @OnClick({R.id.setting_information,R.id.setting_admin, R.id.setting_area, R.id.setting_swipe_card, R.id.setting_server_ip, R.id.setting_change, R.id.layout_gps_upload_interval,R.id.layout_networt_status})
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
                startact(this, StartSettingActivity.class);
                break;
            case R.id.layout_gps_upload_interval:
                //TODO 待完成
                //默认5秒
                break;

            case R.id.setting_information:
                startact(this, InformationActivity.class);
                break;
            case R.id.layout_networt_status:
                List<ServerIP> areaInfoListFromDB = ServerIPHelper.getAreaInfoListFromDB();
                if (areaInfoListFromDB!=null&&areaInfoListFromDB.size()>0){
                    ServerIP server_IP = areaInfoListFromDB.get(0);
                    if (server_IP.getIp()!=null){
                        checkOpenLock("网络连接中");
                        PingNetEntity pingNetEntity=new PingNetEntity(server_IP.getIp(),2,5,new StringBuffer());
                        pingNetEntity= NetWorkUtils.ping(pingNetEntity);
                        if (initDialog!=null){
                            initDialog.dismiss();
                        }
                        if (pingNetEntity.isResult()){
                            Toast.makeText(this, "ping "+server_IP.getIp()+"成功", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(this, "服务器连接失败", Toast.LENGTH_SHORT).show();
                        }
                        Log.i("testPing",pingNetEntity.getIp());
                        Log.i("testPing","time="+pingNetEntity.getPingTime());
                        Log.i("testPing",pingNetEntity.isResult()+"");
                    }else {
                        Toast.makeText(this, "服务器ip没有设置", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "服务器ip没有设置", Toast.LENGTH_SHORT).show();
                }

                break;
            default:
                break;
        }
    }

    public void checkOpenLock(String msg) {
        if (null == initDialog) {
            initDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
            initDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            initDialog.setCancelable(false);
            initDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            initDialog.setCanceledOnTouchOutside(true);
        }
        if (!initDialog.isShowing()) {
            initDialog.setTitleText(msg);
            initDialog.show();
        }
    }



    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
