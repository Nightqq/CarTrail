package com.zxdz.car.main.model.domain;

import com.alibaba.fastjson.annotation.JSONField;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * Created by iflying on 2017/11/6.
 * 区域信息
 * 0：代表是，1：否
 */
@Entity
public class AreaInfo implements Serializable {

    private static final long serialVersionUID = -7060210233611444481L;

    @Id
    private Long id;

    @JSONField(name = "QY_ID")
    private String areaId;

    @JSONField(name = "QYMC")
    private String areaName;

    @JSONField(name = "QYFW")
    private String areaRange;

    @JSONField(name = "QYGJ")
    private String areaRoute;

    @JSONField(name = "QYZB")
    private String areaPoint;
    /**
     * 管理员是否需要打卡
     */
    @JSONField(name = "GLRYBZ")
    private int adminPunchIn;

    /**
     * 带领干警是否需要刷卡
     */
    @JSONField(name = "DLGJBZ")
    private int policePunChIn;

    /**
     * 驾驶员是否需要刷卡
     */
    @JSONField(name = "WLJSYBZ")
    private int driverPunchIn;

    @JSONField(name = "BZ")
    private String remark;

    private String zdjId;//终端机ID



    @Generated(hash = 177146206)
    public AreaInfo() {
    }

    @Generated(hash = 379063762)
    public AreaInfo(Long id, String areaId, String areaName, String areaRange,
            String areaRoute, String areaPoint, int adminPunchIn, int policePunChIn,
            int driverPunchIn, String remark, String zdjId) {
        this.id = id;
        this.areaId = areaId;
        this.areaName = areaName;
        this.areaRange = areaRange;
        this.areaRoute = areaRoute;
        this.areaPoint = areaPoint;
        this.adminPunchIn = adminPunchIn;
        this.policePunChIn = policePunChIn;
        this.driverPunchIn = driverPunchIn;
        this.remark = remark;
        this.zdjId = zdjId;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAreaId() {
        return this.areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return this.areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaRange() {
        return this.areaRange;
    }

    public void setAreaRange(String areaRange) {
        this.areaRange = areaRange;
    }

    public String getAreaRoute() {
        return this.areaRoute;
    }

    public void setAreaRoute(String areaRoute) {
        this.areaRoute = areaRoute;
    }

    public String getAreaPoint() {
        return this.areaPoint;
    }

    public void setAreaPoint(String areaPoint) {
        this.areaPoint = areaPoint;
    }

    public int getAdminPunchIn() {
        return this.adminPunchIn;
    }

    public void setAdminPunchIn(int adminPunchIn) {
        this.adminPunchIn = adminPunchIn;
    }

    public int getPolicePunChIn() {
        return this.policePunChIn;
    }

    public void setPolicePunChIn(int policePunChIn) {
        this.policePunChIn = policePunChIn;
    }

    public int getDriverPunchIn() {
        return this.driverPunchIn;
    }

    public void setDriverPunchIn(int driverPunchIn) {
        this.driverPunchIn = driverPunchIn;
    }

    public String getRemark() {
        return this.remark;
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
