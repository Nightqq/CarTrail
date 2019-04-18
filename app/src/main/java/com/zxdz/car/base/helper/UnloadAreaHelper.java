package com.zxdz.car.base.helper;

import com.zxdz.car.base.dao.UnloadAreaInfoDao;
import com.zxdz.car.main.model.domain.UnloadAreaInfo;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

public class UnloadAreaHelper {
    private static UnloadAreaInfoDao unloadAreaInfoDao = CardDBUtil.getDaoSession().getUnloadAreaInfoDao();


    public static List<UnloadAreaInfo> getWarnInfoListFromDB() {
        return unloadAreaInfoDao.queryBuilder().list();
    }

    public static UnloadAreaInfo getWarnInfoListByID(String name) {
        QueryBuilder queryBuilder = unloadAreaInfoDao.queryBuilder();
        queryBuilder.where(UnloadAreaInfoDao.Properties.Area_name.eq(name));
        return (UnloadAreaInfo)queryBuilder.list().get(0);
    }


    public static void saveWarnInfoListToDB(List<UnloadAreaInfo> list) {
        if (list != null) {
            for (UnloadAreaInfo info : list) {
                unloadAreaInfoDao.insertOrReplace(info);
            }
        }
    }

    public static void deleteAllWarnInfoList() {
        unloadAreaInfoDao.deleteAll();
    }


}
