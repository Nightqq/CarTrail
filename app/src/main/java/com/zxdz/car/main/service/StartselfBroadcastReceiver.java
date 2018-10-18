package com.zxdz.car.main.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import com.blankj.utilcode.util.ToastUtils;

/**
 * Created by Administrator on 2018/6/12.
 */

public class StartselfBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences qq = context.getSharedPreferences("qq",Context.MODE_PRIVATE);
        boolean startself = qq.getBoolean("startself", true);
        if (startself) {
            ToastUtils.showShort("开始启动程序。。。");
            PackageManager pm = context.getPackageManager();    //包管理者
            //意图
            Intent it = pm.getLaunchIntentForPackage("com.zxdz.car");   //值为应用的包名
            if (null != it)
            {
                context.startActivity(it);         //启动意图
            }
        }
    }
}
