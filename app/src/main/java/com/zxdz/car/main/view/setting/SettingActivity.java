package com.zxdz.car.main.view.setting;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.alibaba.fastjson.TypeReference;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.engin.HttpCoreEngin;
import com.zxdz.car.App;
import com.zxdz.car.R;
import com.zxdz.car.base.helper.ServerIPHelper;
import com.zxdz.car.base.view.BaseActivity;
import com.zxdz.car.main.bean.PingNetEntity;
import com.zxdz.car.main.model.domain.Constant;
import com.zxdz.car.main.model.domain.ServerIP;
import com.zxdz.car.main.model.domain.SettingInfo;
import com.zxdz.car.main.model.domain.URLConfig;
import com.zxdz.car.main.service.UploadDataService;
import com.zxdz.car.main.utils.NetWorkUtils;
import com.zxdz.car.main.view.InitActivity;
import com.zxdz.car.main.view.MainActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

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
    @OnClick({R.id.layout_open_lock,R.id.Setting_init_again,R.id.exit,R.id.setting_information,R.id.setting_admin, R.id.setting_area, R.id.setting_swipe_card, R.id.setting_server_ip, R.id.setting_change, R.id.layout_gps_upload_interval,R.id.layout_networt_status})
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
            case R.id.layout_open_lock:
               
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
                            Toast.makeText(this, "ping "+server_IP.getIp()+"连接成功", Toast.LENGTH_SHORT).show();
                            //重新初始化，测试服务器是否正常连接
                            initInfo();
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
            case R.id.Setting_init_again:
                SPUtils.getInstance().put(Constant.INIT_DEVICE, false);
                startActivity(new Intent(this, InitActivity.class));
                break;
            case R.id.exit:
                Intent intent1 = new Intent(this,MainActivity.class);
                intent1.putExtra(MainActivity.TAG_EXIT, MainActivity.TAG_EXIT);
                startActivity(intent1);
                break;
            default:
                break;
        }
    }

    private void initInfo() {
        checkOpenLock("连接服务器中");
        final int[] i = {1};
        if (App.ZDJID==null){
            Toast.makeText(this, "设备未注册", Toast.LENGTH_SHORT).show();
            if (initDialog!=null){
                initDialog.dismiss();
            }
            return;
        }
        Subscription subscribe = loadSettingInfo(App.ZDJID).subscribe(new Subscriber<ResultInfo<SettingInfo>>() {
            @Override
            public void onCompleted() {
                initDialog.dismiss();
                if (i[0]==0){
                    ToastUtils.showShort("连接服务器成功");
                }else {
                    ToastUtils.showShort("连接服务器失败");
                }

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(final ResultInfo<SettingInfo> resultInfo) {
                if (resultInfo!=null){
                    i[0] =0;
                }
            }
        });


    }

    public Observable<ResultInfo<SettingInfo>> loadSettingInfo(String zdjId) {
        Map<String, String> params = new HashMap<>();
        //TODO,值暂时写固定，后面修改
        params.put("ZDJID", zdjId);
        return HttpCoreEngin.get(this).rxpost(URLConfig.getinstance().getEQU_INFO_URL(), new TypeReference<ResultInfo<SettingInfo>>() {
                }.getType(), params,
                false, false,
                false);
    }

    public void checkOpenLock(String msg) {
        try {
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
        } } catch (Exception e) {
            LogUtils.a("" + e.getMessage().toString());
        }

    }



    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

}
