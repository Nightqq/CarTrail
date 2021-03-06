package com.zxdz.car.main.model.domain;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.blankj.utilcode.util.Utils;
import com.zxdz.car.App;
import com.zxdz.car.base.helper.ServerIPHelper;
import com.zxdz.car.base.model.Config;
import com.zxdz.car.base.view.BaseActivity;

import java.util.List;

/**
 * Created by zhangkai on 2017/8/1.
 */

public class URLConfig {
    public static final boolean DEBUG = Config.DEBUG;
    private static String debugBaseUrl = "www.baidu.com";
    private static URLConfig urlConfig;

    private String baseUrl() {
        List<ServerIP> areaInfoListFromDB = ServerIPHelper.getAreaInfoListFromDB();
        if (areaInfoListFromDB != null && areaInfoListFromDB.size() > 0) {
            ServerIP server_IP = areaInfoListFromDB.get(0);
            if (App.ipchange == 1) {
                if (server_IP.getDk() != null && !server_IP.getDk().equals("")) {
                    return "http://" + server_IP.getIp() + ":" + server_IP.getDk() + "/AreaMonitor/services/handler.ashx?";
                } else {
                    return "http://" + server_IP.getIp() + "/AreaMonitor/services/handler.ashx?";
                }
            } else if (App.ipchange == 2) {
                if (server_IP.getDk_2() != null && !server_IP.getDk_2().equals("")) {
                    return "http://" + server_IP.getIp_2() + ":" + server_IP.getDk_2() + "/AreaMonitor/services/handler.ashx?";
                } else {
                    return "http://" + server_IP.getIp_2() + "/AreaMonitor/services/handler.ashx?";
                }
            }
        }
        return "http://192.168.0.10/AreaMonitor/services/handler.ashx?";
    }

    public static URLConfig getinstance() {
        if (urlConfig == null) {
            urlConfig = new URLConfig();
        }
        return urlConfig;
    }

    public String getCARD_INFO_URL() {
        return baseUrl();
    }

    //初始化设备信息
    public String getINIT_EQU_URL() {
        return baseUrl() + "action=savezdjxx";
    }

    //获取设备的系统设置信息
    public String getEQU_INFO_URL() {
        return baseUrl() + "action=getzdglry";
    }

    //获取设备的系统设置信息
    public String getunload_area_URL() {
        return baseUrl() + "action=getunloadarea";
    }

    //修改设定值
    public String getUPDATE_CARD_STATE_URL() {
        return baseUrl() + "action=updateqyxx";
    }

    //车辆主信息更新
    public String getUPDATE_CAR_RECORD_URL() {
        return baseUrl() + "action=uploadcljcls";
    }

    //锁照片
    public String getUPDATE_TAKE_PICTURE_URL() {
        return baseUrl() + "action=takepicture";
    }

    //轨迹
    public String getUPDATE_TRAIL_POINT_URL() {
        return baseUrl() + "action=uploadcljclsgj";
    }

    //报警
    public String getUPDATE_WATN_INFO_URL() {
        return baseUrl() + "action=uploadcljclsbj";
    }

    //取消报警
    public String getUPDATE_UNWATN_INFO_URL() {
        return baseUrl() + "action=canelbj&BJ_ID=";
    }

    //请求远程开锁
    public String getsend_unLOCK_INFO_URL(){
        return baseUrl() + "action=sendunlock";
    }
    //取消远程开锁
    public String getcancel_send_unLOCK_INFO_URL(){
        return baseUrl() + "action=cancelunlock";
    }

    public String getUPDATE_CHANGE_INFO_URL() {
        return baseUrl() + "";
    }

    //根据卡号获取民警信息
    public String getGET_PERSION_INFO() {
        return baseUrl() + "action=getinfobykh";
    }
    //根据卡号获取民警信息
    public String getGET_Driver_INFO() {
        return baseUrl() + "action=getDriverInfoByCard";
    }
}
