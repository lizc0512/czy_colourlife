package com.im.greendao;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.update.service.UpdateService;
import com.user.UserAppConst;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;


public class IMGreenDaoManager {

    // 数据库名
    private static final String DB_NAME = "czy_im_";

    private static IMGreenDaoManager instance = null;

    private Context mContext;
    private DaoSession mDaoSession;

    public static IMGreenDaoManager instance(Context context) {
        if (instance == null) {
            instance = new IMGreenDaoManager(context);
        }
        return instance;
    }

    private String mUuid;

    private IMGreenDaoManager(Context context) {
        mContext = context.getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences(UserAppConst.USERINFO, 0);
        if (sharedPreferences.contains(UserAppConst.Colour_User_uuid)) {
            mUuid = sharedPreferences.getString(UserAppConst.Colour_User_uuid, "");
        } else {
            mUuid = "";
        }
        String dbName = DB_NAME + mUuid + ".db";
        initDBDao(dbName);
    }

    public void init(String uuid) {
        if (TextUtils.isEmpty(mUuid) || !mUuid.equals(uuid)) {
            mUuid = uuid;
            String dbName = DB_NAME + uuid + ".db";
            genDBDao(dbName);
        }
    }

    private void genDBDao(String dbName) {
        try {
            IMGreenDaoOpenHelper helper = new IMGreenDaoOpenHelper(mContext, dbName);
            Database db = helper.getWritableDb();
            mDaoSession = new DaoMaster(db).newSession();
            QueryBuilder.LOG_SQL = true;
            QueryBuilder.LOG_VALUES = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initDBDao(String dbName) {
        try {
            if (mDaoSession == null) {
                IMGreenDaoOpenHelper helper = new IMGreenDaoOpenHelper(mContext, dbName);
                Database db = helper.getWritableDb();
                mDaoSession = new DaoMaster(db).newSession();
                QueryBuilder.LOG_SQL = true;
                QueryBuilder.LOG_VALUES = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public  void deleteAllCache() {
        mDaoSession.getApplyRecordEntityDao().deleteAll();
        mDaoSession.getFriendInforEntityDao().deleteAll();
        mDaoSession.getMobileInforEntityDao().deleteAll();
    }

    public ApplyRecordEntityDao getApplyRecordEntityDao() {
        return mDaoSession.getApplyRecordEntityDao();
    }

    public FriendInforEntityDao getFriendInforEntityDao() {
        return mDaoSession.getFriendInforEntityDao();
    }

    public MobileInforEntityDao getMobileInforEntityDao() {
        return mDaoSession.getMobileInforEntityDao();
    }

    public IntelligenceDoorEntityDao getIntelligenceDoorEntityDao() {
        return mDaoSession.getIntelligenceDoorEntityDao();
    }
}
