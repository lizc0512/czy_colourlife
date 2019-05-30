package com.youmai.hxsdk.db.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.youmai.hxsdk.db.bean.ContactBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "CONTACT_BEAN".
*/
public class ContactBeanDao extends AbstractDao<ContactBean, Long> {

    public static final String TABLENAME = "CONTACT_BEAN";

    /**
     * Properties of entity ContactBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property Uuid = new Property(1, String.class, "uuid", false, "UUID");
        public final static Property UserId = new Property(2, String.class, "userId", false, "USER_ID");
        public final static Property Mobile = new Property(3, String.class, "mobile", false, "MOBILE");
        public final static Property NickName = new Property(4, String.class, "nickName", false, "NICK_NAME");
        public final static Property RealName = new Property(5, String.class, "realName", false, "REAL_NAME");
        public final static Property UserName = new Property(6, String.class, "userName", false, "USER_NAME");
        public final static Property Avatar = new Property(7, String.class, "avatar", false, "AVATAR");
        public final static Property Sex = new Property(8, String.class, "sex", false, "SEX");
        public final static Property Email = new Property(9, String.class, "email", false, "EMAIL");
        public final static Property JobName = new Property(10, String.class, "jobName", false, "JOB_NAME");
        public final static Property OrgId = new Property(11, String.class, "orgId", false, "ORG_ID");
        public final static Property OrgName = new Property(12, String.class, "orgName", false, "ORG_NAME");
        public final static Property Sign = new Property(13, String.class, "sign", false, "SIGN");
        public final static Property Status = new Property(14, int.class, "status", false, "STATUS");
    }


    public ContactBeanDao(DaoConfig config) {
        super(config);
    }
    
    public ContactBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"CONTACT_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"UUID\" TEXT," + // 1: uuid
                "\"USER_ID\" TEXT," + // 2: userId
                "\"MOBILE\" TEXT," + // 3: mobile
                "\"NICK_NAME\" TEXT," + // 4: nickName
                "\"REAL_NAME\" TEXT," + // 5: realName
                "\"USER_NAME\" TEXT," + // 6: userName
                "\"AVATAR\" TEXT," + // 7: avatar
                "\"SEX\" TEXT," + // 8: sex
                "\"EMAIL\" TEXT," + // 9: email
                "\"JOB_NAME\" TEXT," + // 10: jobName
                "\"ORG_ID\" TEXT," + // 11: orgId
                "\"ORG_NAME\" TEXT," + // 12: orgName
                "\"SIGN\" TEXT," + // 13: sign
                "\"STATUS\" INTEGER NOT NULL );"); // 14: status
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"CONTACT_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ContactBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String uuid = entity.getUuid();
        if (uuid != null) {
            stmt.bindString(2, uuid);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(3, userId);
        }
 
        String mobile = entity.getMobile();
        if (mobile != null) {
            stmt.bindString(4, mobile);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(5, nickName);
        }
 
        String realName = entity.getRealName();
        if (realName != null) {
            stmt.bindString(6, realName);
        }
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(7, userName);
        }
 
        String avatar = entity.getAvatar();
        if (avatar != null) {
            stmt.bindString(8, avatar);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(9, sex);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(10, email);
        }
 
        String jobName = entity.getJobName();
        if (jobName != null) {
            stmt.bindString(11, jobName);
        }
 
        String orgId = entity.getOrgId();
        if (orgId != null) {
            stmt.bindString(12, orgId);
        }
 
        String orgName = entity.getOrgName();
        if (orgName != null) {
            stmt.bindString(13, orgName);
        }
 
        String sign = entity.getSign();
        if (sign != null) {
            stmt.bindString(14, sign);
        }
        stmt.bindLong(15, entity.getStatus());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ContactBean entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
 
        String uuid = entity.getUuid();
        if (uuid != null) {
            stmt.bindString(2, uuid);
        }
 
        String userId = entity.getUserId();
        if (userId != null) {
            stmt.bindString(3, userId);
        }
 
        String mobile = entity.getMobile();
        if (mobile != null) {
            stmt.bindString(4, mobile);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(5, nickName);
        }
 
        String realName = entity.getRealName();
        if (realName != null) {
            stmt.bindString(6, realName);
        }
 
        String userName = entity.getUserName();
        if (userName != null) {
            stmt.bindString(7, userName);
        }
 
        String avatar = entity.getAvatar();
        if (avatar != null) {
            stmt.bindString(8, avatar);
        }
 
        String sex = entity.getSex();
        if (sex != null) {
            stmt.bindString(9, sex);
        }
 
        String email = entity.getEmail();
        if (email != null) {
            stmt.bindString(10, email);
        }
 
        String jobName = entity.getJobName();
        if (jobName != null) {
            stmt.bindString(11, jobName);
        }
 
        String orgId = entity.getOrgId();
        if (orgId != null) {
            stmt.bindString(12, orgId);
        }
 
        String orgName = entity.getOrgName();
        if (orgName != null) {
            stmt.bindString(13, orgName);
        }
 
        String sign = entity.getSign();
        if (sign != null) {
            stmt.bindString(14, sign);
        }
        stmt.bindLong(15, entity.getStatus());
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public ContactBean readEntity(Cursor cursor, int offset) {
        ContactBean entity = new ContactBean( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // uuid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // userId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // mobile
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // nickName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // realName
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // userName
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // avatar
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // sex
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // email
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // jobName
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // orgId
            cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12), // orgName
            cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13), // sign
            cursor.getInt(offset + 14) // status
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ContactBean entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUuid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setUserId(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setMobile(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setNickName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setRealName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setUserName(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setAvatar(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setSex(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setEmail(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setJobName(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setOrgId(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setOrgName(cursor.isNull(offset + 12) ? null : cursor.getString(offset + 12));
        entity.setSign(cursor.isNull(offset + 13) ? null : cursor.getString(offset + 13));
        entity.setStatus(cursor.getInt(offset + 14));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ContactBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ContactBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ContactBean entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
