package com.im.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.im.entity.IntelligenceDoorEntity;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "INTELLIGENCE_DOOR_ENTITY".
*/
public class IntelligenceDoorEntityDao extends AbstractDao<IntelligenceDoorEntity, Long> {

    public static final String TABLENAME = "INTELLIGENCE_DOOR_ENTITY";

    /**
     * Properties of entity IntelligenceDoorEntity.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, Long.class, "id", true, "_id");
        public final static Property User_id = new Property(1, int.class, "user_id", false, "USER_ID");
        public final static Property Qr_code = new Property(2, String.class, "qr_code", false, "QR_CODE");
        public final static Property Rename = new Property(3, String.class, "rename", false, "RENAME");
    }


    public IntelligenceDoorEntityDao(DaoConfig config) {
        super(config);
    }
    
    public IntelligenceDoorEntityDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"INTELLIGENCE_DOOR_ENTITY\" (" + //
                "\"_id\" INTEGER PRIMARY KEY ," + // 0: id
                "\"USER_ID\" INTEGER NOT NULL ," + // 1: user_id
                "\"QR_CODE\" TEXT," + // 2: qr_code
                "\"RENAME\" TEXT);"); // 3: rename
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"INTELLIGENCE_DOOR_ENTITY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, IntelligenceDoorEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getUser_id());
 
        String qr_code = entity.getQr_code();
        if (qr_code != null) {
            stmt.bindString(3, qr_code);
        }
 
        String rename = entity.getRename();
        if (rename != null) {
            stmt.bindString(4, rename);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, IntelligenceDoorEntity entity) {
        stmt.clearBindings();
 
        Long id = entity.getId();
        if (id != null) {
            stmt.bindLong(1, id);
        }
        stmt.bindLong(2, entity.getUser_id());
 
        String qr_code = entity.getQr_code();
        if (qr_code != null) {
            stmt.bindString(3, qr_code);
        }
 
        String rename = entity.getRename();
        if (rename != null) {
            stmt.bindString(4, rename);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public IntelligenceDoorEntity readEntity(Cursor cursor, int offset) {
        IntelligenceDoorEntity entity = new IntelligenceDoorEntity( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // id
            cursor.getInt(offset + 1), // user_id
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // qr_code
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // rename
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, IntelligenceDoorEntity entity, int offset) {
        entity.setId(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setUser_id(cursor.getInt(offset + 1));
        entity.setQr_code(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setRename(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(IntelligenceDoorEntity entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(IntelligenceDoorEntity entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(IntelligenceDoorEntity entity) {
        return entity.getId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
