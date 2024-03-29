package com.fruizc.sites.modeldb;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "PLACE".
*/
public class PlaceDao extends AbstractDao<Place, Long> {

    public static final String TABLENAME = "PLACE";

    /**
     * Properties of entity Place.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property IdPlace = new Property(0, Long.class, "idPlace", true, "ID_PLACE");
        public final static Property PlaceName = new Property(1, String.class, "placeName", false, "PLACE_NAME");
        public final static Property DescPlace = new Property(2, String.class, "descPlace", false, "DESC_PLACE");
        public final static Property CreatedAt = new Property(3, String.class, "createdAt", false, "CREATED_AT");
        public final static Property Picture = new Property(4, String.class, "picture", false, "PICTURE");
    }


    public PlaceDao(DaoConfig config) {
        super(config);
    }
    
    public PlaceDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"PLACE\" (" + //
                "\"ID_PLACE\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 0: idPlace
                "\"PLACE_NAME\" TEXT," + // 1: placeName
                "\"DESC_PLACE\" TEXT," + // 2: descPlace
                "\"CREATED_AT\" TEXT," + // 3: createdAt
                "\"PICTURE\" TEXT);"); // 4: picture
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"PLACE\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, Place entity) {
        stmt.clearBindings();
 
        Long idPlace = entity.getIdPlace();
        if (idPlace != null) {
            stmt.bindLong(1, idPlace);
        }
 
        String placeName = entity.getPlaceName();
        if (placeName != null) {
            stmt.bindString(2, placeName);
        }
 
        String descPlace = entity.getDescPlace();
        if (descPlace != null) {
            stmt.bindString(3, descPlace);
        }
 
        String createdAt = entity.getCreatedAt();
        if (createdAt != null) {
            stmt.bindString(4, createdAt);
        }
 
        String picture = entity.getPicture();
        if (picture != null) {
            stmt.bindString(5, picture);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, Place entity) {
        stmt.clearBindings();
 
        Long idPlace = entity.getIdPlace();
        if (idPlace != null) {
            stmt.bindLong(1, idPlace);
        }
 
        String placeName = entity.getPlaceName();
        if (placeName != null) {
            stmt.bindString(2, placeName);
        }
 
        String descPlace = entity.getDescPlace();
        if (descPlace != null) {
            stmt.bindString(3, descPlace);
        }
 
        String createdAt = entity.getCreatedAt();
        if (createdAt != null) {
            stmt.bindString(4, createdAt);
        }
 
        String picture = entity.getPicture();
        if (picture != null) {
            stmt.bindString(5, picture);
        }
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0);
    }    

    @Override
    public Place readEntity(Cursor cursor, int offset) {
        Place entity = new Place( //
            cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0), // idPlace
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // placeName
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // descPlace
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // createdAt
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4) // picture
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, Place entity, int offset) {
        entity.setIdPlace(cursor.isNull(offset + 0) ? null : cursor.getLong(offset + 0));
        entity.setPlaceName(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setDescPlace(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setCreatedAt(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setPicture(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
     }
    
    @Override
    protected final Long updateKeyAfterInsert(Place entity, long rowId) {
        entity.setIdPlace(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(Place entity) {
        if(entity != null) {
            return entity.getIdPlace();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(Place entity) {
        return entity.getIdPlace() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
