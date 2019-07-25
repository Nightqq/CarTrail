package com.zxdz.car.main.model.domain;

import com.alibaba.fastjson.annotation.JSONField;
import com.zxdz.car.App;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;

/**
 * Created by super on 2017/10/25.
 * 车辆进出记录
 */
@Entity
public class CarTravelRecord {

    @Id
    private Long id;
    @JSONField(name = "LS_ID")
    private int LS_ID;
    @JSONField(name = "ZDJ_ID")
    private String ZDJ_ID;
    @JSONField(name = "QY_ID")
    private int QY_ID;
    @JSONField(name = "RIQI")
    private Date RIQI = new Date();
    @JSONField(name = "ZT")
    private int ZT = 0;
    @JSONField(name = "GLRY")
    private String GLRY = "";
    @JSONField(name = "GLRYSJ")
    private Date GLRYSJ = new Date();
    //领用
    @JSONField(name = "DLGJ_LYKH")
    private String DLGJ_LYKH ="";
    @JSONField(name = "DLGJ_LYSJ")
    private Date DLGJ_LYSJ = new Date();

    //---新增字段
    @JSONField(name = "DLGJ_LYXM")
    private String DLGJ_LYXM;
    @JSONField(name = "DLGJ_LYJH")
    private String DLGJ_LYJH;
    @JSONField(name = "DLGJ_LYBM")
    private String DLGJ_LYBM;

    @JSONField(name = "WLJSYKH")//卡号
    private String WLJSYKH = "";
    @JSONField(name = "WLJSYSJ")//时间
    private Date WLJSYSJ = new Date();
    @JSONField(name = "CLHP")//车辆号牌
    private String CLHP = "";
    @JSONField(name = "CLLX")//车辆类型
    private String CLLX ="";
    @JSONField(name = "JSYXM")//姓名
    private String JSYXM ="";
    @JSONField(name = "JSYXB")//性别
    private String JSYXB ="";
    @JSONField(name = "JRSY")//进入事由
    private String JRSY ="";
    @JSONField(name = "JSYSFZ")//驾驶员身份证
    private String JSYSFZ ="";
    @JSONField(name = "JSYSQBM")//驾驶员所去部门
    private String JSYSQBM ="";

    @JSONField(name = "DEV_NUMBER")//设备编号
    private String DEV_NUMBER ="";

    //安装
    @JSONField(name = "DLGJ_AZKH")
    private String DLGJ_AZKH = "";
    @JSONField(name = "DLGJ_AZSJ")
    private Date DLGJ_AZSJ = new Date();

    //新增字段
    @JSONField(name = "DLGJ_AZXM")
    private String DLGJ_AZXM = "";

    @JSONField(name = "DLGJ_AZJH")
    private String DLGJ_AZJH = "";

    @JSONField(name = "DLGJ_AZBM")
    private String DLGJ_AZBM = "";

    //锁车
    @JSONField(name = "DLGJ_SCKH")
    private String DLGJ_SCKH ="";
    @JSONField(name = "DLGJ_SCSJ")
    private Date DLGJ_SCSJ = new Date();
    @JSONField(name = "SC_ADDRESS")
    private String SC_ADDRESS = "";//后添加字段
    @JSONField(name = "DLGJ_SCXM")
    private String DLGJ_SCXM ="";
    @JSONField(name = "DLGJ_SCJH")
    private String DLGJ_SCJH ="";
    @JSONField(name = "DLGJ_SCBM")
    private String DLGJ_SCBM ="";


    @JSONField(name = "VIDEOAREAID")//装卸区id
    private String VIDEOAREAID ="";

    //拍照时间
    @JSONField(name = "GJPZSJ")
    private Date GJPZSJ = new Date();//后添加字段

    //拍照的图片信息
    @JSONField(name = "GJPZPNG")
    private String GJPZPNG = "";//后添加字段

    //开锁
    @JSONField(name = "DLGJ_KSKH")
    private String DLGJ_KSKH ="";
    @JSONField(name = "DLGJ_KSSJ")
    private Date DLGJ_KSSJ = new Date();
    @JSONField(name = "KS_ADDRESS")
    private String KS_ADDRESS = "";//后添加字段

    @JSONField(name = "DLGJ_KSXM")
    private String DLGJ_KSXM ="";
    @JSONField(name = "DLGJ_KSJH")
    private String DLGJ_KSJH ="";
    @JSONField(name = "DLGJ_KSBM")
    private String DLGJ_KSBM ="";

    //交还
    @JSONField(name = "DLGJ_JHKH")
    private String DLGJ_JHKH = "";
    @JSONField(name = "DLGJ_JHSJ")
    private Date DLGJ_JHSJ = new Date();

