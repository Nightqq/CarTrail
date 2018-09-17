package com.zxdz.car.base.helper;

import com.zxdz.car.base.dao.WarnInfoDao;
import com.zxdz.car.main.model.domain.WarnInfo;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 *
 */

public class WarnInfoHelper {

    private static WarnInfoDao warnInfoDao = CardDBUtil.getDaoSession().getWarnInfoDao();


    public static List<WarnInfo> getWarnInfoListFromDB() {
        return warnInfoDao.queryBuilder().list();
    }

    public static List<WarnInfo> getWarnInfoListByID(Long id) {
        QueryBuilder queryBuilder = warnInfoDao.queryBuilder();
        queryBuilder.where(WarnInfoDao.Properties.Id.eq(id));
        return queryBuilder.list();
    }

    public static void saveWarnInfoToDB(WarnInfo WarnInfo) {
        if (WarnInfo != null) {
            warnInfoDao.insertOrReplace(WarnInfo);
        }
    }

    public static void saveWarnInfoListToDB(List<WarnInfo> list) {
        if (list != null) {
            for (WarnInfo info : list) {
                warnInfoDao.insertOrReplace(info);
            }
        }
    }

    public static void deleteWarnInfoInDB(WarnInfo WarnInfo) {
        warnInfoDao.delete(WarnInfo);
    }

    public static void deleteAllWarnInfoList() {
        warnInfoDao.deleteAll();
    }
}
