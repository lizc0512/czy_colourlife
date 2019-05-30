package com.im.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.im.entity.MobileInforEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MOBILE_INFOR_ENTITY".
*/
public class MobileInforEntityDao extends AbstractDao<MobileInforEntity, Long> {

    public static final String TABLENAME = "MOBILE_INFOR_ENTITY";

    /**
     * Properties of entity MobileInforEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property User_id = new Property(0, String.class, "user_id", false, "USER_ID");
        public final static Property Uuid = new Property(1, String.class, "uuid", false, "UUID");
        public final static Property Mobile = new Property(2, String.class, "mobile", false, "MOBILE");
        public final static Property State = new Property(3, String.class, "state", false, "STATE");
        public final static Property Portrait = new Property(4, String.class, "portrait", false, "PORTRAIT");
        public final static Property Gender = new Property(5, String.class, "gender", false, "GENDER");
        public final static Property Nick_name = new Property(6, String.class, "nick_name", false, "NICK_NAME");
        public final static Property Name = new Property(7, String.class, "name", false, "NAME");
        public final static Property Community_uuid = new Property(8, String.class, "community_uuid", false, "COMMUNITY_UUID");
        public final static Property Community_name = new Property(9, String.class, "community_name", false, "COMMUNITY_NAME");
        public final static Property Comment = new Property(10, String.class, "comment", false, "COMMENT");
        public final static Property SortLetters = new Property(11, String.class, "sortLetters", false, "SORT_LETTERS");
        public final static Property Id = new Property(12, Long.class, "id", true, "_id");
    }


    public MobileInforEntityDao(DaoConfig config) {
        super(config);
    }
    
    public MobileInforEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MOBILE_INFOR_ENTITY\" (" + //
                "\"USER_ID\" TEXT," + // 0: user_id
                "\"UUID\" TEXT," + // 1: uuid
                "\"MOBILE\" TEXT," + // 2: mobile
                "\"STATE\" TEXT," + // 3: state
                "\"PORTRAIT\" TEXT," + // 4: portrait
                "\"GENDER\" TEXT," + // 5: gender
                "\"NICK_NAME\" TEXT," + // 6: nick_name
                "\"NAME\" TEXT," + // 7: name
                "\"COMMUNITY_UUID\" TEXT," + // 8: community_uuid
                "\"COMMUNITY_NAME\" TEXT," + // 9: community_name
                "\"COMMENT\" TEXT," + // 10: comment
                "\"SORT_LETTERS\" TEXT," + // 11: sortLetters
                "\"_id\" INTEGER PRIMARY KEY );"); // 12: id
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MOBILE_INFOR_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, MobileInforEntity entity) {
        stmt.clearBindings();
 
        String user_id = entity.getUser_id();
        if (user_id != null) {
            stmt.bindString(1, user_id);
        }
 
        String uuid = entity.getUuid();
        if (uuid != null) {
            stmt.bindString(2, uuid);
        }
 
        String mobile = entity.getMobile();
        if (mobile != null) {
            stmt.bindString(3, mobile);
        }
 
        String state = entity.getState();
        if (state != null) {
            stmt.bindString(4, state);
        }
 
        String portrait = entity.getPortrait();
        if (portrait != null) {
            stmt.bindString(5, portrait);
        }
 
        String gender = entity.getGender();
        if (gender != null) {
            stmt.bindString(6, gender);
        }
 
        String nick_name = entity.getNick_name();
        if (nick_name != null) {
            stmt.bindString(7, nick_name);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(8, name);
        }
 
        String community_uuid = entity.getCommunity_uuid();
        if (community_uuid != null) {
            stmt.bindString(9, community_uuid);
        }
 
        String community_name = entity.getCommunity_name();
        if (community_name != null) {
            stmt.bindString(10, community_name);
        }
 
        String comment = entity.getComment();
        if (comment != null) {
            stmt.bindString(11, comment);
        }
 
        String sortLetters = entity.getSortLetters();
        if (sortLetters != null) {
            stmt.bindString(12, sortLetters);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(13, id);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, MobileInforEntity entity) {
        stmt.clearBindings();
 
        String user_id = entity.getUser_id();
        if (user_id != null) {
            stmt.bindString(1, user_id);
        }
 
        String uuid = entity.getUuid();
        if (uuid != null) {
            stmt.bindString(2, uuid);
        }
 
        String mobile = entity.getMobile();
        if (mobile != null) {
            stmt.bindString(3, mobile);
        }
 
        String state = entity.getState();
        if (state != null) {
            stmt.bindString(4, state);
        }
 
        String portrait = entity.getPortrait();
        if (portrait != null) {
            stmt.bindString(5, portrait);
        }
 
        String gender = entity.getGender();
        if (gender != null) {
            stmt.bindString(6, gender);
        }
 
        String nick_name = entity.getNick_name();
        if (nick_name != null) {
            stmt.bindString(7, nick_name);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(8, name);
        }
 
        String community_uuid = entity.getCommunity_uuid();
        if (community_uuid != null) {
            stmt.bindString(9, community_uuid);
        }
 
        String community_name = entity.getCommunity_name();
        if (community_name != null) {
            stmt.bindString(10, community_name);
        }
 
        String comment = entity.getComment();
        if (comment != null) {
            stmt.bindString(11, comment);
        }
 
        String sortLetters = entity.getSortLetters();
        if (sortLetters != null) {
            stmt.bindString(12, sortLetters);
        }
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(13, id);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 12) ? null : cursor.getLong(offset + 12);
    }    

    @Override
    public MobileInforEntity readEntity(Cursor cursor, int offset) {
        MobileInforEntity entity = new MobileInforEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // user_id
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // uuid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // mobile
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // state
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // portrait
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // gender
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // nick_name
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // name
            cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8), // community_uuid
            cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9), // community_name
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // comment
            cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11), // sortLetters
            cursor.isNull(offset + 12) ? null : cursor.getLong(offset + 12) // id
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, MobileInforEntity entity, int offset) {
        entity.setUser_id(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setUuid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setMobile(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setState(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPortrait(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setGender(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setNick_name(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setName(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setCommunity_uuid(cursor.isNull(offset + 8) ? null : cursor.getString(offset + 8));
        entity.setCommunity_name(cursor.isNull(offset + 9) ? null : cursor.getString(offset + 9));
        entity.setComment(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setSortLetters(cursor.isNull(offset + 11) ? null : cursor.getString(offset + 11));
        entity.setId(cursor.isNull(offset + 12) ? null : cursor.getLong(offset + 12));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(MobileInforEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(MobileInforEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(MobileInforEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
