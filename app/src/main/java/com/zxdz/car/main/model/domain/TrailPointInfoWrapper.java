package com.zxdz.car.main.model.domain;

import java.util.List;

/**
 * Created by super on 2017/11/14.
 */

public class TrailPointInfoWrapper {

    private String gid;

    private List<TrailPointInfo> points;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public List<TrailPointInfo> getPoints() {
        return points;
    }

    public void setPoints(List<TrailPointInfo> points) {
        this.points = points;
    }


}
