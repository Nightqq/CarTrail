package com.zxdz.car.main.model.domain;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by iflying on 2017/11/21.
 */

public class PersionInfo implements Serializable {

    private static final long serialVersionUID = -7060210532611464481L;

    private int type;

    @JSONField(name="kh")
    private String cardNumber;

    @JSONField(name = "name")
    private String name;

    @JSONField(name="depname")
    private String depName;

    @JSONField(name="jh")
    private String alarm;

    private String sfz;

    private String cph;

    private String fwbmmc;

    private String remark;

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }

    public String getAlarm() {
        return alarm;
    }

    public void setAlarm(String alarm) {
        this.alarm = alarm;
    }

    public String getSfz() {
        return sfz;
    }

    public void setSfz(String sfz) {
        this.sfz = sfz;
    }

    public String getCph() {
        return cph;
    }

    public void setCph(String cph) {
        this.cph = cph;
    }

    public String getFwbmmc() {
        return fwbmmc;
    }

    public void setFwbmmc(String fwbmmc) {
        this.fwbmmc = fwbmmc;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
