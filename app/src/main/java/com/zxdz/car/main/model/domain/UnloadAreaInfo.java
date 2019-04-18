package com.zxdz.car.main.model.domain;

import com.alibaba.fastjson.annotation.JSONField;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class UnloadAreaInfo {
    @Id
    private Long id;
    @JSONField(name = "name")
    private String area_name="";
    @JSONField(name = "id")
    private String area_id="";
    @Generated(hash = 302236269)
    public UnloadAreaInfo(Long id, String area_name, String area_id) {
        this.id = id;
        this.area_name = area_name;
        this.area_id = area_id;
    }
    @Generated(hash = 1849640813)
    public UnloadAreaInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getArea_name() {
        return this.area_name;
    }
    public void setArea_name(String area_name) {
        this.area_name = area_name;
    }
    public String getArea_id() {
        return this.area_id;
    }
    public void setArea_id(String area_id) {
        this.area_id = area_id;
    }

    @Override
    public String toString() {
        return "UnloadAreaInfo{" +
                "id=" + id +
                ", area_name='" + area_name + '\'' +
                ", area_id='" + area_id + '\'' +
                '}';
    }
}