    @JSONField(name = "DLGJ_JHXM")
    private String DLGJ_JHXM = "";

    @JSONField(name = "DLGJ_JHJH")
    private String DLGJ_JHJH = "";

    @JSONField(name = "DLGJ_JHBM")
    private String DLGJ_JHBM = "";

    @JSONField(name = "GLY_GHQRSJ")//管理员初始化时间
    private Date GLY_GHQRSJ = new Date();


    //数据交换状态
    @JSONField(name = "SJJHBZ")
    private int SJJHBZ = 0;
    @JSONField(name = "BZ")
    private String BZ = "";

    //图片地址信息
    private String imageUrl;

    @Generated(hash = 75411764)
    public CarTravelRecord() {

    }
    @Generated(hash = 1593475983)
    public CarTravelRecord(Long id, int LS_ID, String ZDJ_ID, int QY_ID, Date RIQI,
            int ZT, String GLRY, Date GLRYSJ, String DLGJ_LYKH, Date DLGJ_LYSJ,
            String DLGJ_LYXM, String DLGJ_LYJH, String DLGJ_LYBM, String WLJSYKH,
            Date WLJSYSJ, String CLHP, String CLLX, String JSYXM, String JSYXB,
            String JRSY, String JSYSFZ, String JSYSQBM, String DEV_NUMBER,
            String DLGJ_AZKH, Date DLGJ_AZSJ, String DLGJ_AZXM, String DLGJ_AZJH,
            String DLGJ_AZBM, String DLGJ_SCKH, Date DLGJ_SCSJ, String SC_ADDRESS,
            String DLGJ_SCXM, String DLGJ_SCJH, String DLGJ_SCBM,
            String VIDEOAREAID, Date GJPZSJ, String GJPZPNG, String DLGJ_KSKH,
            Date DLGJ_KSSJ, String KS_ADDRESS, String DLGJ_KSXM, String DLGJ_KSJH,
            String DLGJ_KSBM, String DLGJ_JHKH, Date DLGJ_JHSJ, String DLGJ_JHXM,
            String DLGJ_JHJH, String DLGJ_JHBM, Date GLY_GHQRSJ, int SJJHBZ,
            String BZ, String imageUrl) {
        this.id = id;
        this.LS_ID = LS_ID;
        this.ZDJ_ID = ZDJ_ID;
        this.QY_ID = QY_ID;
        this.RIQI = RIQI;
        this.ZT = ZT;
        this.GLRY = GLRY;
        this.GLRYSJ = GLRYSJ;
        this.DLGJ_LYKH = DLGJ_LYKH;
        this.DLGJ_LYSJ = DLGJ_LYSJ;
        this.DLGJ_LYXM = DLGJ_LYXM;
        this.DLGJ_LYJH = DLGJ_LYJH;
        this.DLGJ_LYBM = DLGJ_LYBM;
        this.WLJSYKH = WLJSYKH;
        this.WLJSYSJ = WLJSYSJ;
        this.CLHP = CLHP;
        this.CLLX = CLLX;
        this.JSYXM = JSYXM;
        this.JSYXB = JSYXB;
        this.JRSY = JRSY;
        this.JSYSFZ = JSYSFZ;
        this.JSYSQBM = JSYSQBM;
        this.DEV_NUMBER = DEV_NUMBER;
        this.DLGJ_AZKH = DLGJ_AZKH;
        this.DLGJ_AZSJ = DLGJ_AZSJ;
        this.DLGJ_AZXM = DLGJ_AZXM;
        this.DLGJ_AZJH = DLGJ_AZJH;
        this.DLGJ_AZBM = DLGJ_AZBM;
        this.DLGJ_SCKH = DLGJ_SCKH;
        this.DLGJ_SCSJ = DLGJ_SCSJ;
        this.SC_ADDRESS = SC_ADDRESS;
        this.DLGJ_SCXM = DLGJ_SCXM;
        this.DLGJ_SCJH = DLGJ_SCJH;
        this.DLGJ_SCBM = DLGJ_SCBM;
        this.VIDEOAREAID = VIDEOAREAID;
        this.GJPZSJ = GJPZSJ;
        this.GJPZPNG = GJPZPNG;
        this.DLGJ_KSKH = DLGJ_KSKH;
        this.DLGJ_KSSJ = DLGJ_KSSJ;
        this.KS_ADDRESS = KS_ADDRESS;
        this.DLGJ_KSXM = DLGJ_KSXM;
        this.DLGJ_KSJH = DLGJ_KSJH;
        this.DLGJ_KSBM = DLGJ_KSBM;
        this.DLGJ_JHKH = DLGJ_JHKH;
        this.DLGJ_JHSJ = DLGJ_JHSJ;
        this.DLGJ_JHXM = DLGJ_JHXM;
        this.DLGJ_JHJH = DLGJ_JHJH;
        this.DLGJ_JHBM = DLGJ_JHBM;
        this.GLY_GHQRSJ = GLY_GHQRSJ;
        this.SJJHBZ = SJJHBZ;
        this.BZ = BZ;
        this.imageUrl = imageUrl;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getLS_ID() {
        return this.LS_ID;
    }
    public void setLS_ID(int LS_ID) {
        this.LS_ID = LS_ID;
    }
    public String getZDJ_ID() {
        return this.ZDJ_ID;
    }
    public void setZDJ_ID(String ZDJ_ID) {
        this.ZDJ_ID = ZDJ_ID;
    }
    public int getQY_ID() {
        return this.QY_ID;
    }
    public void setQY_ID(int QY_ID) {
        this.QY_ID = QY_ID;
    }
    public Date getRIQI() {
        return this.RIQI;
    }
    public void setRIQI(Date RIQI) {
        this.RIQI = RIQI;
    }
    public int getZT() {
        return this.ZT;
    }
    public void setZT(int ZT) {
        this.ZT = ZT;
    }
    public String getGLRY() {
        return this.GLRY;
    }
    public void setGLRY(String GLRY) {
        this.GLRY = GLRY;
    }
    public Date getGLRYSJ() {
        return this.GLRYSJ;
    }
    public void setGLRYSJ(Date GLRYSJ) {
        this.GLRYSJ = GLRYSJ;
    }
    public String getDLGJ_LYKH() {
        return this.DLGJ_LYKH;
    }
    public void setDLGJ_LYKH(String DLGJ_LYKH) {
        this.DLGJ_LYKH = DLGJ_LYKH;
    }
    public Date getDLGJ_LYSJ() {
        return this.DLGJ_LYSJ;
    }
    public void setDLGJ_LYSJ(Date DLGJ_LYSJ) {
        this.DLGJ_LYSJ = DLGJ_LYSJ;
    }
    public String getWLJSYKH() {
        return this.WLJSYKH;
    }
    public void setWLJSYKH(String WLJSYKH) {
        this.WLJSYKH = WLJSYKH;
    }
    public Date getWLJSYSJ() {
        return this.WLJSYSJ;
    }
    public void setWLJSYSJ(Date WLJSYSJ) {
        this.WLJSYSJ = WLJSYSJ;
    }
    public String getCLHP() {
        return this.CLHP;
    }
    public void setCLHP(String CLHP) {
        this.CLHP = CLHP;
    }
    public String getCLLX() {
        return this.CLLX;
    }
    public void setCLLX(String CLLX) {
        this.CLLX = CLLX;
    }
    public String getJSYXM() {
        return this.JSYXM;
    }
    public void setJSYXM(String JSYXM) {
        this.JSYXM = JSYXM;
    }
    public String getJSYXB() {
        return this.JSYXB;
    }
    public void setJSYXB(String JSYXB) {
        this.JSYXB = JSYXB;
    }
    public String getJRSY() {
        return this.JRSY;
    }
    public void setJRSY(String JRSY) {
        this.JRSY = JRSY;
    }
    public String getDLGJ_AZKH() {
        return this.DLGJ_AZKH;
    }
    public void setDLGJ_AZKH(String DLGJ_AZKH) {
        this.DLGJ_AZKH = DLGJ_AZKH;
    }
    public Date getDLGJ_AZSJ() {
        return this.DLGJ_AZSJ;
    }
    public void setDLGJ_AZSJ(Date DLGJ_AZSJ) {
        this.DLGJ_AZSJ = DLGJ_AZSJ;
    }
    public String getDLGJ_SCKH() {
        return this.DLGJ_SCKH;
    }
    public void setDLGJ_SCKH(String DLGJ_SCKH) {
        this.DLGJ_SCKH = DLGJ_SCKH;
    }
    public Date getDLGJ_SCSJ() {
        return this.DLGJ_SCSJ;
    }
    public void setDLGJ_SCSJ(Date DLGJ_SCSJ) {
        this.DLGJ_SCSJ = DLGJ_SCSJ;
    }
    public String getSC_ADDRESS() {
        return this.SC_ADDRESS;
    }
    public void setSC_ADDRESS(String SC_ADDRESS) {
        this.SC_ADDRESS = SC_ADDRESS;
    }
    public String getDLGJ_KSKH() {
        return this.DLGJ_KSKH;
    }
    public void setDLGJ_KSKH(String DLGJ_KSKH) {
        this.DLGJ_KSKH = DLGJ_KSKH;
    }
    public Date getDLGJ_KSSJ() {
        return this.DLGJ_KSSJ;
    }
    public void setDLGJ_KSSJ(Date DLGJ_KSSJ) {
        this.DLGJ_KSSJ = DLGJ_KSSJ;
    }
    public String getKS_ADDRESS() {
        return this.KS_ADDRESS;
    }
    public void setKS_ADDRESS(String KS_ADDRESS) {
        this.KS_ADDRESS = KS_ADDRESS;
    }
    public String getDLGJ_JHKH() {
        return this.DLGJ_JHKH;
    }
    public void setDLGJ_JHKH(String DLGJ_JHKH) {
        this.DLGJ_JHKH = DLGJ_JHKH;
    }
    public Date getDLGJ_JHSJ() {
        return this.DLGJ_JHSJ;
    }
    public void setDLGJ_JHSJ(Date DLGJ_JHSJ) {
        this.DLGJ_JHSJ = DLGJ_JHSJ;
    }
    public int getSJJHBZ() {
        return this.SJJHBZ;
    }
    public void setSJJHBZ(int SJJHBZ) {
        this.SJJHBZ = SJJHBZ;
    }
    public String getBZ() {
        return this.BZ;
    }
    public void setBZ(String BZ) {
        this.BZ = BZ;
    }
    public String getDLGJ_LYXM() {
        return this.DLGJ_LYXM;
    }
    public void setDLGJ_LYXM(String DLGJ_LYXM) {
        this.DLGJ_LYXM = DLGJ_LYXM;
    }
    public String getDLGJ_LYJH() {
        return this.DLGJ_LYJH;
    }
    public void setDLGJ_LYJH(String DLGJ_LYJH) {
        this.DLGJ_LYJH = DLGJ_LYJH;
    }
    public String getDLGJ_LYBM() {
        return this.DLGJ_LYBM;
    }
    public void setDLGJ_LYBM(String DLGJ_LYBM) {
        this.DLGJ_LYBM = DLGJ_LYBM;
    }
    public String getJSYSFZ() {
        return this.JSYSFZ;
    }
    public void setJSYSFZ(String JSYSFZ) {
        this.JSYSFZ = JSYSFZ;
    }
    public String getJSYSQBM() {
        return this.JSYSQBM;
    }
    public void setJSYSQBM(String JSYSQBM) {
        this.JSYSQBM = JSYSQBM;
    }
    public String getDLGJ_JHXM() {
        return this.DLGJ_JHXM;
    }
    public void setDLGJ_JHXM(String DLGJ_JHXM) {
        this.DLGJ_JHXM = DLGJ_JHXM;
    }
    public String getDLGJ_JHJH() {
        return this.DLGJ_JHJH;
    }
    public void setDLGJ_JHJH(String DLGJ_JHJH) {
        this.DLGJ_JHJH = DLGJ_JHJH;
    }
    public String getDLGJ_JHBM() {
        return this.DLGJ_JHBM;
    }
    public void setDLGJ_JHBM(String DLGJ_JHBM) {
        this.DLGJ_JHBM = DLGJ_JHBM;
    }
    public Date getGJPZSJ() {
        return this.GJPZSJ;
    }
    public void setGJPZSJ(Date GJPZSJ) {
        this.GJPZSJ = GJPZSJ;
    }
    public String getGJPZPNG() {
        return this.GJPZPNG;
    }
    public void setGJPZPNG(String GJPZPNG) {
        this.GJPZPNG = GJPZPNG;
    }
    public String getImageUrl() {
        return this.imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "CarTravelRecord{" +
                "id=" + id +
                ", LS_ID=" + LS_ID +
                ", ZDJ_ID='" + ZDJ_ID + '\'' +
                ", QY_ID=" + QY_ID +
                ", RIQI=" + RIQI +
                ", ZT=" + ZT +
                ", GLRY='" + GLRY + '\'' +
                ", GLRYSJ=" + GLRYSJ +
                ", DLGJ_LYKH='" + DLGJ_LYKH + '\'' +
                ", DLGJ_LYSJ=" + DLGJ_LYSJ +
                ", DLGJ_LYXM='" + DLGJ_LYXM + '\'' +
                ", DLGJ_LYJH='" + DLGJ_LYJH + '\'' +
                ", DLGJ_LYBM='" + DLGJ_LYBM + '\'' +
                ", WLJSYKH='" + WLJSYKH + '\'' +
                ", WLJSYSJ=" + WLJSYSJ +
                ", CLHP='" + CLHP + '\'' +
                ", CLLX='" + CLLX + '\'' +
                ", JSYXM='" + JSYXM + '\'' +
                ", JSYXB='" + JSYXB + '\'' +
                ", JRSY='" + JRSY + '\'' +
                ", JSYSFZ='" + JSYSFZ + '\'' +
                ", JSYSQBM='" + JSYSQBM + '\'' +
                ", DLGJ_AZKH='" + DLGJ_AZKH + '\'' +
                ", DLGJ_AZSJ=" + DLGJ_AZSJ +
                ", DLGJ_SCKH='" + DLGJ_SCKH + '\'' +
                ", DLGJ_SCSJ=" + DLGJ_SCSJ +
                ", SC_ADDRESS='" + SC_ADDRESS + '\'' +
                ", GJPZSJ=" + GJPZSJ +
                ", GJPZPNG='" + GJPZPNG + '\'' +
                ", DLGJ_KSKH='" + DLGJ_KSKH + '\'' +
                ", DLGJ_KSSJ=" + DLGJ_KSSJ +
                ", KS_ADDRESS='" + KS_ADDRESS + '\'' +
                ", DLGJ_JHKH='" + DLGJ_JHKH + '\'' +
                ", DLGJ_JHSJ=" + DLGJ_JHSJ +
                ", DLGJ_JHXM='" + DLGJ_JHXM + '\'' +
                ", DLGJ_JHJH='" + DLGJ_JHJH + '\'' +
                ", DLGJ_JHBM='" + DLGJ_JHBM + '\'' +
                ", SJJHBZ=" + SJJHBZ +
                ", BZ='" + BZ + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
    public String getDLGJ_AZXM() {
        return this.DLGJ_AZXM;
    }
    public void setDLGJ_AZXM(String DLGJ_AZXM) {
        this.DLGJ_AZXM = DLGJ_AZXM;
    }
    public String getDLGJ_AZJH() {
        return this.DLGJ_AZJH;
    }
    public void setDLGJ_AZJH(String DLGJ_AZJH) {
        this.DLGJ_AZJH = DLGJ_AZJH;
    }
    public String getDLGJ_AZBM() {
        return this.DLGJ_AZBM;
    }
    public void setDLGJ_AZBM(String DLGJ_AZBM) {
        this.DLGJ_AZBM = DLGJ_AZBM;
    }
    public String getDLGJ_SCXM() {
        return this.DLGJ_SCXM;
    }
    public void setDLGJ_SCXM(String DLGJ_SCXM) {
        this.DLGJ_SCXM = DLGJ_SCXM;
    }
    public String getDLGJ_SCJH() {
        return this.DLGJ_SCJH;
    }
    public void setDLGJ_SCJH(String DLGJ_SCJH) {
        this.DLGJ_SCJH = DLGJ_SCJH;
    }
    public String getDLGJ_SCBM() {
        return this.DLGJ_SCBM;
    }
    public void setDLGJ_SCBM(String DLGJ_SCBM) {
        this.DLGJ_SCBM = DLGJ_SCBM;
    }
    public String getDLGJ_KSXM() {
        return this.DLGJ_KSXM;
    }
    public void setDLGJ_KSXM(String DLGJ_KSXM) {
        this.DLGJ_KSXM = DLGJ_KSXM;
    }
    public String getDLGJ_KSJH() {
        return this.DLGJ_KSJH;
    }
    public void setDLGJ_KSJH(String DLGJ_KSJH) {
        this.DLGJ_KSJH = DLGJ_KSJH;
    }
    public String getDLGJ_KSBM() {
        return this.DLGJ_KSBM;
    }
    public void setDLGJ_KSBM(String DLGJ_KSBM) {
        this.DLGJ_KSBM = DLGJ_KSBM;
    }
    public Date getGLY_GHQRSJ() {
        return this.GLY_GHQRSJ;
    }
    public void setGLY_GHQRSJ(Date GLY_GHQRSJ) {
        this.GLY_GHQRSJ = GLY_GHQRSJ;
    }
    public String getVIDEOAREAID() {
        return this.VIDEOAREAID;
    }
    public void setVIDEOAREAID(String VIDEOAREAID) {
        this.VIDEOAREAID = VIDEOAREAID;
    }
    public String getDEV_NUMBER() {
        return this.DEV_NUMBER;
    }
    public void setDEV_NUMBER(String DEV_NUMBER) {
        this.DEV_NUMBER = DEV_NUMBER;
    }
  
}
