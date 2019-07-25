package com.zxdz.car.main.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.zxdz.car.App;
import com.zxdz.car.base.helper.ServerIPHelper;
import com.zxdz.car.main.model.domain.ServerIP;
import com.zxdz.car.main.view.lock.UnloadAreaActivity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class WifiUtils {

    private static WifiUtils wifiUtils;
    private Context context;
    private WifiManager wifiManager;
    private WifiUtils.wifiBroadcast wifiBroadcast;
    private List<Integer> list;

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

    public void openBroadcast() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        intentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);//连上与否
        wifiBroadcast = new wifiBroadcast();
        context.registerReceiver(wifiBroadcast, intentFilter);
    }

    private class wifiBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                if (wifiManager == null) {
                    wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
                } else {
                    if (wifiManager.reconnect()) {
                        String ssid = wifiManager.getConnectionInfo().getSSID();
                        IsOtherWifi(ssid);
                    }
                }
                getNetwordIdList();
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
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo mWifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (mWifiInfo.isConnected()) {
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    int ipAddress = wifiInfo.getIpAddress();
                    App.phoneIP = intToIp(ipAddress);
                    String ssid = wifiManager.getConnectionInfo().getSSID();
                    IsOtherWifi(ssid);
                    List<ServerIP> areaInfoListFromDB = ServerIPHelper.getAreaInfoListFromDB();
                    if (areaInfoListFromDB != null && areaInfoListFromDB.size() > 0) {
                        ServerIP server_IP = areaInfoListFromDB.get(0);
                        String ip = "\"" + server_IP.getWifi_name() + "\"";
                        String ip2 = "\"" + server_IP.getWifi_name_2() + "\"";
                        if (server_IP.getWifi_name() != null && ip.equals(ssid)) {
                            App.ipchange = 1;
                            Toast.makeText(context, "使用ip1：" + server_IP.getIp(), Toast.LENGTH_SHORT).show();
                        } else if (server_IP.getWifi_name_2() != null && ip2.equals(ssid)) {
                            App.ipchange = 2;
                            Toast.makeText(context, "使用ip2：" + server_IP.getIp_2(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
//            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
//                ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//                NetworkInfo mWifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//                if (mWifiInfo.isConnected()) {
//                    String ssid = wifiManager.getConnectionInfo().getSSID();
//                    Toast.makeText(context, "wifi名称2："+ssid , Toast.LENGTH_SHORT).show();
//                    List<ServerIP> areaInfoListFromDB = ServerIPHelper.getAreaInfoListFromDB();
//                    if (areaInfoListFromDB != null && areaInfoListFromDB.size() > 0) {
//                        ServerIP server_IP = areaInfoListFromDB.get(0);
//                        if (server_IP.getWifi_name() != null && server_IP.getWifi_name().equals(ssid)) {
//                            App.ipchange = 1;
//                            Toast.makeText(context, "使用ip1：" + server_IP.getIp(), Toast.LENGTH_SHORT).show();
//                        } else if (server_IP.getWifi_name_2() != null && server_IP.getWifi_name_2().equals(ssid)) {
//                            App.ipchange = 2;
//                            Toast.makeText(context, "使用ip2：" + server_IP.getIp_2(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }
//            }
        }
    }

    private void IsOtherWifi(String ssid) {
        if (ssid.equals("<unknown ssid>")||ssid.equals("\"805\"")) {
            return;
        } else {
            Toast.makeText(context, "wifi名称：" + ssid, Toast.LENGTH_SHORT).show();
        }
        if (ssid.length() == "\"njzx_clgk_000\"".length()) {
            String substring = ssid.substring(1, 10);
            if (substring.equals("njzx_clgk")) {
                LogUtils.a("连接wifi合法", ssid);
                return;
            }
        }
        Toast.makeText(context, "连接wifi非法，断开中", Toast.LENGTH_SHORT).show();
        boolean disconnect = wifiManager.disconnect();
        LogUtils.a("断开wifi", ssid + disconnect);
        removeWifiBySsid(wifiManager, ssid);//扫描并连接我们的wifi
    }

    public int i=0;
    public void removeWifiBySsid(WifiManager wifiManager, String targetSsid) {
       if (list!=null&&list.size()>0){
           wifiManager.enableNetwork((Integer) list.get(i), true);
           i++;
           if (i>=list.size()){
               i=0;
           }
       }
    }


    public void getNetwordIdList(){
        if (list==null){
            list = new ArrayList<Integer>();
        }
        list.clear();
        List<WifiConfiguration> wifiConfigs = wifiManager.getConfiguredNetworks();
        if (wifiConfigs != null && wifiConfigs.size() > 0) {
            for (WifiConfiguration wifiConfig : wifiConfigs) {
                String ssid = wifiConfig.SSID;
                if (ssid.length() == "\"njzx_clgk_000\"".length() && ssid.substring(1, 10).equals("njzx_clgk")) {
                    list.add(wifiConfig.networkId);
                    LogUtils.a("添加集合",wifiConfig.SSID);
                    continue;
                }
            }
        }
    }

    private String intToIp(int i) {
        return (i & 0xFF) + "." +
                ((i >> 8) & 0xFF) + "." +
                ((i >> 16) & 0xFF) + "." +
                (i >> 24 & 0xFF);
    }

    public void unRegistBroadcast() {
        try {
            context.unregisterReceiver(wifiBroadcast);
        } catch (IllegalArgumentException e) {
            LogUtils.a(e.getMessage());
        }


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

    public void closeWifi() {
        if (wifiManager == null) {
            wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        }
        wifiManager.setWifiEnabled(false);
        Toast.makeText(context, "wifi关闭中", Toast.LENGTH_SHORT).show();
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
