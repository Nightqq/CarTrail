package com.zxdz.car.main.model.domain;

import com.alibaba.fastjson.annotation.JSONField;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by super on 2017/10/19.
 * 终端管理人员卡号
 */

@Entity
public class CardInfo implements Serializable {

    private static final long serialVersionUID = -7060210533611464481L;

    @Id
    private Long id;

    /**
     * 终端机序号
     */
    @JSONField(name = "ZDJ_ID")
    private String zdjId;

    /**
     * 管理人员卡号
     */
    @JSONField(name = "GLRYKH")
    private String adminCardNumber;

    /**
     * 管理人员姓名
     */
    @JSONField(name = "GLRYXM")
    private String adminName;

    @JSONField(name = "BZ")
    private String remark;

    @JSONField(name = "RYLX")
    private int cardType;

    @Generated(hash = 555217359)
    public CardInfo() {
    }

    @Generated(hash = 2027384224)
    public CardInfo(Long id, String zdjId, String adminCardNumber, String adminName,
            String remark, int cardType) {
        this.id = id;
        this.zdjId = zdjId;
        this.adminCardNumber = adminCardNumber;
        this.adminName = adminName;
        this.remark = remark;
        this.cardType = cardType;
    }


    public String getAdminCardNumber() {
        return adminCardNumber;
    }

    public void setAdminCardNumber(String adminCardNumber) {
        this.adminCardNumber = adminCardNumber;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getZdjId() {
        return this.zdjId;
    }

    public void setZdjId(String zdjId) {
        this.zdjId = zdjId;
    }


    public int getCardType() {
        return this.cardType;
    }


    public void setCardType(int cardType) {
        this.cardType = cardType;
    }
}
