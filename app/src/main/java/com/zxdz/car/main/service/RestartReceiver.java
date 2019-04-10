package com.zxdz.car.main.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.zxdz.car.R;
import com.zxdz.car.base.utils.SwitchUtils;
import com.zxdz.car.main.view.MainActivity;

import java.io.IOException;
import java.lang.reflect.Method;

public class RestartReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.a("开始重启");
        Intent intent1 = new Intent(context.getApplicationContext(), MainActivity.class);
        intent1.putExtra(MainActivity.TAG_EXIT, MainActivity.TAG_RESTART);
        context.getApplicationContext().startActivity(intent1);
    }
}
