package com.zxdz.car.main.model.domain;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Lenovo on 2017/10/31.
 */
@Entity
public class ServerIP {
    @Id
    private Long id;
    private String wifi_name;
    private String ip;
    private String dk;

    private String wifi_name_2;
    private String ip_2;
    private String dk_2;


    
    private String personID;
    private String personfactory;

    @Generated(hash = 1259737333)
    public ServerIP(Long id, String wifi_name, String ip, String dk,
            String wifi_name_2, String ip_2, String dk_2, String personID,
            String personfactory) {
        this.id = id;
        this.wifi_name = wifi_name;
        this.ip = ip;
        this.dk = dk;
        this.wifi_name_2 = wifi_name_2;
        this.ip_2 = ip_2;
        this.dk_2 = dk_2;
        this.personID = personID;
        this.personfactory = personfactory;
    }
    @Generated(hash = 1306829204)
    public ServerIP() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getIp() {
        return this.ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getPersonID() {
        return this.personID;
    }
    public void setPersonID(String personID) {
        this.personID = personID;
    }
    public String getPersonfactory() {
        return this.personfactory;
    }
    public void setPersonfactory(String personfactory) {
        this.personfactory = personfactory;
    }
    public String getWifi_name() {
        return this.wifi_name;
    }
    public void setWifi_name(String wifi_name) {
        this.wifi_name = wifi_name;
    }
    public String getDk() {
        return this.dk;
    }
    public void setDk(String dk) {
        this.dk = dk;
    }
    public String getWifi_name_2() {
        return this.wifi_name_2;
    }
    public void setWifi_name_2(String wifi_name_2) {
        this.wifi_name_2 = wifi_name_2;
    }
    public String getIp_2() {
        return this.ip_2;
    }
    public void setIp_2(String ip_2) {
        this.ip_2 = ip_2;
    }
    public String getDk_2() {
        return this.dk_2;
    }
    public void setDk_2(String dk_2) {
        this.dk_2 = dk_2;
    }
}
