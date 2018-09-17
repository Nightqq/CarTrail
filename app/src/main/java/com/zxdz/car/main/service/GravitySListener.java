package com.zxdz.car.main.service;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;

/**
 * Created by Administrator on 2018\5\10 0010.
 */

public class GravitySListener implements SensorEventListener {
    private long mTime;
    public GravitySListener(CallBack callBack) {
        this.callBack = callBack;
    }
    //可以得到传感器实时测量出来的变化值
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (System.currentTimeMillis() - mTime>2000){
            mTime=System.currentTimeMillis();
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                float x = sensorEvent.values[SensorManager.DATA_X];
                float y = sensorEvent.values[SensorManager.DATA_Y];
                float z = sensorEvent.values[SensorManager.DATA_Z];
                callBack.doSomeThing(x,y,z);
            }
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private CallBack callBack;
    public interface CallBack {
        void doSomeThing(float x,float y,float z);
    }
}
