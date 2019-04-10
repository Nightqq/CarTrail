package com.zxdz.car.main.service;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.app.Service;
import android.os.SystemClock;

import com.blankj.utilcode.util.LogUtils;
import com.zxdz.car.base.utils.SwitchUtils;

public class RemindService extends Service {
    int count = 0;
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.a("定时服务开始");
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Long secondsNextEarlyMorning = SwitchUtils.getSecondsNextEarlyMorning(21,0,0);
        Intent i = new Intent(getApplicationContext(), RestartReceiver.class);
        i.putExtra("flag",21);
        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), count++, i, PendingIntent.FLAG_UPDATE_CURRENT);
        manager.set(AlarmManager.RTC_WAKEUP, secondsNextEarlyMorning, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
