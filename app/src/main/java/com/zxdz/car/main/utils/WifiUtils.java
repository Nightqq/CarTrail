package com.zxdz.car.main.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;

public class WifiUtils {

    private static WifiUtils wifiUtils;
    private Context context;
    private WifiManager wifiManager;
    private WifiUtils.wifiBroadcast wifiBroadcast;

    private WifiUtils() {
        context = Utils.getContext();
        if (!isWifiEnabled(context)) {
            openWifi();
        }
        isWifiContected(context);
    }

    public static WifiUtils getWifiUtils() {
        if (wifiUtils == null) {
            wifiUtils = new WifiUtils();
        }
        return wifiUtils;
    }

    public void openBroadcast(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        wifiBroadcast = new wifiBroadcast();
        context.registerReceiver(wifiBroadcast,intentFilter);
    }

    private class wifiBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                //获取当前的wifi状态int类型数据
                int mWifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
                switch (mWifiState) {
                    case WifiManager.WIFI_STATE_ENABLED:
                        LogUtils.a("已打开");
                        //已打开
                        break;
                    case WifiManager.WIFI_STATE_ENABLING:
                        LogUtils.a("打开中");
                        //打开中
                        break;
                    case WifiManager.WIFI_STATE_DISABLED:
                        LogUtils.a("已关闭");
                        //已关闭
                        openWifi();
                        break;
                    case WifiManager.WIFI_STATE_DISABLING:
                        LogUtils.a("关闭中");
                        //关闭中
                        break;
                    case WifiManager.WIFI_STATE_UNKNOWN:
                        LogUtils.a("未知");
                        //未知
                        break;
                }
            }
        }
    }

    public void unRegistBroadcast(){
        context.unregisterReceiver(wifiBroadcast);
    }

    /**
     * 开启WIFI网络
     */
    private void openWifi() {
        if (wifiManager == null) {
            wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        }
        wifiManager.setWifiEnabled(true);
        Toast.makeText(context, "wifi已经打开,正在连接中", Toast.LENGTH_SHORT).show();
    }


    /**
     * 判断WIFI网络是否开启
     */
    private boolean isWifiEnabled(Context context) {
        WifiManager wm = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        if (wm != null && wm.isWifiEnabled()) {
            LogUtils.a("Wifi网络已经开启");
            return true;
        }
        LogUtils.a("Wifi网络还未开启");
        return false;
    }

    /**
     * 判断WIFI是否连接成功
     */
    private boolean isWifiContected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (info != null && info.isConnected()) {
            LogUtils.a("Wifi网络连接成功");
            return true;
        }
        LogUtils.a("Wifi网络连接失败");
        return false;
    }
}
