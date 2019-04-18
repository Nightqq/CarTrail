package com.zxdz.car.main.model.domain;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

public class UnloadAreaAllInfo implements Serializable {

    private static final long serialVersionUID = -7060210233611464451L;

    @JSONField(name = "dlgj")
    private List<UnloadAreaInfo> dlgj;


    public List<UnloadAreaInfo> getDlgj() {
        return dlgj;
    }

    public void setDlgj(List<UnloadAreaInfo> dlgj) {
        this.dlgj = dlgj;
    }

    @Override
    public String toString() {
        return "UnloadAreaAllInfo{" +
                "dlgj=" + dlgj +
                '}';
    }
}
