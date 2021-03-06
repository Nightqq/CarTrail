package com.zxdz.car.base.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.zxdz.car.main.model.domain.CarTravelRecord;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CAR_TRAVEL_RECORD".
*/
public class CarTravelRecordDao extends AbstractDao<CarTravelRecord, Long> {

    public static final String TABLENAME = "CAR_TRAVEL_RECORD";

    /**
     * Properties of entity CarTravelRecord.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property LS_ID = new Property(1, int.class, "LS_ID", false, "LS__ID");
        public final static Property ZDJ_ID = new Property(2, String.class, "ZDJ_ID", false, "ZDJ__ID");
        public final static Property QY_ID = new Property(3, int.class, "QY_ID", false, "QY__ID");
        public final static Property RIQI = new Property(4, java.util.Date.class, "RIQI", false, "RIQI");
        public final static Property ZT = new Property(5, int.class, "ZT", false, "ZT");
        public final static Property GLRY = new Property(6, String.class, "GLRY", false, "GLRY");
        public final static Property GLRYSJ = new Property(7, java.util.Date.class, "GLRYSJ", false, "GLRYSJ");
        public final static Property DLGJ_LYKH = new Property(8, String.class, "DLGJ_LYKH", false, "DLGJ__LYKH");
        public final static Property DLGJ_LYSJ = new Property(9, java.util.Date.class, "DLGJ_LYSJ", false, "DLGJ__LYSJ");
        public final static Property DLGJ_LYXM = new Property(10, String.class, "DLGJ_LYXM", false, "DLGJ__LYXM");
        public final static Property DLGJ_LYJH = new Property(11, String.class, "DLGJ_LYJH", false, "DLGJ__LYJH");
        public final static Property DLGJ_LYBM = new Property(12, String.class, "DLGJ_LYBM", false, "DLGJ__LYBM");
        public final static Property WLJSYKH = new Property(13, String.class, "WLJSYKH", false, "WLJSYKH");
        public final static Property WLJSYSJ = new Property(14, java.util.Date.class, "WLJSYSJ", false, "WLJSYSJ");
        public final static Property CLHP = new Property(15, String.class, "CLHP", false, "CLHP");
        public final static Property CLLX = new Property(16, String.class, "CLLX", false, "CLLX");
        public final static Property JSYXM = new Property(17, String.class, "JSYXM", false, "JSYXM");
        public final static Property JSYXB = new Property(18, String.class, "JSYXB", false, "JSYXB");
        public final static Property JRSY = new Property(19, String.class, "JRSY", false, "JRSY");
        public final static Property JSYSFZ = new Property(20, String.class, "JSYSFZ", false, "JSYSFZ");
        public final static Property JSYSQBM = new Property(21, String.class, "JSYSQBM", false, "JSYSQBM");
        public final static Property DEV_NUMBER = new Property(22, String.class, "DEV_NUMBER", false, "DEV__NUMBER");
        public final static Property DLGJ_AZKH = new Property(23, String.class, "DLGJ_AZKH", false, "DLGJ__AZKH");
        public final static Property DLGJ_AZSJ = new Property(24, java.util.Date.class, "DLGJ_AZSJ", false, "DLGJ__AZSJ");
        public final static Property DLGJ_AZXM = new Property(25, String.class, "DLGJ_AZXM", false, "DLGJ__AZXM");
        public final static Property DLGJ_AZJH = new Property(26, String.class, "DLGJ_AZJH", false, "DLGJ__AZJH");
        public final static Property DLGJ_AZBM = new Property(27, String.class, "DLGJ_AZBM", false, "DLGJ__AZBM");
        public final static Property DLGJ_SCKH = new Property(28, String.class, "DLGJ_SCKH", false, "DLGJ__SCKH");
        public final static Property DLGJ_SCSJ = new Property(29, java.util.Date.class, "DLGJ_SCSJ", false, "DLGJ__SCSJ");
        public final static Property SC_ADDRESS = new Property(30, String.class, "SC_ADDRESS", false, "SC__ADDRESS");
        public final static Property DLGJ_SCXM = new Property(31, String.class, "DLGJ_SCXM", false, "DLGJ__SCXM");
        public final static Property DLGJ_SCJH = new Property(32, String.class, "DLGJ_SCJH", false, "DLGJ__SCJH");
        public final static Property DLGJ_SCBM = new Property(33, String.class, "DLGJ_SCBM", false, "DLGJ__SCBM");
        public final static Property VIDEOAREAID = new Property(34, String.class, "VIDEOAREAID", false, "VIDEOAREAID");
        public final static Property GJPZSJ = new Property(35, java.util.Date.class, "GJPZSJ", false, "GJPZSJ");
        public final static Property GJPZPNG = new Property(36, String.class, "GJPZPNG", false, "GJPZPNG");
        public final static Property DLGJ_KSKH = new Property(37, String.class, "DLGJ_KSKH", false, "DLGJ__KSKH");
        public final static Property DLGJ_KSSJ = new Property(38, java.util.Date.class, "DLGJ_KSSJ", false, "DLGJ__KSSJ");
        public final static Property KS_ADDRESS = new Property(39, String.class, "KS_ADDRESS", false, "KS__ADDRESS");
        public final static Property DLGJ_KSXM = new Property(40, String.class, "DLGJ_KSXM", false, "DLGJ__KSXM");
        public final static Property DLGJ_KSJH = new Property(41, String.class, "DLGJ_KSJH", false, "DLGJ__KSJH");
        public final static Property DLGJ_KSBM = new Property(42, String.class, "DLGJ_KSBM", false, "DLGJ__KSBM");
        public final static Property DLGJ_JHKH = new Property(43, String.class, "DLGJ_JHKH", false, "DLGJ__JHKH");
        public final static Property DLGJ_JHSJ = new Property(44, java.util.Date.class, "DLGJ_JHSJ", false, "DLGJ__JHSJ");
        public final static Property DLGJ_JHXM = new Property(45, String.class, "DLGJ_JHXM", false, "DLGJ__JHXM");
        public final static Property DLGJ_JHJH = new Property(46, String.class, "DLGJ_JHJH", false, "DLGJ__JHJH");
        public final static Property DLGJ_JHBM = new Property(47, String.class, "DLGJ_JHBM", false, "DLGJ__JHBM");
        public final static Property GLY_GHQRSJ = new Property(48, java.util.Date.class, "GLY_GHQRSJ", false, "GLY__GHQRSJ");
        public final static Property SJJHBZ = new Property(49, int.class, "SJJHBZ", false, "SJJHBZ");
        public final static Property BZ = new Property(50, String.class, "BZ", false, "BZ");
        public final static Property ImageUrl = new Property(51, String.class, "imageUrl", false, "IMAGE_URL");
    }


    public CarTravelRecordDao(DaoConfig config) {
        super(config);
    }
    
    public CarTravelRecordDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CAR_TRAVEL_RECORD\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"LS__ID\" INTEGER NOT NULL ," + // 1: LS_ID
                "\"ZDJ__ID\" TEXT," + // 2: ZDJ_ID
                "\"QY__ID\" INTEGER NOT NULL ," + // 3: QY_ID
                "\"RIQI\" INTEGER," + // 4: RIQI
                "\"ZT\" INTEGER NOT NULL ," + // 5: ZT
                "\"GLRY\" TEXT," + // 6: GLRY
                "\"GLRYSJ\" INTEGER," + // 7: GLRYSJ
                "\"DLGJ__LYKH\" TEXT," + // 8: DLGJ_LYKH
                "\"DLGJ__LYSJ\" INTEGER," + // 9: DLGJ_LYSJ
                "\"DLGJ__LYXM\" TEXT," + // 10: DLGJ_LYXM
                "\"DLGJ__LYJH\" TEXT," + // 11: DLGJ_LYJH
                "\"DLGJ__LYBM\" TEXT," + // 12: DLGJ_LYBM
                "\"WLJSYKH\" TEXT," + // 13: WLJSYKH
                "\"WLJSYSJ\" INTEGER," + // 14: WLJSYSJ
                "\"CLHP\" TEXT," + // 15: CLHP
                "\"CLLX\" TEXT," + // 16: CLLX
                "\"JSYXM\" TEXT," + // 17: JSYXM
                "\"JSYXB\" TEXT," + // 18: JSYXB
                "\"JRSY\" TEXT," + // 19: JRSY
                "\"JSYSFZ\" TEXT," + // 20: JSYSFZ
                "\"JSYSQBM\" TEXT," + // 21: JSYSQBM
                "\"DEV__NUMBER\" TEXT," + // 22: DEV_NUMBER
                "\"DLGJ__AZKH\" TEXT," + // 23: DLGJ_AZKH
                "\"DLGJ__AZSJ\" INTEGER," + // 24: DLGJ_AZSJ
                "\"DLGJ__AZXM\" TEXT," + // 25: DLGJ_AZXM
                "\"DLGJ__AZJH\" TEXT," + // 26: DLGJ_AZJH
                "\"DLGJ__AZBM\" TEXT," + // 27: DLGJ_AZBM
                "\"DLGJ__SCKH\" TEXT," + // 28: DLGJ_SCKH
                "\"DLGJ__SCSJ\" INTEGER," + // 29: DLGJ_SCSJ
                "\"SC__ADDRESS\" TEXT," + // 30: SC_ADDRESS
                "\"DLGJ__SCXM\" TEXT," + // 31: DLGJ_SCXM
                "\"DLGJ__SCJH\" TEXT," + // 32: DLGJ_SCJH
                "\"DLGJ__SCBM\" TEXT," + // 33: DLGJ_SCBM
                "\"VIDEOAREAID\" TEXT," + // 34: VIDEOAREAID
                "\"GJPZSJ\" INTEGER," + // 35: GJPZSJ
                "\"GJPZPNG\" TEXT," + // 36: GJPZPNG
                "\"DLGJ__KSKH\" TEXT," + // 37: DLGJ_KSKH
                "\"DLGJ__KSSJ\" INTEGER," + // 38: DLGJ_KSSJ
                "\"KS__ADDRESS\" TEXT," + // 39: KS_ADDRESS
                "\"DLGJ__KSXM\" TEXT," + // 40: DLGJ_KSXM
                "\"DLGJ__KSJH\" TEXT," + // 41: DLGJ_KSJH
                "\"DLGJ__KSBM\" TEXT," + // 42: DLGJ_KSBM
                "\"DLGJ__JHKH\" TEXT," + // 43: DLGJ_JHKH
                "\"DLGJ__JHSJ\" INTEGER," + // 44: DLGJ_JHSJ
                "\"DLGJ__JHXM\" TEXT," + // 45: DLGJ_JHXM
                "\"DLGJ__JHJH\" TEXT," + // 46: DLGJ_JHJH
                "\"DLGJ__JHBM\" TEXT," + // 47: DLGJ_JHBM
                "\"GLY__GHQRSJ\" INTEGER," + // 48: GLY_GHQRSJ
                "\"SJJHBZ\" INTEGER NOT NULL ," + // 49: SJJHBZ
                "\"BZ\" TEXT," + // 50: BZ
                "\"IMAGE_URL\" TEXT);"); // 51: imageUrl
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CAR_TRAVEL_RECORD\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, CarTravelRecord entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getLS_ID());
 
        String ZDJ_ID = entity.getZDJ_ID();
        if (ZDJ_ID != null) {
            stmt.bindString(3, ZDJ_ID);
        }
        stmt.bindLong(4, entity.getQY_ID());
 
        java.util.Date RIQI = entity.getRIQI();
        if (RIQI != null) {
            stmt.bindLong(5, RIQI.getTime());
        }
        stmt.bindLong(6, entity.getZT());
 
        String GLRY = entity.getGLRY();
        if (GLRY != null) {
            stmt.bindString(7, GLRY);
        }
 
        java.util.Date GLRYSJ = entity.getGLRYSJ();
        if (GLRYSJ != null) {
            stmt.bindLong(8, GLRYSJ.getTime());
        }
 
        String DLGJ_LYKH = entity.getDLGJ_LYKH();
        if (DLGJ_LYKH != null) {
            stmt.bindString(9, DLGJ_LYKH);
        }
 
        java.util.Date DLGJ_LYSJ = entity.getDLGJ_LYSJ();
        if (DLGJ_LYSJ != null) {
            stmt.bindLong(10, DLGJ_LYSJ.getTime());
        }
 
        String DLGJ_LYXM = entity.getDLGJ_LYXM();
        if (DLGJ_LYXM != null) {
            stmt.bindString(11, DLGJ_LYXM);
        }
 
        String DLGJ_LYJH = entity.getDLGJ_LYJH();
        if (DLGJ_LYJH != null) {
            stmt.bindString(12, DLGJ_LYJH);
        }
 
        String DLGJ_LYBM = entity.getDLGJ_LYBM();
        if (DLGJ_LYBM != null) {
            stmt.bindString(13, DLGJ_LYBM);
        }
 
        String WLJSYKH = entity.getWLJSYKH();
        if (WLJSYKH != null) {
            stmt.bindString(14, WLJSYKH);
        }
 
        java.util.Date WLJSYSJ = entity.getWLJSYSJ();
        if (WLJSYSJ != null) {
            stmt.bindLong(15, WLJSYSJ.getTime());
        }
 
        String CLHP = entity.getCLHP();
        if (CLHP != null) {
            stmt.bindString(16, CLHP);
        }
 
        String CLLX = entity.getCLLX();
        if (CLLX != null) {
            stmt.bindString(17, CLLX);
        }
 
        String JSYXM = entity.getJSYXM();
        if (JSYXM != null) {
            stmt.bindString(18, JSYXM);
        }
 
        String JSYXB = entity.getJSYXB();
        if (JSYXB != null) {
            stmt.bindString(19, JSYXB);
        }
 
        String JRSY = entity.getJRSY();
        if (JRSY != null) {
            stmt.bindString(20, JRSY);
        }
 
        String JSYSFZ = entity.getJSYSFZ();
        if (JSYSFZ != null) {
            stmt.bindString(21, JSYSFZ);
        }
 
        String JSYSQBM = entity.getJSYSQBM();
        if (JSYSQBM != null) {
            stmt.bindString(22, JSYSQBM);
        }
 
        String DEV_NUMBER = entity.getDEV_NUMBER();
        if (DEV_NUMBER != null) {
            stmt.bindString(23, DEV_NUMBER);
        }
 
        String DLGJ_AZKH = entity.getDLGJ_AZKH();
        if (DLGJ_AZKH != null) {
            stmt.bindString(24, DLGJ_AZKH);
        }
 
        java.util.Date DLGJ_AZSJ = entity.getDLGJ_AZSJ();
        if (DLGJ_AZSJ != null) {
            stmt.bindLong(25, DLGJ_AZSJ.getTime());
        }
 
        String DLGJ_AZXM = entity.getDLGJ_AZXM();
        if (DLGJ_AZXM != null) {
            stmt.bindString(26, DLGJ_AZXM);
        }
 
        String DLGJ_AZJH = entity.getDLGJ_AZJH();
        if (DLGJ_AZJH != null) {
            stmt.bindString(27, DLGJ_AZJH);
        }
 
        String DLGJ_AZBM = entity.getDLGJ_AZBM();
        if (DLGJ_AZBM != null) {
            stmt.bindString(28, DLGJ_AZBM);
        }
 
        String DLGJ_SCKH = entity.getDLGJ_SCKH();
        if (DLGJ_SCKH != null) {
            stmt.bindString(29, DLGJ_SCKH);
        }
 
        java.util.Date DLGJ_SCSJ = entity.getDLGJ_SCSJ();
        if (DLGJ_SCSJ != null) {
            stmt.bindLong(30, DLGJ_SCSJ.getTime());
        }
 
        String SC_ADDRESS = entity.getSC_ADDRESS();
        if (SC_ADDRESS != null) {
            stmt.bindString(31, SC_ADDRESS);
        }
 
        String DLGJ_SCXM = entity.getDLGJ_SCXM();
        if (DLGJ_SCXM != null) {
            stmt.bindString(32, DLGJ_SCXM);
        }
 
        String DLGJ_SCJH = entity.getDLGJ_SCJH();
        if (DLGJ_SCJH != null) {
            stmt.bindString(33, DLGJ_SCJH);
        }
 
        String DLGJ_SCBM = entity.getDLGJ_SCBM();
        if (DLGJ_SCBM != null) {
            stmt.bindString(34, DLGJ_SCBM);
        }
 
        String VIDEOAREAID = entity.getVIDEOAREAID();
        if (VIDEOAREAID != null) {
            stmt.bindString(35, VIDEOAREAID);
        }
 
        java.util.Date GJPZSJ = entity.getGJPZSJ();
        if (GJPZSJ != null) {
            stmt.bindLong(36, GJPZSJ.getTime());
        }
 
        String GJPZPNG = entity.getGJPZPNG();
        if (GJPZPNG != null) {
            stmt.bindString(37, GJPZPNG);
        }
 
        String DLGJ_KSKH = entity.getDLGJ_KSKH();
        if (DLGJ_KSKH != null) {
            stmt.bindString(38, DLGJ_KSKH);
        }
 
        java.util.Date DLGJ_KSSJ = entity.getDLGJ_KSSJ();
        if (DLGJ_KSSJ != null) {
            stmt.bindLong(39, DLGJ_KSSJ.getTime());
        }
 
        String KS_ADDRESS = entity.getKS_ADDRESS();
        if (KS_ADDRESS != null) {
            stmt.bindString(40, KS_ADDRESS);
        }
 
        String DLGJ_KSXM = entity.getDLGJ_KSXM();
        if (DLGJ_KSXM != null) {
            stmt.bindString(41, DLGJ_KSXM);
        }
 
        String DLGJ_KSJH = entity.getDLGJ_KSJH();
        if (DLGJ_KSJH != null) {
            stmt.bindString(42, DLGJ_KSJH);
        }
 
        String DLGJ_KSBM = entity.getDLGJ_KSBM();
        if (DLGJ_KSBM != null) {
            stmt.bindString(43, DLGJ_KSBM);
        }
 
        String DLGJ_JHKH = entity.getDLGJ_JHKH();
        if (DLGJ_JHKH != null) {
            stmt.bindString(44, DLGJ_JHKH);
        }
 
        java.util.Date DLGJ_JHSJ = entity.getDLGJ_JHSJ();
        if (DLGJ_JHSJ != null) {
            stmt.bindLong(45, DLGJ_JHSJ.getTime());
        }
 
        String DLGJ_JHXM = entity.getDLGJ_JHXM();
        if (DLGJ_JHXM != null) {
            stmt.bindString(46, DLGJ_JHXM);
        }
 
        String DLGJ_JHJH = entity.getDLGJ_JHJH();
        if (DLGJ_JHJH != null) {
            stmt.bindString(47, DLGJ_JHJH);
        }
 
        String DLGJ_JHBM = entity.getDLGJ_JHBM();
        if (DLGJ_JHBM != null) {
            stmt.bindString(48, DLGJ_JHBM);
        }
 
        java.util.Date GLY_GHQRSJ = entity.getGLY_GHQRSJ();
        if (GLY_GHQRSJ != null) {
            stmt.bindLong(49, GLY_GHQRSJ.getTime());
        }
        stmt.bindLong(50, entity.getSJJHBZ());
 
        String BZ = entity.getBZ();
        if (BZ != null) {
            stmt.bindString(51, BZ);
        }
 
        String imageUrl = entity.getImageUrl();
        if (imageUrl != null) {
            stmt.bindString(52, imageUrl);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, CarTravelRecord entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getLS_ID());
 
        String ZDJ_ID = entity.getZDJ_ID();
        if (ZDJ_ID != null) {
            stmt.bindString(3, ZDJ_ID);
        }
        stmt.bindLong(4, entity.getQY_ID());
 
        java.util.Date RIQI = entity.getRIQI();
        if (RIQI != null) {
            stmt.bindLong(5, RIQI.getTime());
        }
        stmt.bindLong(6, entity.getZT());
 
        String GLRY = entity.getGLRY();
        if (GLRY != null) {
            stmt.bindString(7, GLRY);
        }
 
        java.util.Date GLRYSJ = entity.getGLRYSJ();
        if (GLRYSJ != null) {
            stmt.bindLong(8, GLRYSJ.getTime());
        }
 
        String DLGJ_LYKH = entity.getDLGJ_LYKH();
        if (DLGJ_LYKH != null) {
            stmt.bindString(9, DLGJ_LYKH);
        }
 
        java.util.Date DLGJ_LYSJ = entity.getDLGJ_LYSJ();
        if (DLGJ_LYSJ != null) {
            stmt.bindLong(10, DLGJ_LYSJ.getTime());
        }
 
        String DLGJ_LYXM = entity.getDLGJ_LYXM();
        if (DLGJ_LYXM != null) {
            stmt.bindString(11, DLGJ_LYXM);
        }
 
        String DLGJ_LYJH = entity.getDLGJ_LYJH();
        if (DLGJ_LYJH != null) {
            stmt.bindString(12, DLGJ_LYJH);
        }
 
        String DLGJ_LYBM = entity.getDLGJ_LYBM();
        if (DLGJ_LYBM != null) {
            stmt.bindString(13, DLGJ_LYBM);
        }
 
        String WLJSYKH = entity.getWLJSYKH();
        if (WLJSYKH != null) {
            stmt.bindString(14, WLJSYKH);
        }
 
        java.util.Date WLJSYSJ = entity.getWLJSYSJ();
        if (WLJSYSJ != null) {
            stmt.bindLong(15, WLJSYSJ.getTime());
        }
 
        String CLHP = entity.getCLHP();
        if (CLHP != null) {
            stmt.bindString(16, CLHP);
        }
 
        String CLLX = entity.getCLLX();
        if (CLLX != null) {
            stmt.bindString(17, CLLX);
        }
 
        String JSYXM = entity.getJSYXM();
        if (JSYXM != null) {
            stmt.bindString(18, JSYXM);
        }
 
        String JSYXB = entity.getJSYXB();
        if (JSYXB != null) {
            stmt.bindString(19, JSYXB);
        }
 
        String JRSY = entity.getJRSY();
        if (JRSY != null) {
            stmt.bindString(20, JRSY);
        }
 
        String JSYSFZ = entity.getJSYSFZ();
        if (JSYSFZ != null) {
            stmt.bindString(21, JSYSFZ);
        }
 
        String JSYSQBM = entity.getJSYSQBM();
        if (JSYSQBM != null) {
            stmt.bindString(22, JSYSQBM);
        }
 
        String DEV_NUMBER = entity.getDEV_NUMBER();
        if (DEV_NUMBER != null) {
            stmt.bindString(23, DEV_NUMBER);
        }
 
        String DLGJ_AZKH = entity.getDLGJ_AZKH();
        if (DLGJ_AZKH != null) {
            stmt.bindString(24, DLGJ_AZKH);
        }
 
        java.util.Date DLGJ_AZSJ = entity.getDLGJ_AZSJ();
        if (DLGJ_AZSJ != null) {
            stmt.bindLong(25, DLGJ_AZSJ.getTime());
        }
 
        String DLGJ_AZXM = entity.getDLGJ_AZXM();
        if (DLGJ_AZXM != null) {
            stmt.bindString(26, DLGJ_AZXM);
        }
 
        String DLGJ_AZJH = entity.getDLGJ_AZJH();
        if (DLGJ_AZJH != null) {
            stmt.bindString(27, DLGJ_AZJH);
        }
 
        String DLGJ_AZBM = entity.getDLGJ_AZBM();
        if (DLGJ_AZBM != null) {
            stmt.bindString(28, DLGJ_AZBM);
        }
 
        String DLGJ_SCKH = entity.getDLGJ_SCKH();
        if (DLGJ_SCKH != null) {
            stmt.bindString(29, DLGJ_SCKH);
        }
 
        java.util.Date DLGJ_SCSJ = entity.getDLGJ_SCSJ();
        if (DLGJ_SCSJ != null) {
            stmt.bindLong(30, DLGJ_SCSJ.getTime());
        }
 
        String SC_ADDRESS = entity.getSC_ADDRESS();
        if (SC_ADDRESS != null) {
            stmt.bindString(31, SC_ADDRESS);
        }
 
        String DLGJ_SCXM = entity.getDLGJ_SCXM();
        if (DLGJ_SCXM != null) {
            stmt.bindString(32, DLGJ_SCXM);
        }
 
        String DLGJ_SCJH = entity.getDLGJ_SCJH();
        if (DLGJ_SCJH != null) {
            stmt.bindString(33, DLGJ_SCJH);
        }
 
        String DLGJ_SCBM = entity.getDLGJ_SCBM();
        if (DLGJ_SCBM != null) {
            stmt.bindString(34, DLGJ_SCBM);
        }
 
        String VIDEOAREAID = entity.getVIDEOAREAID();
        if (VIDEOAREAID != null) {
            stmt.bindString(35, VIDEOAREAID);
        }
 
        java.util.Date GJPZSJ = entity.getGJPZSJ();
        if (GJPZSJ != null) {
            stmt.bindLong(36, GJPZSJ.getTime());
        }
 
        String GJPZPNG = entity.getGJPZPNG();
        if (GJPZPNG != null) {
            stmt.bindString(37, GJPZPNG);
        }
 
        String DLGJ_KSKH = entity.getDLGJ_KSKH();
        if (DLGJ_KSKH != null) {
            stmt.bindString(38, DLGJ_KSKH);
        }
 
        java.util.Date DLGJ_KSSJ = entity.getDLGJ_KSSJ();
        if (DLGJ_KSSJ != null) {
            stmt.bindLong(39, DLGJ_KSSJ.getTime());
        }
 
        String KS_ADDRESS = entity.getKS_ADDRESS();
        if (KS_ADDRESS != null) {
            stmt.bindString(40, KS_ADDRESS);
        }
 
        String DLGJ_KSXM = entity.getDLGJ_KSXM();
        if (DLGJ_KSXM != null) {
            stmt.bindString(41, DLGJ_KSXM);
        }
 
        String DLGJ_KSJH = entity.getDLGJ_KSJH();
        if (DLGJ_KSJH != null) {
            stmt.bindString(42, DLGJ_KSJH);
        }
 
        String DLGJ_KSBM = entity.getDLGJ_KSBM();
        if (DLGJ_KSBM != null) {
            stmt.bindString(43, DLGJ_KSBM);
        }
 
        String DLGJ_JHKH = entity.getDLGJ_JHKH();
        if (DLGJ_JHKH != null) {
            stmt.bindString(44, DLGJ_JHKH);
        }
 
        java.util.Date DLGJ_JHSJ = entity.getDLGJ_JHSJ();
        if (DLGJ_JHSJ != null) {
            stmt.bindLong(45, DLGJ_JHSJ.getTime());
        }
 
        String DLGJ_JHXM = entity.getDLGJ_JHXM();
        if (DLGJ_JHXM != null) {
            stmt.bindString(46, DLGJ_JHXM);
        }
 
        String DLGJ_JHJH = entity.getDLGJ_JHJH();
        if (DLGJ_JHJH != null) {
            stmt.bindString(47, DLGJ_JHJH);
        }
 
        String DLGJ_JHBM = entity.getDLGJ_JHBM();
        if (DLGJ_JHBM != null) {
            stmt.bindString(48, DLGJ_JHBM);
        }
 
        java.util.Date GLY_GHQRSJ = entity.getGLY_GHQRSJ();
        if (GLY_GHQRSJ != null) {
            stmt.bindLong(49, GLY_GHQRSJ.getTime());
        }
        stmt.bindLong(50, entity.getSJJHBZ());
 
        String BZ = entity.getBZ();
        if (BZ != null) {
            stmt.bindString(51, BZ);
        }
 
        String imageUrl = entity.getImageUrl();
        if (imageUrl != null) {
            stmt.bindString(52, imageUrl);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public CarTravelRecord readEntity(Cursor cursor, int offset) {
        CarTravelRecord entity = new CarTravelRecord( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // LS_ID
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // ZDJ_ID
            cursor.getInt(offset + 3), // QY_ID
            cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)), // RIQI
            cursor.getInt(offset + 5), // ZT
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // GLRY
            cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)), // GLRYSJ
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // DLGJ_LYKH
            cursor.isNull(offset + 9) ? null : new java.util.Date(cursor.getLong(offset + 9)), // DLGJ_LYSJ
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // DLGJ_LYXM
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // DLGJ_LYJH
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // DLGJ_LYBM
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // WLJSYKH
            cursor.isNull(offset + 14) ? null : new java.util.Date(cursor.getLong(offset + 14)), // WLJSYSJ
            cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15), // CLHP
            cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16), // CLLX
            cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17), // JSYXM
            cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18), // JSYXB
            cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19), // JRSY
            cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20), // JSYSFZ
            cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21), // JSYSQBM
            cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22), // DEV_NUMBER
            cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23), // DLGJ_AZKH
            cursor.isNull(offset + 24) ? null : new java.util.Date(cursor.getLong(offset + 24)), // DLGJ_AZSJ
            cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25), // DLGJ_AZXM
            cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26), // DLGJ_AZJH
            cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27), // DLGJ_AZBM
            cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28), // DLGJ_SCKH
            cursor.isNull(offset + 29) ? null : new java.util.Date(cursor.getLong(offset + 29)), // DLGJ_SCSJ
            cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30), // SC_ADDRESS
            cursor.isNull(offset + 31) ? null : cursor.getString(offset + 31), // DLGJ_SCXM
            cursor.isNull(offset + 32) ? null : cursor.getString(offset + 32), // DLGJ_SCJH
            cursor.isNull(offset + 33) ? null : cursor.getString(offset + 33), // DLGJ_SCBM
            cursor.isNull(offset + 34) ? null : cursor.getString(offset + 34), // VIDEOAREAID
            cursor.isNull(offset + 35) ? null : new java.util.Date(cursor.getLong(offset + 35)), // GJPZSJ
            cursor.isNull(offset + 36) ? null : cursor.getString(offset + 36), // GJPZPNG
            cursor.isNull(offset + 37) ? null : cursor.getString(offset + 37), // DLGJ_KSKH
            cursor.isNull(offset + 38) ? null : new java.util.Date(cursor.getLong(offset + 38)), // DLGJ_KSSJ
            cursor.isNull(offset + 39) ? null : cursor.getString(offset + 39), // KS_ADDRESS
            cursor.isNull(offset + 40) ? null : cursor.getString(offset + 40), // DLGJ_KSXM
            cursor.isNull(offset + 41) ? null : cursor.getString(offset + 41), // DLGJ_KSJH
            cursor.isNull(offset + 42) ? null : cursor.getString(offset + 42), // DLGJ_KSBM
            cursor.isNull(offset + 43) ? null : cursor.getString(offset + 43), // DLGJ_JHKH
            cursor.isNull(offset + 44) ? null : new java.util.Date(cursor.getLong(offset + 44)), // DLGJ_JHSJ
            cursor.isNull(offset + 45) ? null : cursor.getString(offset + 45), // DLGJ_JHXM
            cursor.isNull(offset + 46) ? null : cursor.getString(offset + 46), // DLGJ_JHJH
            cursor.isNull(offset + 47) ? null : cursor.getString(offset + 47), // DLGJ_JHBM
            cursor.isNull(offset + 48) ? null : new java.util.Date(cursor.getLong(offset + 48)), // GLY_GHQRSJ
            cursor.getInt(offset + 49), // SJJHBZ
            cursor.isNull(offset + 50) ? null : cursor.getString(offset + 50), // BZ
            cursor.isNull(offset + 51) ? null : cursor.getString(offset + 51) // imageUrl
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, CarTravelRecord entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setLS_ID(cursor.getInt(offset + 1));
        entity.setZDJ_ID(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setQY_ID(cursor.getInt(offset + 3));
        entity.setRIQI(cursor.isNull(offset + 4) ? null : new java.util.Date(cursor.getLong(offset + 4)));
        entity.setZT(cursor.getInt(offset + 5));
        entity.setGLRY(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setGLRYSJ(cursor.isNull(offset + 7) ? null : new java.util.Date(cursor.getLong(offset + 7)));
        entity.setDLGJ_LYKH(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setDLGJ_LYSJ(cursor.isNull(offset + 9) ? null : new java.util.Date(cursor.getLong(offset + 9)));
        entity.setDLGJ_LYXM(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setDLGJ_LYJH(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setDLGJ_LYBM(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setWLJSYKH(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setWLJSYSJ(cursor.isNull(offset + 14) ? null : new java.util.Date(cursor.getLong(offset + 14)));
        entity.setCLHP(cursor.isNull(offset + 15) ? null : cursor.getString(offset + 15));
        entity.setCLLX(cursor.isNull(offset + 16) ? null : cursor.getString(offset + 16));
        entity.setJSYXM(cursor.isNull(offset + 17) ? null : cursor.getString(offset + 17));
        entity.setJSYXB(cursor.isNull(offset + 18) ? null : cursor.getString(offset + 18));
        entity.setJRSY(cursor.isNull(offset + 19) ? null : cursor.getString(offset + 19));
        entity.setJSYSFZ(cursor.isNull(offset + 20) ? null : cursor.getString(offset + 20));
        entity.setJSYSQBM(cursor.isNull(offset + 21) ? null : cursor.getString(offset + 21));
        entity.setDEV_NUMBER(cursor.isNull(offset + 22) ? null : cursor.getString(offset + 22));
        entity.setDLGJ_AZKH(cursor.isNull(offset + 23) ? null : cursor.getString(offset + 23));
        entity.setDLGJ_AZSJ(cursor.isNull(offset + 24) ? null : new java.util.Date(cursor.getLong(offset + 24)));
        entity.setDLGJ_AZXM(cursor.isNull(offset + 25) ? null : cursor.getString(offset + 25));
        entity.setDLGJ_AZJH(cursor.isNull(offset + 26) ? null : cursor.getString(offset + 26));
        entity.setDLGJ_AZBM(cursor.isNull(offset + 27) ? null : cursor.getString(offset + 27));
        entity.setDLGJ_SCKH(cursor.isNull(offset + 28) ? null : cursor.getString(offset + 28));
        entity.setDLGJ_SCSJ(cursor.isNull(offset + 29) ? null : new java.util.Date(cursor.getLong(offset + 29)));
        entity.setSC_ADDRESS(cursor.isNull(offset + 30) ? null : cursor.getString(offset + 30));
        entity.setDLGJ_SCXM(cursor.isNull(offset + 31) ? null : cursor.getString(offset + 31));
        entity.setDLGJ_SCJH(cursor.isNull(offset + 32) ? null : cursor.getString(offset + 32));
        entity.setDLGJ_SCBM(cursor.isNull(offset + 33) ? null : cursor.getString(offset + 33));
        entity.setVIDEOAREAID(cursor.isNull(offset + 34) ? null : cursor.getString(offset + 34));
        entity.setGJPZSJ(cursor.isNull(offset + 35) ? null : new java.util.Date(cursor.getLong(offset + 35)));
        entity.setGJPZPNG(cursor.isNull(offset + 36) ? null : cursor.getString(offset + 36));
        entity.setDLGJ_KSKH(cursor.isNull(offset + 37) ? null : cursor.getString(offset + 37));
        entity.setDLGJ_KSSJ(cursor.isNull(offset + 38) ? null : new java.util.Date(cursor.getLong(offset + 38)));
        entity.setKS_ADDRESS(cursor.isNull(offset + 39) ? null : cursor.getString(offset + 39));
        entity.setDLGJ_KSXM(cursor.isNull(offset + 40) ? null : cursor.getString(offset + 40));
        entity.setDLGJ_KSJH(cursor.isNull(offset + 41) ? null : cursor.getString(offset + 41));
        entity.setDLGJ_KSBM(cursor.isNull(offset + 42) ? null : cursor.getString(offset + 42));
        entity.setDLGJ_JHKH(cursor.isNull(offset + 43) ? null : cursor.getString(offset + 43));
        entity.setDLGJ_JHSJ(cursor.isNull(offset + 44) ? null : new java.util.Date(cursor.getLong(offset + 44)));
        entity.setDLGJ_JHXM(cursor.isNull(offset + 45) ? null : cursor.getString(offset + 45));
        entity.setDLGJ_JHJH(cursor.isNull(offset + 46) ? null : cursor.getString(offset + 46));
        entity.setDLGJ_JHBM(cursor.isNull(offset + 47) ? null : cursor.getString(offset + 47));
        entity.setGLY_GHQRSJ(cursor.isNull(offset + 48) ? null : new java.util.Date(cursor.getLong(offset + 48)));
        entity.setSJJHBZ(cursor.getInt(offset + 49));
        entity.setBZ(cursor.isNull(offset + 50) ? null : cursor.getString(offset + 50));
        entity.setImageUrl(cursor.isNull(offset + 51) ? null : cursor.getString(offset + 51));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(CarTravelRecord entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(CarTravelRecord entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(CarTravelRecord entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
