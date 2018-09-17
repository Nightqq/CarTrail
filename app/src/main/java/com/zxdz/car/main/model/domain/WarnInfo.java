package com.zxdz.car.main.model.domain;

import com.alibaba.fastjson.annotation.JSONField;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by admin on 2017/11/12.
 * 报警信息
 */

@Entity
public class WarnInfo implements Serializable {
    private static final long serialVersionUID = -7060210213611464481L;

    @Id
    private Long id;

    @JSONField(name = "BJ_ID")
    private int wid;//报警ID

    @JSONField(name = "LS_ID")
    private int lsId;//流水ID

    @JSONField(name = "BJSJ")
    private Date warnDate;

    @JSONField(name = "BJNR")
    private String warnContent;

    @JSONField(name = "BJLX")
    private int warnType;

    @JSONField(name = "SJJHBZ")
    private int dataState;//数据交换标志

    @JSONField(name = "DLGJKH")
    private String warnPoliceNum;

    @JSONField(name = "FLAG")
    private int warnFlag;


    @Generated(hash = 1998005962)
    public WarnInfo(Long id, int wid, int lsId, Date warnDate, String warnContent,
            int warnType, int dataState, String warnPoliceNum, int warnFlag) {
        this.id = id;
        this.wid = wid;
        this.lsId = lsId;
        this.warnDate = warnDate;
        this.warnContent = warnContent;
        this.warnType = warnType;
        this.dataState = dataState;
        this.warnPoliceNum = warnPoliceNum;
        this.warnFlag = warnFlag;
    }

    @Generated(hash = 1979995358)
    public WarnInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getWid() {
        return this.wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public int getLsId() {
        return this.lsId;
    }

    public void setLsId(int lsId) {
        this.lsId = lsId;
    }

    public Date getWarnDate() {
        return this.warnDate;
    }

    public void setWarnDate(Date warnDate) {
        this.warnDate = warnDate;
    }

    public String getWarnContent() {
        return this.warnContent;
    }

    public void setWarnContent(String warnContent) {
        this.warnContent = warnContent;
    }

    public int getWarnType() {
        return this.warnType;
    }

    public void setWarnType(int warnType) {
        this.warnType = warnType;
    }

    public int getDataState() {
        return this.dataState;
    }

    public void setDataState(int dataState) {
        this.dataState = dataState;
    }

    public String getWarnPoliceNum() {
        return this.warnPoliceNum;
    }

    public void setWarnPoliceNum(String warnPoliceNum) {
        this.warnPoliceNum = warnPoliceNum;
    }

    public int getWarnFlag() {
        return this.warnFlag;
    }

    public void setWarnFlag(int warnFlag) {
        this.warnFlag = warnFlag;
    }


}
