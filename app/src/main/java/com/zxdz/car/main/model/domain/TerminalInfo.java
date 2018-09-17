package com.zxdz.car.main.model.domain;


import com.alibaba.fastjson.annotation.JSONField;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by iflying on 2017/11/7.
 * 终端机信息
 */

@Entity
public class TerminalInfo {

    @Id
    private Long id;

    @JSONField(name = "ZDJ_ID")
    private String zdjId;

    private String zdjName;

    private String simCardNumber;

    private String equImel;

    private int areaId;

    private String serverIp;

    private String useState;

    private String remark;



    @Generated(hash = 1002466036)
    public TerminalInfo() {
    }

    @Generated(hash = 884079201)
    public TerminalInfo(Long id, String zdjId, String zdjName, String simCardNumber,
            String equImel, int areaId, String serverIp, String useState,
            String remark) {
        this.id = id;
        this.zdjId = zdjId;
        this.zdjName = zdjName;
        this.simCardNumber = simCardNumber;
        this.equImel = equImel;
        this.areaId = areaId;
        this.serverIp = serverIp;
        this.useState = useState;
        this.remark = remark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }



    public String getZdjName() {
        return zdjName;
    }

    public void setZdjName(String zdjName) {
        this.zdjName = zdjName;
    }

    public String getSimCardNumber() {
        return simCardNumber;
    }

    public void setSimCardNumber(String simCardNumber) {
        this.simCardNumber = simCardNumber;
    }

    public String getEquImel() {
        return equImel;
    }

    public void setEquImel(String equImel) {
        this.equImel = equImel;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public String getUseState() {
        return useState;
    }

    public void setUseState(String useState) {
        this.useState = useState;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getZdjId() {
        return this.zdjId;
    }

    public void setZdjId(String zdjId) {
        this.zdjId = zdjId;
    }

}
