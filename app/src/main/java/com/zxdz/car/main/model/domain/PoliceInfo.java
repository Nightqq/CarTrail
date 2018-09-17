package com.zxdz.car.main.model.domain;

import com.alibaba.fastjson.annotation.JSONField;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by iflying on 2017/11/20.
 * 带领干警信息
 */

@Entity
public class PoliceInfo implements Serializable {

    private static final long serialVersionUID = -7060210233211444481L;

    @Id
    private Long id;

    //领用
    @JSONField(name = "DLGJ_LYKH")
    private String DLGJ_LYKH ="";

    @JSONField(name = "DLGJ_LYSJ")
    private Date DLGJ_LYSJ = new Date();

    @JSONField(name = "DLGJ_LYXM")
    private String DLGJ_LYXM;

    @JSONField(name = "DLGJ_LYJH")
    private String DLGJ_LYJH;

    @JSONField(name = "DLGJ_LYBM")
    private String DLGJ_LYBM;


    @JSONField(name = "DLGJ_JHKH")
    private String DLGJ_JHKH = "";
    @JSONField(name = "DLGJ_JHSJ")
    private Date DLGJ_JHSJ = new Date();

    @JSONField(name = "DLGJ_JHXM")
    private String DLGJ_JHXM = "";

    @JSONField(name = "DLGJ_JHJH")
    private String DLGJ_JHJH = "";

    @JSONField(name = "DLGJ_JHBM")
    private String DLGJ_JHBM = "";

    @Generated(hash = 134130048)
    public PoliceInfo() {
    }

    @Generated(hash = 1919329692)
    public PoliceInfo(Long id, String DLGJ_LYKH, Date DLGJ_LYSJ, String DLGJ_LYXM,
            String DLGJ_LYJH, String DLGJ_LYBM, String DLGJ_JHKH, Date DLGJ_JHSJ,
            String DLGJ_JHXM, String DLGJ_JHJH, String DLGJ_JHBM) {
        this.id = id;
        this.DLGJ_LYKH = DLGJ_LYKH;
        this.DLGJ_LYSJ = DLGJ_LYSJ;
        this.DLGJ_LYXM = DLGJ_LYXM;
        this.DLGJ_LYJH = DLGJ_LYJH;
        this.DLGJ_LYBM = DLGJ_LYBM;
        this.DLGJ_JHKH = DLGJ_JHKH;
        this.DLGJ_JHSJ = DLGJ_JHSJ;
        this.DLGJ_JHXM = DLGJ_JHXM;
        this.DLGJ_JHJH = DLGJ_JHJH;
        this.DLGJ_JHBM = DLGJ_JHBM;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDLGJ_LYKH() {
        return this.DLGJ_LYKH;
    }

    public void setDLGJ_LYKH(String DLGJ_LYKH) {
        this.DLGJ_LYKH = DLGJ_LYKH;
    }

    public Date getDLGJ_LYSJ() {
        return this.DLGJ_LYSJ;
    }

    public void setDLGJ_LYSJ(Date DLGJ_LYSJ) {
        this.DLGJ_LYSJ = DLGJ_LYSJ;
    }

    public String getDLGJ_LYXM() {
        return this.DLGJ_LYXM;
    }

    public void setDLGJ_LYXM(String DLGJ_LYXM) {
        this.DLGJ_LYXM = DLGJ_LYXM;
    }

    public String getDLGJ_LYJH() {
        return this.DLGJ_LYJH;
    }

    public void setDLGJ_LYJH(String DLGJ_LYJH) {
        this.DLGJ_LYJH = DLGJ_LYJH;
    }

    public String getDLGJ_LYBM() {
        return this.DLGJ_LYBM;
    }

    public void setDLGJ_LYBM(String DLGJ_LYBM) {
        this.DLGJ_LYBM = DLGJ_LYBM;
    }

    public String getDLGJ_JHKH() {
        return this.DLGJ_JHKH;
    }

    public void setDLGJ_JHKH(String DLGJ_JHKH) {
        this.DLGJ_JHKH = DLGJ_JHKH;
    }

    public Date getDLGJ_JHSJ() {
        return this.DLGJ_JHSJ;
    }

    public void setDLGJ_JHSJ(Date DLGJ_JHSJ) {
        this.DLGJ_JHSJ = DLGJ_JHSJ;
    }

    public String getDLGJ_JHXM() {
        return this.DLGJ_JHXM;
    }

    public void setDLGJ_JHXM(String DLGJ_JHXM) {
        this.DLGJ_JHXM = DLGJ_JHXM;
    }

    public String getDLGJ_JHJH() {
        return this.DLGJ_JHJH;
    }

    public void setDLGJ_JHJH(String DLGJ_JHJH) {
        this.DLGJ_JHJH = DLGJ_JHJH;
    }

    public String getDLGJ_JHBM() {
        return this.DLGJ_JHBM;
    }

    public void setDLGJ_JHBM(String DLGJ_JHBM) {
        this.DLGJ_JHBM = DLGJ_JHBM;
    }
}
