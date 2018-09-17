package com.zxdz.car.main.presenter;

import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.net.contains.HttpConfig;
import com.zxdz.car.App;
import com.zxdz.car.base.helper.CarTravelHelper;
import com.zxdz.car.base.helper.ChangePoliceInfoHelper;
import com.zxdz.car.base.helper.ResultInfoHelper;
import com.zxdz.car.base.helper.TrailPointHelper;
import com.zxdz.car.base.helper.WarnInfoHelper;
import com.zxdz.car.base.presenter.BasePresenter;
import com.zxdz.car.main.contract.UploadInfoContract;
import com.zxdz.car.main.model.domain.CarTravelRecord;
import com.zxdz.car.main.model.domain.ChangePoliceInfo;
import com.zxdz.car.main.model.domain.TrailPointInfoWrapper;
import com.zxdz.car.main.model.domain.WarnInfo;
import com.zxdz.car.main.model.engin.UploadInfoEngin;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;


public class UploadInfoPresenter extends BasePresenter<UploadInfoEngin, UploadInfoContract.View> implements UploadInfoContract.Presenter {

    private final String TAG = "upload";

    public UploadInfoPresenter(Context context, UploadInfoContract.View view) {
        super(context, view);
        mEngin = new UploadInfoEngin(context);
    }

    @Override
    public void uploadCarRecord(CarTravelRecord carTravelRecord) {
        mView.showLoadingDialog("正在上传");
        Subscription subscribe = mEngin.uploadCarRecord(carTravelRecord).subscribe(new Subscriber<ResultInfo<CarTravelRecord>>() {
            @Override
            public void onCompleted() {
                //mView.dismissLoadingDialog();
                LogUtils.e(TAG, "主信息上传完成--->");
            }

            @Override
            public void onError(Throwable e) {
                mView.dismissLoadingDialog();
                mView.showNoNet();
            }

            @Override
            public void onNext(final ResultInfo<CarTravelRecord> resultInfo) {
                ResultInfoHelper.handleResultInfo(resultInfo, new ResultInfoHelper.Callback() {
                    @Override
                    public void resultInfoEmpty(String message) {
                        mView.showNoNet();
                    }

                    @Override
                    public void resultInfoNotOk(String message) {
                        mView.showNoNet();
                    }

                    @Override
                    public void resultInfoOk() {
                        if (resultInfo != null && resultInfo.data != null) {
                            App.LSID = new Long(resultInfo.data.getLS_ID());
                            //设置
                            List<CarTravelRecord> carTravelRecordListFromDB = CarTravelHelper.getCarTravelRecordListFromDB();
                            if (carTravelRecordListFromDB != null && carTravelRecordListFromDB.size() > 0) {
                                for (CarTravelRecord carTravelRecord : carTravelRecordListFromDB) {
                                    if (carTravelRecord != null) {
                                        carTravelRecord.setLS_ID(resultInfo.data.getLS_ID());
                                        CarTravelHelper.saveCarTravelRecordToDB(carTravelRecord);
                                    }
                                }
                            }
                            //上传轨迹路线
                            TrailPointInfoWrapper trailPointInfoWrapper = new TrailPointInfoWrapper();
                            trailPointInfoWrapper.setGid(resultInfo.data.getLS_ID() + "");
                            if (TrailPointHelper.getTrailPointInfoListFromDB() != null) {
                                trailPointInfoWrapper.setPoints(TrailPointHelper.getTrailPointInfoListFromDB());
                            }

                            //上传轨迹
                            uploadTrailPoint(trailPointInfoWrapper);
                        } else {
                            mView.showNoNet();
                        }
                    }
                });
            }
        });
        mSubscriptions.add(subscribe);
    }

    //上传轨迹
    @Override
    public void uploadTrailPoint(TrailPointInfoWrapper trailPointInfoWrapper) {
        Subscription subscribe = mEngin.uploadTrailPoint(trailPointInfoWrapper).subscribe(new Subscriber<ResultInfo>() {
            @Override
            public void onCompleted() {
                LogUtils.e(TAG, "轨迹点上传完成--->");
            }

            @Override
            public void onError(Throwable e) {
                mView.dismissLoadingDialog();
                mView.showNoNet();
            }

            @Override
            public void onNext(final ResultInfo resultInfo) {
            }

        });
        mSubscriptions.add(subscribe);
    }

    @Override
    public void uploadWarnInfo(WarnInfo warnInfos) {
        Subscription subscribe = mEngin.uploadWarnInfo(warnInfos).subscribe(new Subscriber<ResultInfo>() {
            @Override
            public void onCompleted() {
                LogUtils.e(TAG, "报警信息上传完成--->");
            }

            @Override
            public void onError(Throwable e) {
                mView.dismissLoadingDialog();
                mView.showNoNet();
            }

            @Override
            public void onNext(final ResultInfo resultInfo) {
                if (resultInfo.code == HttpConfig.STATUS_OK) {
                    List<ChangePoliceInfo> list = ChangePoliceInfoHelper.getChangePoliceInfoListByLSID(App.LSID.intValue());
                    if (list != null && list.size() > 0) {
                        //上传更换带领干警信息
                        uploadChangePoliceInfo(list);
                    }
                }
            }

        });
        mSubscriptions.add(subscribe);
    }

    @Override
    public void uploadChangePoliceInfo(List<ChangePoliceInfo> changePoliceInfos) {
        Subscription subscribe = mEngin.uploadChangePoliceInfo(changePoliceInfos).subscribe(new Subscriber<ResultInfo>() {
            @Override
            public void onCompleted() {
                mView.dismissLoadingDialog();
                LogUtils.e(TAG, "更换带领干警信息上传完成--->");
            }

            @Override
            public void onError(Throwable e) {
                mView.dismissLoadingDialog();
                mView.showNoNet();
            }

            @Override
            public void onNext(final ResultInfo resultInfo) {
                mView.dismissLoadingDialog();
                if (resultInfo.code == HttpConfig.STATUS_OK) {
                    mView.loadChangeUpload(resultInfo);
                }
            }

        });
        mSubscriptions.add(subscribe);
    }

    @Override
    public void loadData(boolean forceUpdate, boolean showLoadingUI) {
        if (!forceUpdate) return;
    }
}
