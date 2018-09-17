package com.zxdz.car.base.helper;

import com.zxdz.car.base.dao.PictureInfoDao;
import com.zxdz.car.main.model.domain.PictureInfo;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\5\23 0023.
 */

public class TakePictureHelper {

    private static PictureInfoDao pictureInfoDao = CardDBUtil.getDaoSession().getPictureInfoDao();

    public static List<PictureInfo> getWarnInfoListFromDB() {
        return (ArrayList<PictureInfo>)pictureInfoDao.queryBuilder().list();
    }
    public static PictureInfo getWarnInfoListFromDB(Long id) {
        QueryBuilder queryBuilder = pictureInfoDao.queryBuilder();
        queryBuilder.where(PictureInfoDao.Properties.Id.eq(id));
        if (queryBuilder.list() != null && queryBuilder.list().size() > 0) {
            return (PictureInfo) queryBuilder.list().get(0);
        }
        return null;
    }

    public static void saveWarnInfoToDB(PictureInfo pictureInfo) {
        if (pictureInfo != null) {
            pictureInfoDao.insertOrReplace(pictureInfo);
        }
    }

    public static void saveWarnInfoListToDB(List<PictureInfo> list) {
        if (list != null) {
            for (PictureInfo info : list) {
                pictureInfoDao.insertOrReplace(info);
            }
        }
    }

    public static void deleteWarnInfoInDB(PictureInfo pictureInfo) {
        pictureInfoDao.delete(pictureInfo);
    }
}
