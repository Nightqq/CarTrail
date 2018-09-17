package com.zxdz.car.main.model.domain;

import com.alibaba.fastjson.annotation.JSONField;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.util.Date;

/**
 * 车辆轨迹坐标点
 */

@Entity
public class TrailPointInfo implements Serializable {

    private static final long serialVersionUID = -7060210233611464481L;

    @Id
    private Long id;

    @JSONField(name = "GJ_ID")
    private int GJ_ID;//轨迹ID

    @JSONField(name = "LS_ID")
    private int LS_ID;//车辆轨迹流水ID

    @JSONField(name = "GJZBJD")
    private double GJZBJD;//纬度

    @JSONField(name = "GJZBWD")
    private double GJZBWD;//经度

    @JSONField(name = "GJSJ")
    private Date GJSJ;//获取轨迹点时的时间

    @JSONField(name = "SJJHBZ")
    private int SJJHBZ;//数据交换标志

    private String result;

    @Generated(hash = 1037936049)
    public TrailPointInfo(Long id, int GJ_ID, int LS_ID, double GJZBJD,
            double GJZBWD, Date GJSJ, int SJJHBZ, String result) {
        this.id = id;
        this.GJ_ID = GJ_ID;
        this.LS_ID = LS_ID;
        this.GJZBJD = GJZBJD;
        this.GJZBWD = GJZBWD;
        this.GJSJ = GJSJ;
        this.SJJHBZ = SJJHBZ;
        this.result = result;
    }

    @Generated(hash = 483304035)
    public TrailPointInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getGJ_ID() {
        return GJ_ID;
    }

    public void setGJ_ID(int GJ_ID) {
        this.GJ_ID = GJ_ID;
    }

    public int getLS_ID() {
        return LS_ID;
    }

    public void setLS_ID(int LS_ID) {
        this.LS_ID = LS_ID;
    }

    public double getGJZBJD() {
        return GJZBJD;
    }

    public void setGJZBJD(double GJZBJD) {
        this.GJZBJD = GJZBJD;
    }

    public double getGJZBWD() {
        return GJZBWD;
    }

    public void setGJZBWD(double GJZBWD) {
        this.GJZBWD = GJZBWD;
    }

    public Date getGJSJ() {
        return GJSJ;
    }

    public void setGJSJ(Date GJSJ) {
        this.GJSJ = GJSJ;
    }

    public int getSJJHBZ() {
        return SJJHBZ;
    }

    public void setSJJHBZ(int SJJHBZ) {
        this.SJJHBZ = SJJHBZ;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    @Override
    public String toString() {
        return "TrailPointInfo{" +
                "id=" + id +
                ", GJ_ID=" + GJ_ID +
                ", LS_ID=" + LS_ID +
                ", GJZBJD=" + GJZBJD +
                ", GJZBWD=" + GJZBWD +
                ", GJSJ=" + GJSJ +
                ", SJJHBZ=" + SJJHBZ +
                ", result='" + result + '\'' +
                '}';
    }
}
