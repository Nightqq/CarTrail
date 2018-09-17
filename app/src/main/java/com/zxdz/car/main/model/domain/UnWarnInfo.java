package com.zxdz.car.main.model.domain;

import com.alibaba.fastjson.annotation.JSONField;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;


/**
 * Created by Administrator on 2018\5\15 0015.
 */
@Entity
public class UnWarnInfo implements Serializable {
    private static final  long serialVersionUID =-7060210213611464481L;

    @Id
    private Long id;
    
    @JSONField(name = "BJ_ID")
    private int wid;//报警ID

    //是否可以上传的标志
    //取消报警为先存储本地，取消时直接上传
    private boolean flag;

    @Generated(hash = 574118949)
    public UnWarnInfo(Long id, int wid, boolean flag) {
        this.id = id;
        this.wid = wid;
        this.flag = flag;
    }

    @Generated(hash = 696760368)
    public UnWarnInfo() {
    }

    public int getWid() {
        return this.wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean getFlag() {
        return this.flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
