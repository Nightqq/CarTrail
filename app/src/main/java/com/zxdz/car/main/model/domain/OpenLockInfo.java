package com.zxdz.car.main.model.domain;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * Created by Administrator on 2019/4/16.
 */

public class OpenLockInfo implements Serializable {

    @JSONField(name = "IP")
    private String IP = "";

    @JSONField(name = "port")
    private String port = "";

    public String getIP() {
        return IP;
    }

    public String getPort() {
        return port;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public void setPort(String port) {
        this.port = port;
    }
}
