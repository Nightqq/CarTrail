package com.zxdz.car.main.model.domain;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Lenovo on 2017/10/31.
 */
@Entity
public class AdminCard {
    @Id
    private String admin_num;
    private String admin_name;
    private String admin_terminal_num;
    @Generated(hash = 678636133)
    public AdminCard(String admin_num, String admin_name,
            String admin_terminal_num) {
        this.admin_num = admin_num;
        this.admin_name = admin_name;
        this.admin_terminal_num = admin_terminal_num;
    }
    @Generated(hash = 162772860)
    public AdminCard() {
    }
    public String getAdmin_num() {
        return this.admin_num;
    }
    public void setAdmin_num(String admin_num) {
        this.admin_num = admin_num;
    }
    public String getAdmin_name() {
        return this.admin_name;
    }
    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }
    public String getAdmin_terminal_num() {
        return this.admin_terminal_num;
    }
    public void setAdmin_terminal_num(String admin_terminal_num) {
        this.admin_terminal_num = admin_terminal_num;
    }
}
