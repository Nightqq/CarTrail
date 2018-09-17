package com.zxdz.car.base.helper;

import com.zxdz.car.base.dao.RouteInfoDao;
import com.zxdz.car.main.model.domain.RouteInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2017/10/30.
 */

public class RouteHelper {

    private static RouteInfoDao routeInfoDao = CardDBUtil.getDaoSession().getRouteInfoDao();

    public static List<RouteInfo> getRouteInfoListFromDB() {
        return routeInfoDao.queryBuilder().list();
    }

    public static void saveRouteInfoToDB(RouteInfo RouteInfo) {
        if (RouteInfo != null) {
            routeInfoDao.insertOrReplace(RouteInfo);
        }
    }

    public static void saveRouteInfoListToDB(List<RouteInfo> list) {
        if (list != null) {
            for (RouteInfo info : list) {
                routeInfoDao.insertOrReplace(info);
            }
        }
    }

    public static void deleteRouteInfoInDB(RouteInfo RouteInfo) {
        routeInfoDao.delete(RouteInfo);
    }

    public static void deleteAllRouteInfoList() {
        routeInfoDao.deleteAll();
    }
}
