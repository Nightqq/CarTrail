package com.zxdz.car.base.helper;

import com.zxdz.car.base.dao.UnWarnInfoDao;
import com.zxdz.car.main.model.domain.UnWarnInfo;

import org.greenrobot.greendao.query.QueryBuilder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\5\15 0015.
 */

public class UnWarnInfoHelper {
    private static UnWarnInfoDao unwarnInfoDao = CardDBUtil.getDaoSession().getUnWarnInfoDao();

    public static List<UnWarnInfo> getWarnInfoListFromDB() {
        QueryBuilder queryBuilder = unwarnInfoDao.queryBuilder();
        queryBuilder.where(UnWarnInfoDao.Properties.Flag.eq(true));
        return (ArrayList<UnWarnInfo>)queryBuilder.list();
    }

    public static UnWarnInfo getWarnInfoListByID(long id,Boolean flag) {
        QueryBuilder queryBuilder = unwarnInfoDao.queryBuilder();
        queryBuilder.where(UnWarnInfoDao.Properties.Id.eq(id),UnWarnInfoDao.Properties.Flag.eq(flag));
        return (UnWarnInfo)queryBuilder.list().get(queryBuilder.list().size()-1);
    }

    public static void saveWarnInfoToDB(UnWarnInfo WarnInfo) {
        if (WarnInfo != null) {
            unwarnInfoDao.insertOrReplace(WarnInfo);
        }
    }

    public static void saveWarnInfoListToDB(List<UnWarnInfo> list) {
        if (list != null) {
            for (UnWarnInfo info : list) {
                unwarnInfoDao.insertOrReplace(info);
            }
        }
    }

    public static void deleteWarnInfoInDB(UnWarnInfo WarnInfo) {
        unwarnInfoDao.delete(WarnInfo);
    }

    public static void deleteAllWarnInfoList() {
        unwarnInfoDao.deleteAll();
    }
}
