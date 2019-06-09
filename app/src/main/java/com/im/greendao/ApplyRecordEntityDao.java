package com.im.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.im.entity.ApplyRecordEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "APPLY_RECORD_ENTITY".
*/
public class ApplyRecordEntityDao extends AbstractDao<ApplyRecordEntity, Long> {

    public static final String TABLENAME = "APPLY_RECORD_ENTITY";

    /**
     * Properties of entity ApplyRecordEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Portrait = new Property(0, String.class, "portrait", false, "PORTRAIT");
        public final static Property Name = new Property(1, String.class, "name", false, "NAME");
        public final static Property NickName = new Property(2, String.class, "nickName", false, "NICK_NAME");
        public final static Property NewComment = new Property(3, String.class, "newComment", false, "NEW_COMMENT");
        public final static Property CommunityName = new Property(4, String.class, "communityName", false, "COMMUNITY_NAME");
        public final static Property Gender = new Property(5, String.class, "gender", false, "GENDER");
        public final static Property Comment = new Property(6, String.class, "comment", false, "COMMENT");
        public final static Property State = new Property(7, String.class, "state", false, "STATE");
        public final static Property Uuid = new Property(8, String.class, "uuid", false, "UUID");
        public final static Property Real_name = new Property(9, String.class, "real_name", false, "REAL_NAME");
        public final static Property Mobile = new Property(10, String.class, "mobile", false, "MOBILE");
        public final static Property Id = new Property(11, Long.class, "id", true, "_id");
    }


    public ApplyRecordEntityDao(DaoConfig config) {
        super(config);
    }
    
    public ApplyRecordEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"APPLY_RECORD_ENTITY\" (" + //
                "\"PORTRAIT\" TEXT," + // 0: portrait
                "\"NAME\" TEXT," + // 1: name
                "\"NICK_NAME\" TEXT," + // 2: nickName
                "\"NEW_COMMENT\" TEXT," + // 3: newComment
                "\"COMMUNITY_NAME\" TEXT," + // 4: communityName
                "\"GENDER\" TEXT," + // 5: gender
                "\"COMMENT\" TEXT," + // 6: comment
                "\"STATE\" TEXT," + // 7: state
                "\"UUID\" TEXT," + // 8: uuid
                "\"REAL_NAME\" TEXT," + // 9: real_name
                "\"MOBILE\" TEXT," + // 10: mobile
                "\"_id\" INTEGER PRIMARY KEY );"); // 11: id
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"APPLY_RECORD_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, ApplyRecordEntity entity) {
        stmt.clearBindings();
 
        String portrait = entity.getPortrait();
        if (portrait != null) {
            stmt.bindString(1, portrait);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(3, nickName);
        }
 
        String newComment = entity.getNewComment();
        if (newComment != null) {
            stmt.bindString(4, newComment);
        }
 
        String communityName = entity.getCommunityName();
        if (communityName != null) {
            stmt.bindString(5, communityName);
        }
 
        String gender = entity.getGender();
        if (gender != null) {
            stmt.bindString(6, gender);
        }
 
        String comment = entity.getComment();
        if (comment != null) {
            stmt.bindString(7, comment);
        }
 
        String state = entity.getState();
        if (state != null) {
            stmt.bindString(8, state);
        }
 
        String uuid = entity.getUuid();
        if (uuid != null) {
            stmt.bindString(9, uuid);
        }
 
        String real_name = entity.getReal_name();
        if (real_name != null) {
            stmt.bindString(10, real_name);
        }
 
        String mobile = entity.getMobile();
        if (mobile != null) {
            stmt.bindString(11, mobile);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(12, id);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, ApplyRecordEntity entity) {
        stmt.clearBindings();
 
        String portrait = entity.getPortrait();
        if (portrait != null) {
            stmt.bindString(1, portrait);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(2, name);
        }
 
        String nickName = entity.getNickName();
        if (nickName != null) {
            stmt.bindString(3, nickName);
        }
 
        String newComment = entity.getNewComment();
        if (newComment != null) {
            stmt.bindString(4, newComment);
        }
 
        String communityName = entity.getCommunityName();
        if (communityName != null) {
            stmt.bindString(5, communityName);
        }
 
        String gender = entity.getGender();
        if (gender != null) {
            stmt.bindString(6, gender);
        }
 
        String comment = entity.getComment();
        if (comment != null) {
            stmt.bindString(7, comment);
        }
 
        String state = entity.getState();
        if (state != null) {
            stmt.bindString(8, state);
        }
 
        String uuid = entity.getUuid();
        if (uuid != null) {
            stmt.bindString(9, uuid);
        }
 
        String real_name = entity.getReal_name();
        if (real_name != null) {
            stmt.bindString(10, real_name);
        }
 
        String mobile = entity.getMobile();
        if (mobile != null) {
            stmt.bindString(11, mobile);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(12, id);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 11) ? null : cursor.getLong(offset + 11);
    }    

    @Override
    public ApplyRecordEntity readEntity(Cursor cursor, int offset) {
        ApplyRecordEntity entity = new ApplyRecordEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // portrait
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // name
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // nickName
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // newComment
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // communityName
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // gender
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // comment
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // state
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // uuid
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // real_name
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // mobile
            cursor.isNull(offset + 11) ? null : cursor.getLong(offset + 11) // id
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, ApplyRecordEntity entity, int offset) {
        entity.setPortrait(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setNickName(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setNewComment(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setCommunityName(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setGender(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setComment(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setState(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setUuid(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setReal_name(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setMobile(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setId(cursor.isNull(offset + 11) ? null : cursor.getLong(offset + 11));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(ApplyRecordEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(ApplyRecordEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(ApplyRecordEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}