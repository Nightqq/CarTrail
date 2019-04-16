package com.zxdz.car.main.model.domain;

import com.alibaba.fastjson.annotation.JSONField;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by iflying on 2017/11/20.
 * 驾驶员信息
 */


public class DriverInfo implements Serializable {

    private static final long serialVersionUID = -7060210233212444481L;


    @JSONField(name = "sp_id")
    private String sp_id = "";

    @JSONField(name = "外来人员姓名")
    private String name="";

    @JSONField(name = "前往监区")
    private String prison_area ="";

    @JSONField(name = "进监事由")
    private String in_reason ="";

    @JSONField(name = "dl_emp_id")
    private String dl_emp_id ="";

    @JSONField(name = "带领民警")
    private String person_name ="";

    @JSONField(name = "带领民警警号")
    private String prison_JH ="";

    @JSONField(name = "民警部门")
    private String prison_BM ="";
    @JSONField(name = "采集时间")
    private String CJ_time ="";

    @JSONField(name = "审批时间")
    private String XP_time ="";
    @JSONField(name = "车辆类型")
    private String car_type ="";
    @JSONField(name = "车牌号码")
    private String car_number ="";


    public String getSp_id() {
        return this.sp_id;
    }

    public void setSp_id(String sp_id) {
        this.sp_id = sp_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrison_area() {
        return this.prison_area;
    }

    public void setPrison_area(String prison_area) {
        this.prison_area = prison_area;
    }

    public String getIn_reason() {
        return this.in_reason;
    }

    public void setIn_reason(String in_reason) {
        this.in_reason = in_reason;
    }

    public String getDl_emp_id() {
        return this.dl_emp_id;
    }

    public void setDl_emp_id(String dl_emp_id) {
        this.dl_emp_id = dl_emp_id;
    }

    public String getPerson_name() {
        return this.person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getPrison_JH() {
        return this.prison_JH;
    }

    public void setPrison_JH(String prison_JH) {
        this.prison_JH = prison_JH;
    }

    public String getPrison_BM() {
        return this.prison_BM;
    }

    public void setPrison_BM(String prison_BM) {
        this.prison_BM = prison_BM;
    }

    public String getCJ_time() {
        return this.CJ_time;
    }

    public void setCJ_time(String CJ_time) {
        this.CJ_time = CJ_time;
    }

    public String getXP_time() {
        return this.XP_time;
    }

    public void setXP_time(String XP_time) {
        this.XP_time = XP_time;
    }

    public String getCar_type() {
        return this.car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public String getCar_number() {
        return this.car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }


    @Override
    public String toString() {
        return "DriverInfo{" +
                ", sp_id='" + sp_id + '\'' +
                ", name='" + name + '\'' +
                ", prison_area='" + prison_area + '\'' +
                ", in_reason='" + in_reason + '\'' +
                ", dl_emp_id='" + dl_emp_id + '\'' +
                ", person_name='" + person_name + '\'' +
                ", prison_JH='" + prison_JH + '\'' +
                ", prison_BM='" + prison_BM + '\'' +
                ", CJ_time='" + CJ_time + '\'' +
                ", XP_time='" + XP_time + '\'' +
                ", car_type='" + car_type + '\'' +
                ", car_number='" + car_number + '\'' +
                '}';
    }
}
