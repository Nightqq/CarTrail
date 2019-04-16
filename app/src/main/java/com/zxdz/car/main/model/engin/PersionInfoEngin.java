package com.zxdz.car.main.model.engin;

import android.content.Context;

import com.alibaba.fastjson.TypeReference;
import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.engin.HttpCoreEngin;
import com.zxdz.car.base.model.BaseEngin;
import com.zxdz.car.main.model.domain.DriverInfo;
import com.zxdz.car.main.model.domain.PersionInfo;
import com.zxdz.car.main.model.domain.PersionInfoWrapper;
import com.zxdz.car.main.model.domain.TerminalInfo;
import com.zxdz.car.main.model.domain.URLConfig;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by iflying on 2017/11/20.
 */

public class PersionInfoEngin extends BaseEngin {

    private Context mContext;

    public PersionInfoEngin(Context context) {
        super(context);
        mContext = context;
    }

    /**
     * 根据卡号获取人员信息
     * type ： 1（干警）  2（外来驾驶员）  3（内部驾驶员）
     * @param cardNumber
     * @return
     */
    public Observable<ResultInfo<PersionInfo>> getPersionInfo(String cardNumber) {
        Map<String, String> params = new HashMap<>();
        params.put("kh", cardNumber);
        return HttpCoreEngin.get(mContext).rxpost(URLConfig.getinstance().getGET_PERSION_INFO(), new TypeReference<ResultInfo<PersionInfo>>() {
                }.getType(), params,
                false, false,
                false);
    }
    public Observable<DriverInfo> getDriverInfo(String cardNumber) {
        Map<String, String> params = new HashMap<>();
        params.put("kh", cardNumber);
        return HttpCoreEngin.get(mContext).rxpost(URLConfig.getinstance().getGET_Driver_INFO(), new TypeReference<DriverInfo>() {
                }.getType(), params,
                false, false,
                false);
    }

}
