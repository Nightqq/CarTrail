package com.zxdz.car.main.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.Utils;
import com.kk.securityhttp.domain.ResultInfo;
import com.zxdz.car.App;
import com.zxdz.car.base.helper.PoliceInfoAllHelper;
import com.zxdz.car.base.helper.ResultInfoHelper;
import com.zxdz.car.base.presenter.BasePresenter;
import com.zxdz.car.main.contract.PersionInfoContract;
import com.zxdz.car.main.contract.SettingInfoContract;
import com.zxdz.car.main.model.domain.DriverInfo;
import com.zxdz.car.main.model.domain.PersionInfo;
import com.zxdz.car.main.model.domain.PersionInfoWrapper;
import com.zxdz.car.main.model.domain.PoliceInfoAll;
import com.zxdz.car.main.model.domain.TerminalInfo;
import com.zxdz.car.main.model.engin.PersionInfoEngin;
import com.zxdz.car.main.model.engin.SettingInfoEngin;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by iflying on 2017/11/20.
 */

public class PersionInfoPresenter extends BasePresenter<PersionInfoEngin, PersionInfoContract.View> implements PersionInfoContract.Presenter {

    private String TAG = "personinfo";
    public PersionInfoPresenter(Context context, PersionInfoContract.View view) {
        super(context, view);
        mEngin = new PersionInfoEngin(context);
    }

    /**
     * 根据卡号获取人员信息
     *
     * @param cardNumber
     */
    @Override
    public void getPersionInfo(String cardNumber) {
       /* List<PoliceInfoAll> warnInfoListByLSID = PoliceInfoAllHelper.getPoliceInfoAllListByLSID(cardNumber);
        if (warnInfoListByLSID!=null&&warnInfoListByLSID.size()>0){
            mView.showPoliceInfoAll(warnInfoListByLSID.get(0));
        }*/
        Subscription subscribe = mEngin.getPersionInfo(cardNumber).subscribe(new Subscriber<ResultInfo<PersionInfo>>() {
            @Override
            public void onCompleted() {
                //  LogUtils.a("onCompleted");
            }
            @Override
            public void onError(Throwable e) {
                LogUtils.a("onError");
                mView.showNoNet();
            }
            @Override
            public void onNext(final ResultInfo<PersionInfo> resultInfo) {
                ResultInfoHelper.handleResultInfo(resultInfo, new ResultInfoHelper.Callback() {
                    @Override
                    public void resultInfoEmpty(String message) {
                        mView.showNoNet();
                        //LogUtils.a("resultInfoEmpty失败");
                    }

                    @Override
                    public void resultInfoNotOk(String message) {
                        mView.showNoNet();
                        LogUtils.a("resultInfoNotOk失败");
                    }

                    @Override
                    public void resultInfoOk() {
                        LogUtils.a(resultInfo.toString());
                        LogUtils.a(resultInfo.data.getName());
                        if (resultInfo != null) {
                            LogUtils.a("成功返回数据");
                            mView.showPersionInfo(resultInfo.data);
                        } else {
                            LogUtils.a("成功返回数据");
                            mView.showNoNet();
                        }
                    }
                });
            }
        });
        mSubscriptions.add(subscribe);
    }


    public void getDriverInfo(String cardNumber) {
     Subscription subscribe = mEngin.getDriverInfo(cardNumber).subscribe(new Subscriber<DriverInfo>() {
            @Override
            public void onCompleted() {
               LogUtils.a("onCompleted");
            }
            @Override
            public void onError(Throwable e) {
                LogUtils.a("onError",e.getMessage());
                mView.showNoNet();
            }
            @Override
            public void onNext(final DriverInfo resultInfo) {
                if (resultInfo!=null){
                    LogUtils.a("返回驾驶员数据",resultInfo.toString());
                }else {
                    LogUtils.a("返回驾驶员数据","resultInfo空");
                }

                // LogUtils.a("返回驾驶员数据",resultInfo.data);
                if (resultInfo != null) {
                    LogUtils.a("成功返回数据");
                    mView.showdriverInfo(resultInfo);
                } else {
                    LogUtils.a("成功返回数据");
                    mView.showNoNet();
                }

               /* ResultInfoHelper.handleResultInfo(resultInfo, new ResultInfoHelper.Callback() {
                    @Override
                    public void resultInfoEmpty(String message) {
                        mView.showNoNet();
                        LogUtils.a("resultInfoEmpty");
                    }

                    @Override
                    public void resultInfoNotOk(String message) {
                        mView.showNoNet();
                        LogUtils.a("resultInfoNotOk失败");
                    }

                    @Override
                    public void resultInfoOk() {
                        LogUtils.a("返回驾驶员数据",resultInfo.toString());
                       // LogUtils.a("返回驾驶员数据",resultInfo.data);
                        if (resultInfo != null) {
                            LogUtils.a("成功返回数据");
                            //mView.showPersionInfo(resultInfo);
                        } else {
                            LogUtils.a("成功返回数据");
                            mView.showNoNet();
                        }
                    }
                });*/
            }
        });
        mSubscriptions.add(subscribe);
    }






    @Override
    public void loadData(boolean forceUpdate, boolean showLoadingUI) {

    }
}
