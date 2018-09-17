package com.zxdz.car.base.helper;

import com.blankj.utilcode.util.LogUtils;
import com.zxdz.car.base.dao.AreaInfoDao;
import com.zxdz.car.main.model.domain.AreaInfo;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * 区域信息数据库操作
 */

public class AreaHelper {

    private static AreaInfoDao areaInfo = CardDBUtil.getDaoSession().getAreaInfoDao();

    /**
     * 根据设备号和区域ID查询区域信息
     *
     * @param zdjId
     * @param areaId
     * @return
     */
    public static AreaInfo getAreaInfoByParams(String zdjId, int areaId) {
        QueryBuilder queryBuilder = areaInfo.queryBuilder();
        queryBuilder.where(AreaInfoDao.Properties.ZdjId.eq(zdjId));
        queryBuilder.where(AreaInfoDao.Properties.AreaId.eq(areaId));
        try {
            if (queryBuilder.list() != null && queryBuilder.list().size()>0) {
                return (AreaInfo) queryBuilder.list().get(0);
            }
        }catch (Exception e){
            LogUtils.i("1111222222",""+e.getMessage());
        }
        return null;
    }

    public static List<AreaInfo> getAreaInfoListFromDB() {
        return areaInfo.queryBuilder().list();
    }

    public static void saveAreaInfoToDB(AreaInfo AreaInfo) {
        if (AreaInfo != null) {
            areaInfo.insertOrReplace(AreaInfo);
        }
    }

    public static void saveAreaInfoListToDB(List<AreaInfo> list) {
        if (list != null) {
            for (AreaInfo info : list) {
                areaInfo.insertOrReplace(info);
            }
        }
    }

    public static void deleteAreaInfoInDB(AreaInfo AreaInfo) {
        areaInfo.delete(AreaInfo);
    }

    public static void deleteAllAreaInfoList() {
        areaInfo.deleteAll();
    }
}
