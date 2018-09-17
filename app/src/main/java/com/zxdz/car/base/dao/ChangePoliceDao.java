package com.zxdz.car.base.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.zxdz.car.main.model.domain.ChangePolice;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CHANGE_POLICE".
*/
public class ChangePoliceDao extends AbstractDao<ChangePolice, Integer> {

    public static final String TABLENAME = "CHANGE_POLICE";

    /**
     * Properties of entity ChangePolice.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Change_id = new Property(0, int.class, "change_id", true, "CHANGE_ID");
        public final static Property Change_terminal = new Property(1, String.class, "change_terminal", false, "CHANGE_TERMINAL");
        public final static Property Change_time = new Property(2, String.class, "change_time", false, "CHANGE_TIME");
        public final static Property Change_admin_num = new Property(3, String.class, "change_admin_num", false, "CHANGE_ADMIN_NUM");
        public final static Property Change_police_old = new Property(4, String.class, "change_police_old", false, "CHANGE_POLICE_OLD");
        public final static Property Change_police_new = new Property(5, String.class, "change_police_new", false, "CHANGE_POLICE_NEW");
    }


    public ChangePoliceDao(DaoConfig config) {
        super(config);
    }
    
    public ChangePoliceDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CHANGE_POLICE\" (" + //
                "\"CHANGE_ID\" INTEGER PRIMARY KEY NOT NULL ," + // 0: change_id
                "\"CHANGE_TERMINAL\" TEXT," + // 1: change_terminal
                "\"CHANGE_TIME\" TEXT," + // 2: change_time
                "\"CHANGE_ADMIN_NUM\" TEXT," + // 3: change_admin_num
                "\"CHANGE_POLICE_OLD\" TEXT," + // 4: change_police_old
                "\"CHANGE_POLICE_NEW\" TEXT);"); // 5: change_police_new
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CHANGE_POLICE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ChangePolice entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getChange_id());
 
        String change_terminal = entity.getChange_terminal();
        if (change_terminal != null) {
            stmt.bindString(2, change_terminal);
        }
 
        String change_time = entity.getChange_time();
        if (change_time != null) {
            stmt.bindString(3, change_time);
        }
 
        String change_admin_num = entity.getChange_admin_num();
        if (change_admin_num != null) {
            stmt.bindString(4, change_admin_num);
        }
 
        String change_police_old = entity.getChange_police_old();
        if (change_police_old != null) {
            stmt.bindString(5, change_police_old);
        }
 
        String change_police_new = entity.getChange_police_new();
        if (change_police_new != null) {
            stmt.bindString(6, change_police_new);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ChangePolice entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getChange_id());
 
        String change_terminal = entity.getChange_terminal();
        if (change_terminal != null) {
            stmt.bindString(2, change_terminal);
        }
 
        String change_time = entity.getChange_time();
        if (change_time != null) {
            stmt.bindString(3, change_time);
        }
 
        String change_admin_num = entity.getChange_admin_num();
        if (change_admin_num != null) {
            stmt.bindString(4, change_admin_num);
        }
 
        String change_police_old = entity.getChange_police_old();
        if (change_police_old != null) {
            stmt.bindString(5, change_police_old);
        }
 
        String change_police_new = entity.getChange_police_new();
        if (change_police_new != null) {
            stmt.bindString(6, change_police_new);
        }
    }

    @Override
    public Integer readKey(Cursor cursor, int offset) {
        return cursor.getInt(offset + 0);
    }    

    @Override
    public ChangePolice readEntity(Cursor cursor, int offset) {
        ChangePolice entity = new ChangePolice( //
            cursor.getInt(offset + 0), // change_id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // change_terminal
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // change_time
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // change_admin_num
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // change_police_old
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5) // change_police_new
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ChangePolice entity, int offset) {
        entity.setChange_id(cursor.getInt(offset + 0));
        entity.setChange_terminal(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setChange_time(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setChange_admin_num(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setChange_police_old(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setChange_police_new(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
     }
    
    @Override
    protected final Integer updateKeyAfterInsert(ChangePolice entity, long rowId) {
        return entity.getChange_id();
    }
    
    @Override
    public Integer getKey(ChangePolice entity) {
        if(entity != null) {
            return entity.getChange_id();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ChangePolice entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}