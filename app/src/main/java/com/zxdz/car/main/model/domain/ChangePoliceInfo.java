package com.zxdz.car.main.model.domain;

import com.alibaba.fastjson.annotation.JSONField;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * Created by admin on 2017/11/12.
 * 带领干警更换
 */

@Entity
public class ChangePoliceInfo implements Serializable {
    private static final long serialVersionUID = -7060210213211464481L;

    @Id
    private Long id;

    @JSONField(name = "GH_ID")
    private int cid;//更换ID

    @JSONField(name = "LS_ID")
    private int lsId;//流水ID

    @JSONField(name = "GJSJ")
    private String oldCardNumber;

    @JSONField(name = "GJZBJD")
    private String newCardNumber;

    @Generated(hash = 1229091730)
    public ChangePoliceInfo(Long id, int cid, int lsId, String oldCardNumber,
                            String newCardNumber) {
        this.id = id;
        this.cid = cid;
        this.lsId = lsId;
        this.oldCardNumber = oldCardNumber;
        this.newCardNumber = newCardNumber;
    }

    @Generated(hash = 331506406)
    public ChangePoliceInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCid() {
        return this.cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getLsId() {
        return this.lsId;
    }

    public void setLsId(int lsId) {
        this.lsId = lsId;
    }

    public String getOldCardNumber() {
        return this.oldCardNumber;
    }

    public void setOldCardNumber(String oldCardNumber) {
        this.oldCardNumber = oldCardNumber;
    }

    public String getNewCardNumber() {
        return this.newCardNumber;
    }

    public void setNewCardNumber(String newCardNumber) {
        this.newCardNumber = newCardNumber;
    }


}
