package com.im.greendao;

import android.content.Context;

import com.youmai.hxsdk.db.manager.MigrationHelper;

import org.greenrobot.greendao.database.Database;


public class IMGreenDaoOpenHelper extends DaoMaster.DevOpenHelper {

    /**
     * 初始化一个AbSDDBHelper.
     *
     * @param context 应用context
     * @param name    数据库名
     */
    public IMGreenDaoOpenHelper(Context context, String name) {
        super(context, name);
    }

    /**
     * 数据库升级
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        //操作数据库的更新
        if (oldVersion < newVersion) {
            MigrationHelper.migrate(db, ApplyRecordEntityDao.class);
            MigrationHelper.migrate(db, FriendInforEntityDao.class);
            MigrationHelper.migrate(db, MobileInforEntityDao.class);
        }
    }

}
