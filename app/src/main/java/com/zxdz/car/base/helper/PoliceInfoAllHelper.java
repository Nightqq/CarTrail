package com.zxdz.car.base.helper;

import com.zxdz.car.base.dao.PoliceInfoAllDao;
import com.zxdz.car.base.dao.PoliceInfoDao;
import com.zxdz.car.main.model.domain.PoliceInfoAll;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by Administrator on 2018\5\31 0031.
 */

public class PoliceInfoAllHelper {
    private static PoliceInfoAllDao policeInfoAllDao = CardDBUtil.getDaoSession().getPoliceInfoAllDao();


    public static List<PoliceInfoAll> getPoliceInfoAllListFromDB() {
        return policeInfoAllDao.queryBuilder().list();
    }

    public static List<PoliceInfoAll> getPoliceInfoAllListByLSID(String num) {
        QueryBuilder queryBuilder = policeInfoAllDao.queryBuilder();
        queryBuilder.where(PoliceInfoAllDao.Properties.DLGJ_KH.eq(num));
        return queryBuilder.list();
    }

    public static void savePoliceInfoAllToDB(PoliceInfoAll policeInfoAll) {
        if (policeInfoAll != null) {
            policeInfoAllDao.insertOrReplace(policeInfoAll);
        }
    }

    public static void savePoliceInfoAllListToDB(List<PoliceInfoAll> list) {
        if (list != null) {
            for (PoliceInfoAll info : list) {
                policeInfoAllDao.insertOrReplace(info);
            }
        }
    }

    public static void deletePoliceInfoAllInDB(PoliceInfoAll policeInfoAll) {
        policeInfoAllDao.delete(policeInfoAll);
    }

    public static void deleteAllPoliceInfoAllList() {
        policeInfoAllDao.deleteAll();
    }
}
