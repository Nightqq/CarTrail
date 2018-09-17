package com.zxdz.car.main.model.domain;

import com.alibaba.fastjson.annotation.JSONField;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Administrator on 2018\5\23 0023.
 */
@Entity
public class PictureInfo {
    @Id
    private Long id;

    @JSONField(name = "LS_ID")
    private int lsId;

    @JSONField(name = "GJPZSJ")
    private Date gjpzSJ = new Date();

    @JSONField(name = "GJPZPNG")
    private String picture;

    @Generated(hash = 684319419)
    public PictureInfo(Long id, int lsId, Date gjpzSJ, String picture) {
        this.id = id;
        this.lsId = lsId;
        this.gjpzSJ = gjpzSJ;
        this.picture = picture;
    }

    @Generated(hash = 671166560)
    public PictureInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getLsId() {
        return this.lsId;
    }

    public void setLsId(int lsId) {
        this.lsId = lsId;
    }

    public Date getGjpzSJ() {
        return this.gjpzSJ;
    }

    public void setGjpzSJ(Date gjpzSJ) {
        this.gjpzSJ = gjpzSJ;
    }

    public String getPicture() {
        return this.picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    @Override
    public String toString() {
        return "PictureInfo{" +
                "id=" + id +
                ", lsId=" + lsId +
                ", gjpzSJ=" + gjpzSJ +
                ", picture='" + picture + '\'' +
                '}';
    }
}
