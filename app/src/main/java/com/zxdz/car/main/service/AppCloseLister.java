package com.zxdz.car.main.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.blankj.utilcode.util.LogUtils;
import com.zxdz.car.App;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AppCloseLister extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            handler_listen.sendMessage(message);
        }
    };
    private ActivityManager am = null;

    @Override
    public void onCreate() {
        timer.schedule(task, 0, 1000);
        super.onCreate();
    }

    Handler handler_listen = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                am = (ActivityManager) AppCloseLister.this.getSystemService(Context.ACTIVITY_SERVICE);
                List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = am.getRunningAppProcesses();
                for (ActivityManager.RunningAppProcessInfo runningAppProcess : runningAppProcesses) {
                    if (runningAppProcess.processName.equals(App.packageName)) {
                        //LogUtils.a("找到自己return");
                        return;
                    }

                }
                PackageManager packageManager = AppCloseLister.this.getPackageManager();
                Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage("com.zxdz.car");
                if (launchIntentForPackage != null) {
                    LogUtils.a("自启动开始");
                    AppCloseLister.this.startActivity(launchIntentForPackage);
                }else {
                    LogUtils.a("launchIntentForPackage空");
                }
            }
            super.handleMessage(msg);
        }

        ;
    };

    public static void startoneself(Context context) {
        Intent intent = new Intent(context, AppCloseLister.class);
        if (context!=null){
            LogUtils.a("准备自启动");
        }else {
            LogUtils.a("context空");
        }
        context.startService(intent);
    }

    @Override
    public void onDestroy() {
        LogUtils.a("onDestroy");
        if (timer != null) {
            timer.cancel();
        }
        super.onDestroy();
    }
}
