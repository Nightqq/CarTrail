package com.zxdz.car.main.model.domain;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Lenovo on 2017/10/31.
 */
@Entity
public class ChangePolice {
    @Id
    private int change_id;
    private String change_terminal;
    private String change_time;
    private String change_admin_num;
    private String change_police_old;
    private String change_police_new;
    @Generated(hash = 1921730828)
    public ChangePolice(int change_id, String change_terminal, String change_time,
            String change_admin_num, String change_police_old,
            String change_police_new) {
        this.change_id = change_id;
        this.change_terminal = change_terminal;
        this.change_time = change_time;
        this.change_admin_num = change_admin_num;
        this.change_police_old = change_police_old;
        this.change_police_new = change_police_new;
    }
    @Generated(hash = 783528636)
    public ChangePolice() {
    }
    public int getChange_id() {
        return this.change_id;
    }
    public void setChange_id(int change_id) {
        this.change_id = change_id;
    }
    public String getChange_terminal() {
        return this.change_terminal;
    }
    public void setChange_terminal(String change_terminal) {
        this.change_terminal = change_terminal;
    }
    public String getChange_time() {
        return this.change_time;
    }
    public void setChange_time(String change_time) {
        this.change_time = change_time;
    }
    public String getChange_admin_num() {
        return this.change_admin_num;
    }
    public void setChange_admin_num(String change_admin_num) {
        this.change_admin_num = change_admin_num;
    }
    public String getChange_police_old() {
        return this.change_police_old;
    }
    public void setChange_police_old(String change_police_old) {
        this.change_police_old = change_police_old;
    }
    public String getChange_police_new() {
        return this.change_police_new;
    }
    public void setChange_police_new(String change_police_new) {
        this.change_police_new = change_police_new;
    }
}
