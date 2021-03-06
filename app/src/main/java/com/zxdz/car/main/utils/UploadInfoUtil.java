package com.zxdz.car.main.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.LogUtils;
import com.hwangjr.rxbus.RxBus;
import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.net.contains.HttpConfig;
import com.zxdz.car.App;
import com.zxdz.car.base.helper.CarTravelHelper;
import com.zxdz.car.base.helper.ChangePoliceInfoHelper;
import com.zxdz.car.base.helper.ResultInfoHelper;
import com.zxdz.car.base.helper.TakePictureHelper;
import com.zxdz.car.base.helper.TrailPointHelper;
import com.zxdz.car.base.helper.UnWarnInfoHelper;
import com.zxdz.car.base.helper.WarnInfoHelper;
import com.zxdz.car.main.model.domain.CarTravelRecord;
import com.zxdz.car.main.model.domain.ChangePoliceInfo;
import com.zxdz.car.main.model.domain.Constant;
import com.zxdz.car.main.model.domain.OpenLockInfo;
import com.zxdz.car.main.model.domain.PictureInfo;
import com.zxdz.car.main.model.domain.TrailPointInfo;
import com.zxdz.car.main.model.domain.TrailPointInfoWrapper;
import com.zxdz.car.main.model.domain.UnWarnInfo;
import com.zxdz.car.main.model.domain.WarnInfo;
import com.zxdz.car.main.model.engin.UploadInfoEngin;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by admin on 2017/11/25.
 * 上传信息辅助类
 */

public class UploadInfoUtil {

    private final String TAG = "UploadInfoUtil";

    private Context mContext;

    protected CompositeSubscription mSubscriptions;

    private UploadInfoEngin mEngin;

    private Intent intent;

    private boolean isFinish;

    public UploadInfoUtil(Context context) {
        mContext = context;
        mEngin = new UploadInfoEngin(context);
        mSubscriptions = new CompositeSubscription();
        RxBus.get().register(context);
    }

    public void uploadCarRecord(final CarTravelRecord carTravelRecord) {
        LogUtils.a("上传数据—上传主信息开始");
        Subscription subscribe = mEngin.uploadCarRecord(carTravelRecord).subscribe(new Subscriber<ResultInfo<CarTravelRecord>>() {
            @Override
            public void onCompleted() {
                LogUtils.a("上传数据—上传主信息onCompleted");
                uploadover();
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.a("上传数据—上传主信息error" + e.getMessage().toString());
            }

            @Override
            public void onNext(final ResultInfo<CarTravelRecord> resultInfo) {
                ResultInfoHelper.handleResultInfo(resultInfo, new ResultInfoHelper.Callback() {
                    @Override
                    public void resultInfoEmpty(String message) {
                        LogUtils.a("上传数据-resultInfoEmpty");
                    }

                    @Override
                    public void resultInfoNotOk(String message) {
                        LogUtils.a("上传数据-resultInfoNotOk", message);
                        LogUtils.a("上传数据-resultInfoNotOk",message);
                       /* if (CarTravelHelper.carTravelRecord != null && carTravelRecord != CarTravelHelper.carTravelRecord) {
                            CarTravelHelper.deleteCarTravelRecordInDB(carTravelRecord);
                            LogUtils.a("删除之前的流水数据", carTravelRecord.toString());
                        }*/
                    }

                    @Override
                    public void resultInfoOk() {//上传成功
                        if (resultInfo != null && resultInfo.data != null) {
                            LogUtils.a("上传数据—主信息上传完成返回" + JSON.toJSONString(resultInfo.data));
                            //删除之前的流水数据
                            if (CarTravelHelper.carTravelRecord != null && carTravelRecord != CarTravelHelper.carTravelRecord) {
                                CarTravelHelper.deleteCarTravelRecordInDB(carTravelRecord);
                                LogUtils.a("删除之前的流水数据", carTravelRecord.toString());
                            } else {
                                App.LSID = new Long(resultInfo.data.getLS_ID());
                                LogUtils.a("上传返回的流水id", App.LSID);
                            }
                            if (carTravelRecord.getLS_ID() == 0L) {
                                carTravelRecord.setLS_ID(resultInfo.data.getLS_ID());
                                CarTravelHelper.saveCarTravelRecordToDB(carTravelRecord);
                                //上传因断网未上传的，照片、报警、取消报警等数据
                                LogUtils.a("上传数据-主信息上传完成后上传附属信息");
                                uploadPicture(carTravelRecord.getId(), resultInfo.data.getLS_ID());
                                uploadWarnInfo(carTravelRecord.getId(), resultInfo.data.getLS_ID());
                                uploadUnWarnInfo();
                            }
                            //上传轨迹
                            LogUtils.a("准备上传轨迹", App.UPLOAD_STEP);
                            if (App.UPLOAD_STEP >= 3) {
                                LogUtils.a("==================准备上传轨迹=============================");
                                TrailPointInfoWrapper trailPointInfoWrapper = new TrailPointInfoWrapper();
                                trailPointInfoWrapper.setGid(resultInfo.data.getLS_ID() + "");
                                if (TrailPointHelper.getTrailPointInfoListFromDB() != null) {
                                    LogUtils.a("=======准备上传轨迹=轨迹上传中===");
                                    trailPointInfoWrapper.setPoints(TrailPointHelper.getTrailPointInfoListFromDB());
                                    uploadTrailPoint(trailPointInfoWrapper);
                                }else {
                                    LogUtils.a("=======准备上传轨迹=轨迹为空===");
                                }
                            }
                            //信息所有状态上传完成，则删除记录
                            if (carTravelRecord.getZT() == 80 && carTravelRecord == CarTravelHelper.carTravelRecord) {
                                CarTravelHelper.deleteCarTravelRecordInDB(carTravelRecord);//上传完成删除该条流水
                                App.UPLOAD_STEP = 1;
                                isFinish = true;
                            }
                            //主信息上传完成
                            LogUtils.a("主信息上传完成,发送广播");

                        } else {
                            LogUtils.e("主信息上传 error");
                        }
                    }
                });
            }
        });
        mSubscriptions.add(subscribe);
    }

