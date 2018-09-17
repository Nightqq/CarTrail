package com.zxdz.car.main.model.domain;

import com.alibaba.fastjson.annotation.JSONField;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by iflying on 2017/11/8.
 * 路径信息
 */
@Entity
public class RouteInfo implements Serializable {

    private static final long serialVersionUID = -7360210233611464481L;

    @Id
    public Long id;

    @JSONField(name = "LJ_ID")
    private String routeId;

    @JSONField(name = "QY_ID")
    private String areaId;

    @JSONField(name = "LJBJ")
    private String routeRange;

    @Generated(hash = 696165541)
    public RouteInfo(Long id, String routeId, String areaId, String routeRange) {
        this.id = id;
        this.routeId = routeId;
        this.areaId = areaId;
        this.routeRange = routeRange;
    }

    @Generated(hash = 1284578867)
    public RouteInfo() {
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getRouteRange() {
        return routeRange;
    }

    public void setRouteRange(String routeRange) {
        this.routeRange = routeRange;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
