package com.zxdz.car.base.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;
import org.greenrobot.greendao.identityscope.IdentityScopeType;


// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/**
 * Master of DAO (schema version 6): knows all DAOs.
 */
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 6;

    /** Creates underlying database table using DAOs. */
    public static void createAllTables(Database db, boolean ifNotExists) {
        AdminCardDao.createTable(db, ifNotExists);
        AreaInfoDao.createTable(db, ifNotExists);
        CardInfoDao.createTable(db, ifNotExists);
        CarTravelRecordDao.createTable(db, ifNotExists);
        ChangePoliceDao.createTable(db, ifNotExists);
        ChangePoliceInfoDao.createTable(db, ifNotExists);
        PictureInfoDao.createTable(db, ifNotExists);
        PoliceInfoDao.createTable(db, ifNotExists);
        PoliceInfoAllDao.createTable(db, ifNotExists);
        RouteInfoDao.createTable(db, ifNotExists);
        ServerIPDao.createTable(db, ifNotExists);
        TerminalInfoDao.createTable(db, ifNotExists);
        TrailPointInfoDao.createTable(db, ifNotExists);
        UnloadAreaInfoDao.createTable(db, ifNotExists);
        UnWarnInfoDao.createTable(db, ifNotExists);
        WarnInfoDao.createTable(db, ifNotExists);
    }

    /** Drops underlying database table using DAOs. */
    public static void dropAllTables(Database db, boolean ifExists) {
        AdminCardDao.dropTable(db, ifExists);
        AreaInfoDao.dropTable(db, ifExists);
        CardInfoDao.dropTable(db, ifExists);
        CarTravelRecordDao.dropTable(db, ifExists);
        ChangePoliceDao.dropTable(db, ifExists);
        ChangePoliceInfoDao.dropTable(db, ifExists);
        PictureInfoDao.dropTable(db, ifExists);
        PoliceInfoDao.dropTable(db, ifExists);
        PoliceInfoAllDao.dropTable(db, ifExists);
        RouteInfoDao.dropTable(db, ifExists);
        ServerIPDao.dropTable(db, ifExists);
        TerminalInfoDao.dropTable(db, ifExists);
        TrailPointInfoDao.dropTable(db, ifExists);
        UnloadAreaInfoDao.dropTable(db, ifExists);
        UnWarnInfoDao.dropTable(db, ifExists);
        WarnInfoDao.dropTable(db, ifExists);
    }

    /**
     * WARNING: Drops all table on Upgrade! Use only during development.
     * Convenience method using a {@link DevOpenHelper}.
     */
    public static DaoSession newDevSession(Context context, String name) {
        Database db = new DevOpenHelper(context, name).getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }

    public DaoMaster(SQLiteDatabase db) {
        this(new StandardDatabase(db));
    }

    public DaoMaster(Database db) {
        super(db, SCHEMA_VERSION);
        registerDaoClass(AdminCardDao.class);
        registerDaoClass(AreaInfoDao.class);
        registerDaoClass(CardInfoDao.class);
        registerDaoClass(CarTravelRecordDao.class);
        registerDaoClass(ChangePoliceDao.class);
        registerDaoClass(ChangePoliceInfoDao.class);
        registerDaoClass(PictureInfoDao.class);
        registerDaoClass(PoliceInfoDao.class);
        registerDaoClass(PoliceInfoAllDao.class);
        registerDaoClass(RouteInfoDao.class);
        registerDaoClass(ServerIPDao.class);
        registerDaoClass(TerminalInfoDao.class);
        registerDaoClass(TrailPointInfoDao.class);
        registerDaoClass(UnloadAreaInfoDao.class);
        registerDaoClass(UnWarnInfoDao.class);
        registerDaoClass(WarnInfoDao.class);
    }

    public DaoSession newSession() {
        return new DaoSession(db, IdentityScopeType.Session, daoConfigMap);
    }

    public DaoSession newSession(IdentityScopeType type) {
        return new DaoSession(db, type, daoConfigMap);
    }

    /**
     * Calls {@link #createAllTables(Database, boolean)} in {@link #onCreate(Database)} -
     */
    public static abstract class OpenHelper extends DatabaseOpenHelper {
        public OpenHelper(Context context, String name) {
            super(context, name, SCHEMA_VERSION);
        }

        public OpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory, SCHEMA_VERSION);
        }

        @Override
        public void onCreate(Database db) {
            Log.i("greenDAO", "Creating tables for schema version " + SCHEMA_VERSION);
            createAllTables(db, false);
        }
    }

    /** WARNING: Drops all table on Upgrade! Use only during development. */
    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String name) {
            super(context, name);
        }

        public DevOpenHelper(Context context, String name, CursorFactory factory) {
            super(context, name, factory);
        }

        @Override
        public void onUpgrade(Database db, int oldVersion, int newVersion) {
            Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
            dropAllTables(db, true);
            onCreate(db);
        }
    }

}
