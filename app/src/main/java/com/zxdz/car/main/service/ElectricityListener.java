package com.zxdz.car.main.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import com.blankj.subutil.util.Utils;
import com.zxdz.car.App;
import com.zxdz.car.base.helper.CarTravelHelper;
import com.zxdz.car.base.utils.AudioPlayUtils;
import com.zxdz.car.main.utils.BlueToothHelper;
import com.zxdz.car.main.view.MainActivity;

import java.util.Date;

public class ElectricityListener {

    private static Context mContext;
    private static ElectricityListener electricityListener;
    private ElectricityBroadcast electricityBroadcast;

    private ElectricityListener() {

    }

    public static ElectricityListener getElectricityListener(Context context) {
        mContext = context;
        if (electricityListener == null) {
            electricityListener = new ElectricityListener();
        }
        return electricityListener;
    }



    public void openElectricityListener() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        intentFilter.addAction(Intent.ACTION_BATTERY_LOW);
        intentFilter.addAction(Intent.ACTION_BATTERY_OKAY);
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        electricityBroadcast = new ElectricityBroadcast();
        mContext.registerReceiver(electricityBroadcast, intentFilter);
    }

    private class ElectricityBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                switch (intent.getAction()) {
                    case Intent.ACTION_POWER_CONNECTED://接通电源
                        Toast.makeText(context, "接通电源", Toast.LENGTH_SHORT).show();
                        closePolice();
                        if ( CarTravelHelper.carTravelRecord!=null){
                            CarTravelHelper.carTravelRecord.setGLY_GHQRSJ(new Date());
                            CarTravelHelper.saveCarTravelRecordToDB(CarTravelHelper.carTravelRecord);
                        }
                        BlueToothHelper.getBlueHelp().closeAll();
                        Intent mintent = new Intent(com.blankj.utilcode.util.Utils.getContext(), MainActivity.class);
                        intent.putExtra("end", 1);
                        mContext.startActivity(mintent);
                        break;
                    case Intent.ACTION_POWER_DISCONNECTED://拔出电源
                        Toast.makeText(context, "拔出电源", Toast.LENGTH_SHORT).show();
                        break;
                    case Intent.ACTION_BATTERY_LOW://电量低
                        break;
                    case Intent.ACTION_BATTERY_OKAY://电量充满
                        break;
                }
            }
        }
    }

    private void closePolice() {
        AudioPlayUtils.getAudio(mContext,0).stop();
    }
}
