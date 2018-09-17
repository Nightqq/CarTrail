package com.zxdz.car.main.model.engin;

import android.content.Context;

import com.alibaba.fastjson.TypeReference;
import com.kk.securityhttp.domain.ResultInfo;
import com.kk.securityhttp.engin.HttpCoreEngin;
import com.zxdz.car.base.model.BaseEngin;
import com.zxdz.car.main.model.domain.TerminalInfo;
import com.zxdz.car.main.model.domain.URLConfig;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;

/**
 * Created by iflying on 2017/11/10.
 */

public class CardSetEngin extends BaseEngin {

    private Context mContext;

    public CardSetEngin(Context context) {
        super(context);
        mContext = context;
    }

    /**
     * @param type    代表需要修改的参数   ---0：是否外来人员刷卡  1：是否带领干警刷卡   2：是否外来驾驶员刷卡
     * @param upValue 就是修改的值(是与否对应的值) 0:刷卡 1:不刷卡
     * @param areaId 区域ID
     * @return
     */
    public Observable<ResultInfo> updateCardSet(int type, int upValue, int areaId) {
        Map<String, String> params = new HashMap<>();
        params.put("TYPE", type + "");
        params.put("VALUE", upValue + "");
        params.put("QY_ID", areaId + "");
        return HttpCoreEngin.get(mContext).rxpost(URLConfig.getinstance().getUPDATE_CARD_STATE_URL(), new TypeReference<ResultInfo>() {
                }.getType(), params,
                false, false,
                false);
    }
}
