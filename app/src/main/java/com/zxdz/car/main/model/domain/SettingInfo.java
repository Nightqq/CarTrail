package com.zxdz.car.main.model.domain;

import com.alibaba.fastjson.annotation.JSONField;

import org.greenrobot.greendao.annotation.Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by iflying on 2017/11/6.
 * 系统设置信息
 */

public class SettingInfo implements Serializable {

    private static final long serialVersionUID = -7060210233611464451L;

    @JSONField(name = "ZDJ_ID")
    private String zdjId;
    @JSONField(name = "ZDJMC")
    private String zdjName;
    @JSONField(name = "SIMKH")
    private String simCardNumber;
    @JSONField(name = "YJXLH")
    private String equImel;
    @JSONField(name = "QY_ID")
    private int areaId;
    @JSONField(name = "FWQIP")
    private String serverIp;
    @JSONField(name = "SYZT")
    private String useState;
    @JSONField(name = "BZ")
    private String remark;

    @JSONField(name = "zdglry")
    private List<CardInfo> admins;

    @JSONField(name = "qylj")
    private List<RouteInfo> routes;

    @JSONField(name = "qyxx")
    private List<AreaInfo> areas;

    @JSONField(name = "dlgj")
    private List<PoliceInfoAll> dlgj;

    public List<PoliceInfoAll> getDlgj() {
        return dlgj;
    }

    public void setDlgj(List<PoliceInfoAll> dlgj) {
        this.dlgj = dlgj;
    }

    public List<CardInfo> getAdmins() {
        return admins;
    }

    public void setAdmins(List<CardInfo> admins) {
        this.admins = admins;
    }

    public String getZdjId() {
        return zdjId;
    }

    public void setZdjId(String zdjId) {
        this.zdjId = zdjId;
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

    public List<RouteInfo> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RouteInfo> routes) {
        this.routes = routes;
    }

    public List<AreaInfo> getAreas() {
        return areas;
    }

    public void setAreas(List<AreaInfo> areas) {
        this.areas = areas;
    }
}
