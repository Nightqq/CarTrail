package com.zxdz.car.base.helper;

import com.zxdz.car.base.dao.ServerIPDao;
import com.zxdz.car.main.model.domain.ServerIP;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2017/10/31.
 */

public class ServerIPHelper {

    private static ServerIPDao serverIPDao = CardDBUtil.getDaoSession().getServerIPDao();
    public static List<ServerIP> getAreaInfoListFromDB() {
        return (ArrayList<ServerIP>) serverIPDao.queryBuilder().list();
    }
    public static void saveAreaInfoToDB(ServerIP serverIP) {
        if (serverIP != null) {
            serverIPDao.insertOrReplace(serverIP);
        }
    }
}
