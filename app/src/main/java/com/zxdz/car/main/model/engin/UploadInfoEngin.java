package com.zxdz.car.main.model.engin;

import android.content.Context;
import android.util.Log;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.blankj.utilcode.util.LogUtils;
import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.engin.HttpCoreEngin;
import com.zxdz.car.App;
import com.zxdz.car.base.model.BaseEngin;
import com.zxdz.car.main.model.domain.CarTravelRecord;
import com.zxdz.car.main.model.domain.ChangePoliceInfo;
import com.zxdz.car.main.model.domain.OpenLockInfo;
import com.zxdz.car.main.model.domain.PersionInfo;
import com.zxdz.car.main.model.domain.PictureInfo;
import com.zxdz.car.main.model.domain.TrailPointInfoWrapper;
import com.zxdz.car.main.model.domain.URLConfig;
import com.zxdz.car.main.model.domain.UnWarnInfo;
import com.zxdz.car.main.model.domain.WarnInfo;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.Observable;


public class UploadInfoEngin extends BaseEngin {

    private Context mContext;
    public UploadInfoEngin(Context context) {
        super(context);
        mContext = context;
    }

    public Observable<ResultInfo<CarTravelRecord>> uploadCarRecord(CarTravelRecord carTravelRecord) {
        Map<String, String> params = new HashMap<>();
        if (carTravelRecord != null) {
            params.put("cljcls", JSON.toJSONString(carTravelRecord));
            LogUtils.a("上传流水：" + JSON.toJSONString(carTravelRecord));
        }
        return HttpCoreEngin.get(mContext).rxpost(URLConfig.getinstance().getUPDATE_CAR_RECORD_URL(), new TypeReference<ResultInfo<CarTravelRecord>>() {
                }.getType(), params,
                false, false,
                false);
    }

    public Observable<ResultInfo<PictureInfo>> uploadPicture(PictureInfo pictureInfo) {
        Map<String, String> params = new HashMap<>();

        if (pictureInfo != null) {
            params.put("cljcls", JSON.toJSONString(pictureInfo));
            LogUtils.a(JSON.toJSONString(pictureInfo));
        }
        return HttpCoreEngin.get(mContext).rxpost(URLConfig.getinstance().getUPDATE_TAKE_PICTURE_URL(), new TypeReference<ResultInfo<PictureInfo>>() {
                }.getType(), params,
                false, false,
                false);
    }

    public Observable<ResultInfo> uploadTrailPoint(TrailPointInfoWrapper trailPointInfoWrapper) {
        Map<String, String> params = new HashMap<>();
        if (trailPointInfoWrapper != null) {
            params.put("cljclsgj", JSON.toJSONString(trailPointInfoWrapper));
            LogUtils.a("cljclsgj",JSON.toJSONString(trailPointInfoWrapper));
        }
        return HttpCoreEngin.get(mContext).rxpost(URLConfig.getinstance().getUPDATE_TRAIL_POINT_URL(), new TypeReference<ResultInfo>() {
                }.getType(), params,
                false, false,
                false);
    }

    public Observable<ResultInfo<WarnInfo>> uploadWarnInfo(WarnInfo warnInfos) {
        Map<String, String> params = new HashMap<>();
        if (warnInfos != null) {
            params.put("cljclsbj", JSON.toJSONString(warnInfos));
            LogUtils.a("报警数据", JSON.toJSONString(warnInfos));
        }
        return HttpCoreEngin.get(mContext).rxpost(URLConfig.getinstance().getUPDATE_WATN_INFO_URL(), new TypeReference<ResultInfo<WarnInfo>>() {
                }.getType(), params,
                false, false,
                false);
    }

    public Observable<ResultInfo> uploadUnWarnInfo(UnWarnInfo unwarnInfos) {
        Map<String, String> params = new HashMap<>();
        if (unwarnInfos != null) {
            params.put("BJ_ID", JSON.toJSONString(unwarnInfos));

            LogUtils.a("取消报警", JSON.toJSONString(unwarnInfos));
        }

        return HttpCoreEngin.get(mContext).rxpost(URLConfig.getinstance().getUPDATE_UNWATN_INFO_URL() + unwarnInfos.getWid(), new TypeReference<ResultInfo>() {
                }.getType(), params,
                false, false,
                false);
    }
    //远程开锁
    public Observable<ResultInfo> uploadRequestOpenLock(OpenLockInfo openLockInfo){
        Map<String, String> params = new HashMap<>();
//        if (openLockInfo != null) {
//            params.put("remoteunlock", JSON.toJSONString(openLockInfo));
//
//            LogUtils.a("远程开锁", JSON.toJSONString(openLockInfo));
//            LogUtils.a("远程开锁", openLockInfo.getIP()+openLockInfo.getPort());
//        }
        return HttpCoreEngin.get(mContext).rxpost(URLConfig.getinstance().getsend_unLOCK_INFO_URL()+"&IP="+openLockInfo.getIP()+"&port="+openLockInfo.getPort()+"&zjdid="+App.ZDJID,new TypeReference<ResultInfo>(){
        }.getType(),params,false,false,false);
    }

    public Observable<ResultInfo> uploadChangePoliceInfo(List<ChangePoliceInfo> changePoliceInfos) {
        Map<String, String> params = new HashMap<>();
        if (changePoliceInfos != null) {
            params.put("cljclsgj", JSON.toJSONString(changePoliceInfos));
        }

        return HttpCoreEngin.get(mContext).rxpost(URLConfig.getinstance().getUPDATE_CHANGE_INFO_URL(), new TypeReference<ResultInfo>() {
                }.getType(), params,
                false, false,
                false);
    }
}
