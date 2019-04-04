package com.zxdz.car.main.model.domain;

import com.alibaba.fastjson.annotation.JSONField;

import org.greenrobot.greendao.annotation.Entity;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018\5\31 0031.
 */
@Entity
public class PoliceInfoAll {

    @JSONField(name = "DLGJ_KH")
    private String DLGJ_KH ="";

    @JSONField(name = "DLGJ_XM")
    private String DLGJ_XM;

    @JSONField(name = "DLGJ_BMMC")
    private String DLGJ_BMMC;

    @JSONField(name = "DLGJ_JH")
    private String DLGJ_JH;




    @Generated(hash = 1456627152)
    public PoliceInfoAll(String DLGJ_KH, String DLGJ_XM, String DLGJ_BMMC,
            String DLGJ_JH) {
        this.DLGJ_KH = DLGJ_KH;
        this.DLGJ_XM = DLGJ_XM;
        this.DLGJ_BMMC = DLGJ_BMMC;
        this.DLGJ_JH = DLGJ_JH;
    }

    @Generated(hash = 771278551)
    public PoliceInfoAll() {
    }

    public String getDLGJ_KH() {
        return this.DLGJ_KH;
    }

    public void setDLGJ_KH(String DLGJ_KH) {
        this.DLGJ_KH = DLGJ_KH;
    }

    public String getDLGJ_XM() {
        return this.DLGJ_XM;
    }

    public void setDLGJ_XM(String DLGJ_XM) {
        this.DLGJ_XM = DLGJ_XM;
    }

    public String getDLGJ_BMMC() {
        return this.DLGJ_BMMC;
    }

    public void setDLGJ_BMMC(String DLGJ_BMMC) {
        this.DLGJ_BMMC = DLGJ_BMMC;
    }

    public String getDLGJ_JH() {
        return this.DLGJ_JH;
    }

    public void setDLGJ_JH(String DLGJ_JH) {
        this.DLGJ_JH = DLGJ_JH;
    }
}
