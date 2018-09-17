package com.zxdz.car.main.model.domain;

import com.alibaba.fastjson.annotation.JSONField;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by iflying on 2017/11/20.
 * 驾驶员信息
 */

@Entity
public class DriverInfo implements Serializable {

    private static final long serialVersionUID = -7060210233212444481L;

    @Id
    private Long id;

    @JSONField(name = "WLJSYKH")
    private String WLJSYKH = "";

    @JSONField(name = "WLJSYSJ")
    private Date WLJSYSJ = new Date();

    @JSONField(name = "JSYXM")
    private String JSYXM ="";

    @JSONField(name = "JSYXB")
    private String JSYXB ="";

    @JSONField(name = "JRSY")
    private String JRSY ="";

    @JSONField(name = "JSYSFZ")
    private String JSYSFZ ="";

    @JSONField(name = "JSYSQBM")
    private String JSYSQBM ="";

    @Generated(hash = 1543798845)
    public DriverInfo(Long id, String WLJSYKH, Date WLJSYSJ, String JSYXM,
            String JSYXB, String JRSY, String JSYSFZ, String JSYSQBM) {
        this.id = id;
        this.WLJSYKH = WLJSYKH;
        this.WLJSYSJ = WLJSYSJ;
        this.JSYXM = JSYXM;
        this.JSYXB = JSYXB;
        this.JRSY = JRSY;
        this.JSYSFZ = JSYSFZ;
        this.JSYSQBM = JSYSQBM;
    }

    @Generated(hash = 2077275369)
    public DriverInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWLJSYKH() {
        return this.WLJSYKH;
    }

    public void setWLJSYKH(String WLJSYKH) {
        this.WLJSYKH = WLJSYKH;
    }

    public Date getWLJSYSJ() {
        return this.WLJSYSJ;
    }

    public void setWLJSYSJ(Date WLJSYSJ) {
        this.WLJSYSJ = WLJSYSJ;
    }

    public String getJSYXM() {
        return this.JSYXM;
    }

    public void setJSYXM(String JSYXM) {
        this.JSYXM = JSYXM;
    }

    public String getJSYXB() {
        return this.JSYXB;
    }

    public void setJSYXB(String JSYXB) {
        this.JSYXB = JSYXB;
    }

    public String getJRSY() {
        return this.JRSY;
    }

    public void setJRSY(String JRSY) {
        this.JRSY = JRSY;
    }

    public String getJSYSFZ() {
        return this.JSYSFZ;
    }

    public void setJSYSFZ(String JSYSFZ) {
        this.JSYSFZ = JSYSFZ;
    }

    public String getJSYSQBM() {
        return this.JSYSQBM;
    }

    public void setJSYSQBM(String JSYSQBM) {
        this.JSYSQBM = JSYSQBM;
    }

}
