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
    private String ip;
    private String personID;
    private String personfactory;

    @Generated(hash = 373955404)
    public ServerIP(Long id, String ip, String personID, String personfactory) {
        this.id = id;
        this.ip = ip;
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
}