    private void uploadover() {
        intent = new Intent();
        intent.setAction(Constant.UPLOAD_INFO_RESULT);
        intent.putExtra("result_code", 0);
        mContext.sendBroadcast(intent);
    }

    //上传照片
    public void uploadPicture(Long id, int lsid) {
        if (id != 0L) {
            final PictureInfo pictureInfo = TakePictureHelper.getWarnInfoListFromDB(id);
            if (pictureInfo != null) {
                pictureInfo.setLsId(lsid);
                TakePictureHelper.saveWarnInfoToDB(pictureInfo);
            }
        }
        List<PictureInfo> pictureInfos = TakePictureHelper.getpictureInfoListHaveLSID();
        if (pictureInfos != null && pictureInfos.size() > 0) {//上传所有有流水id是数据
            LogUtils.a("上传数据-未上传照片个数" + pictureInfos.size());
            for (final PictureInfo pictureInfo1 : pictureInfos) {
                LogUtils.a("上传数据—准备上传照片", pictureInfo1.toString());
                Subscription subscribe = mEngin.uploadPicture(pictureInfo1).subscribe(new Subscriber<ResultInfo>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.a("上传数据onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (e != null) {
                            LogUtils.a("上传数据-准备上传照片失败", e.getMessage().toString());
                        }
                    }

                    @Override
                    public void onNext(ResultInfo resultInfo) {
                        if (resultInfo != null && resultInfo.message.equals("拍照成功")) {
                            LogUtils.a("上传数据-照片成功" + resultInfo.message);
                            TakePictureHelper.deleteWarnInfoInDB(pictureInfo1);
                            List<PictureInfo> pictureInfoii = TakePictureHelper.getpictureInfoListHaveLSID();
                            if (pictureInfoii != null && pictureInfoii.size() > 0) {
                                LogUtils.a("上传数据-照片上传完成未上传照片个数" + pictureInfoii.size());
                            }
                        } else {
                            LogUtils.a("上传数据-照片" + resultInfo.message);
                        }
                        // LogUtils.a(pictureInfos.message);
                    }
                });
                mSubscriptions.add(subscribe);
            }
        }
    }

    //上传轨迹
    public void uploadTrailPoint(final TrailPointInfoWrapper trailPointInfoWrapper) {
        Subscription subscribe = mEngin.uploadTrailPoint(trailPointInfoWrapper).subscribe(new Subscriber<ResultInfo>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(final ResultInfo resultInfo) {
                LogUtils.a(TAG, "轨迹点上传onNext--->",resultInfo.toString());
                LogUtils.a(resultInfo.code + resultInfo.message);
                if (resultInfo.code == HttpConfig.STATUS_OK) {
                    LogUtils.a(TAG, "轨迹点上传完成--->");
                    List<TrailPointInfo> points = trailPointInfoWrapper.getPoints();
                    TrailPointHelper.deleteTrailPointInfoInDBlist(points);
                    if (isFinish) {
                        if (TrailPointHelper.getTrailPointInfoListFromDB() != null) {
                            LogUtils.a("准备删除轨迹");
                            TrailPointHelper.deleteAllTrailPointInfoList();
                        }
                    }
                }
            }
        });
        mSubscriptions.add(subscribe);
    }

    //上传报警
    public void uploadWarnInfo(Long id, int lsid) {
        List<WarnInfo> list = WarnInfoHelper.getWarnInfoListByID(id);
        if (list != null && list.size() > 0) {
            LogUtils.a("有报警数据，准备上传");
            for (final WarnInfo warnInfo : list) {
                warnInfo.setLsId(lsid);
                Subscription subscribe = mEngin.uploadWarnInfo(warnInfo).subscribe(new Subscriber<ResultInfo<WarnInfo>>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.a("报警信息上传onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.a("报警信息上传onError");
                        LogUtils.a(e.getMessage());
                    }

                    @Override
                    public void onNext(final ResultInfo<WarnInfo> resultInfo) {
                        if (resultInfo.message.equals("插入成功")) {
                            WarnInfoHelper.deleteWarnInfoInDB(warnInfo);
                            LogUtils.a("报警信息上传完成--->" + resultInfo.message);
                        }
                        UnWarnInfo unWarnInfo = UnWarnInfoHelper.getWarnInfoListByID(CarTravelHelper.carTravelRecord.getId(), false);
                        if (unWarnInfo == null) {
                            unWarnInfo = new UnWarnInfo();
                        }
                        unWarnInfo.setId(warnInfo.getId());
                        unWarnInfo.setWid(resultInfo.data.getWid());
                        unWarnInfo.setFlag(false);//不可上传
                        UnWarnInfoHelper.saveWarnInfoToDB(unWarnInfo);
                    }
                });
                mSubscriptions.add(subscribe);
            }
        }
    }

    //上传取消报警
    public void uploadUnWarnInfo() {
        List<UnWarnInfo> list1 = UnWarnInfoHelper.getWarnInfoListFromDB();
        if (list1 != null && list1.size() > 0) {
            LogUtils.a("有取消报警数据，准备上传");
            for (final UnWarnInfo warnInfos : list1) {
                Subscription subscribe = mEngin.uploadUnWarnInfo(warnInfos).subscribe(new Subscriber<ResultInfo>() {
                    @Override
                    public void onCompleted() {
                        LogUtils.a("取消报警信息上传onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.a("取消报警信息上传onError");
                        LogUtils.a(e.getMessage());
                    }

                    @Override
                    public void onNext(final ResultInfo resultInfo) {
                        LogUtils.a("取消报警信息上传完成--->" + resultInfo.message);
                        if (resultInfo != null && resultInfo.message.equals("更新成功")) {
                            LogUtils.a(resultInfo.message);
                            UnWarnInfoHelper.deleteWarnInfoInDB(warnInfos);
                        }
                    }
                });
                mSubscriptions.add(subscribe);
            }
        }
    }
   //远程开锁成功后取消远程开锁提示
    public void cancelUploadRequestOpenLock() {
        Subscription subscribe = mEngin.cancelUploadRequestOpenLock().subscribe(new Subscriber<ResultInfo>() {
            @Override
            public void onCompleted() {
                LogUtils.e(TAG, "请求取消远程开锁上传完成--->");
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e(TAG, "onerror"+e.getMessage());
            }

            @Override
            public void onNext(ResultInfo resultInfo) {
                if (resultInfo.code == HttpConfig.STATUS_OK) {
                    LogUtils.e(TAG, "请求取消远程开锁上传完成--->");
                }
            }
        });
        mSubscriptions.add(subscribe);
    }

    //远程开锁
    public void uploadRequestOpenLock(OpenLockInfo openLockInfo, RequestOpenLockListener lockListener) {
        listener = lockListener;
        Subscription subscribe = mEngin.uploadRequestOpenLock(openLockInfo).subscribe(new Subscriber<ResultInfo>() {
            @Override
            public void onCompleted() {
                LogUtils.e(TAG, "请求远程开锁上传完成--->");
            }

            @Override
            public void onError(Throwable e) {
                LogUtils.e(TAG, "onerror"+e.getMessage());
            }

            @Override
            public void onNext(ResultInfo resultInfo) {
                if (resultInfo.code == HttpConfig.STATUS_OK) {
                    LogUtils.e(TAG, "请求远程开锁上传完成--->");
                    listener.successful();
                }
            }
        });
        mSubscriptions.add(subscribe);
    }

    private RequestOpenLockListener listener = null;
    public interface RequestOpenLockListener{
        void successful();
    }

    public void uploadChangePoliceInfo(List<ChangePoliceInfo> changePoliceInfos) {
        Subscription subscribe = mEngin.uploadChangePoliceInfo(changePoliceInfos).subscribe(new Subscriber<ResultInfo>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(final ResultInfo resultInfo) {
                if (resultInfo.code == HttpConfig.STATUS_OK) {
                    if (ChangePoliceInfoHelper.getChangePoliceInfoListFromDB() != null) {
                        ChangePoliceInfoHelper.deleteAllChangePoliceInfoList();
                    }
                    LogUtils.e(TAG, "更换带领干警信息上传完成--->");
                }
            }
        });
        mSubscriptions.add(subscribe);
    }

    public void unregister() {
        RxBus.get().unregister(this);
    }

}
