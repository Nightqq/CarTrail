package com.zxdz.car.base.helper;

import com.zxdz.car.base.dao.ChangePoliceDao;
import com.zxdz.car.base.dao.ChangePoliceInfoDao;
import com.zxdz.car.base.dao.ChangePoliceInfoDao;
import com.zxdz.car.main.model.domain.ChangePolice;
import com.zxdz.car.main.model.domain.ChangePoliceInfo;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class ChangePoliceInfoHelper {

    public static ChangePoliceInfo changePoliceInfo;
    private static ChangePoliceInfoDao changePoliceInfoDao = CardDBUtil.getDaoSession().getChangePoliceInfoDao();

    public static List<ChangePoliceInfo> getChangePoliceInfoListFromDB() {
        return changePoliceInfoDao.queryBuilder().list();
    }

    public static List<ChangePoliceInfo> getChangePoliceInfoListByLSID(int lsId) {
        QueryBuilder queryBuilder = changePoliceInfoDao.queryBuilder();
        queryBuilder.where(ChangePoliceInfoDao.Properties.LsId.eq(lsId));
        return queryBuilder.list();
    }

    public static void saveChangePoliceInfoToDB(ChangePoliceInfo ChangePoliceInfo) {
        if (ChangePoliceInfo != null) {
            changePoliceInfoDao.insertOrReplace(ChangePoliceInfo);
        }
    }

    public static void saveChangePoliceInfoListToDB(List<ChangePoliceInfo> list) {
        if (list != null) {
            for (ChangePoliceInfo info : list) {
                changePoliceInfoDao.insertOrReplace(info);
            }
        }
    }

    public static void deleteChangePoliceInfoInDB(ChangePoliceInfo ChangePoliceInfo) {
        changePoliceInfoDao.delete(ChangePoliceInfo);
    }

    public static void deleteAllChangePoliceInfoList() {
        changePoliceInfoDao.deleteAll();
    }
}
