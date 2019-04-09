package com.zxdz.car;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.zxdz.car.base.helper.CardDBUtil;
import com.zxdz.car.base.utils.LocationService;
import com.zxdz.car.main.service.AppCloseLister;
import com.zxdz.car.main.service.ElectricityListener;
import com.zxdz.car.main.utils.WifiUtils;

/**
 * Created by super on 2017/10/21.
 */

public class App extends Application {

    /**
     * 是否使用带RFID的设备测试
     */

    public static boolean isRFID = false;

    public static int readCardType = 1;//读卡类型1:ID卡,2:IC卡

    public LocationService locationService;

    public static String ZDJID;//此处的值现在为设备标识

    public static String phoneIP;

    public static int QYID;//所属区域

    public static int ADMIN_SWIPE;//管理员是否刷卡

    public static int POLICE_SWIPE;//带领干警是否刷卡

    public static int DRIVER_SWIPE;//驾驶员是否刷卡

    public static Long LSID = 0L;//流水ID

    public static boolean IsNewLS = false;

    public static int Lock_car_num=1;

    public static int ipchange=1;//ip更改标志1为默认，2虚拟ip

    /**
     * 根据所刷卡的步骤，来决定上传那些数据
     * 1：带领干警领用，上传主信息
     * 2：驾驶员刷卡，上传主信息
     * 3：带领干警安装，上传主信息
     * 4：带领干警锁车，上传主信息，上传进入轨迹(车辆安装后到锁车这中间的轨迹路线)
     * 4.1tp
     * 5：带领干警开锁，上传主信息，上传报警记录(中间如果有非法移动及警报信息，则上传)
     * 6：带领干警交还，上传主信息，上传出门的轨迹路线(开锁到大门这中间的轨迹路线),上传整个流程中的更换带领干警信息记录
     * 7：管理员确认归还
     */
    public static int UPLOAD_STEP = 1;

    /**
     * 刷卡的步骤
     * 1:管理员
     * 2：带领干警领用
     * 3：驾驶员刷卡
     * 4：带领干警安装
     * 5：带领干警锁车
     * 6：带领干警开锁
     * 7：带领干警交还
     */
    public static int SWIPE_STEP = 1;


    /**
     * 移动报警
     * 0：关闭报警
     * 1：开启报警
     */
    public static int GravityListener_type = 0;
    public static String packageName;
    /**
     * 报警
     * 0：非报警中
     * 1：报警中
     */
    public static int baojing_type=0;

    @Override // 程序创建的时候执行
    public void onCreate() {
        LogUtils.a("onCreate");
        super.onCreate();
        Utils.init(App.this);
        CardDBUtil.init(App.this);
        locationService = new LocationService(getApplicationContext());
        WifiUtils.getWifiUtils().openBroadcast();//wifi关闭自动开启
        ElectricityListener.getElectricityListener(getApplicationContext()).openElectricityListener();//充电广播
    }

    @Override// 程序终止的时候执行
    public void onTerminate() {
        LogUtils.a("onTerminate");
        // WifiUtils.getWifiUtils().unRegistBroadcast();
        super.onTerminate();
    }

    @Override // 程序在内存清理的时候执行
    public void onTrimMemory(int level) {
        packageName = getPackageName();
        SharedPreferences qq = getApplicationContext().getSharedPreferences("qq", Context.MODE_PRIVATE);
        boolean startself = qq.getBoolean("start_set_2", false);
        if (startself) {
            AppCloseLister.startoneself(Utils.getContext());
        }
        LogUtils.a("onTrimMemory");
        super.onTrimMemory(level);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        LogUtils.a("onConfigurationChanged");
        super.onConfigurationChanged(newConfig);
    }

}
