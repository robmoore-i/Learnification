package com.rrm.learnification.idgenerator.serializable;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.rrm.learnification.logger.AndroidLogger;

public class IdGeneratorSqlTable implements BaseColumns {
    private static final String LOG_TAG = "IdGeneratorSqlTable";

    private static final String TABLE_NAME = "idgenerator";
    private static final String COLUMN_NAME_ID_TYPE = "id_type";
    private static final String COLUMN_NAME_ID_COUNTER = "id_counter";

    private IdGeneratorSqlTable() {
    }

    public static String createTable() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_ID_TYPE + " TEXT," +
                COLUMN_NAME_ID_COUNTER + " INTEGER)";
    }

    public static String deleteTable() {
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    static void storeZeroForIdType(SQLiteDatabase writableDatabase, String idType) {
        writableDatabase.insert(TABLE_NAME, null, from(0, idType));
    }

    private static ContentValues from(int idCounter, String idType) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_ID_TYPE, idType);
        contentValues.put(COLUMN_NAME_ID_COUNTER, idCounter);
        return contentValues;
    }

    // Atomically gets the latest id for the given idType, and increments its value in the database.
    public static int getLatestAndIncrement(AndroidLogger logger, SQLiteDatabase writableDatabase, String idType) {
        int nextId;
        writableDatabase.beginTransaction();
        try {
            nextId = getLatest(logger, writableDatabase, idType);
            writableDatabase.insert(TABLE_NAME, null, from(nextId + 1, idType));
            writableDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            throw new RuntimeException("couldn't get and increment the latest id counter for id type '" + idType + "'.", e);
        } finally {
            writableDatabase.endTransaction();
        }
        return nextId;
    }

    public static int getLatest(AndroidLogger logger, SQLiteDatabase readableDatabase, String idType) {
        String selectionColumnName = "maxCounter";
        String[] whereArgs = {idType};
        Cursor cursor = readableDatabase.rawQuery(
                "SELECT " + _ID + ", MAX(" + COLUMN_NAME_ID_COUNTER + ") AS " + selectionColumnName + " " +
                        "FROM " + TABLE_NAME + " " +
                        "WHERE " + COLUMN_NAME_ID_TYPE + " LIKE ?", whereArgs);
        cursor.moveToFirst();
        int latestId = cursor.getInt(cursor.getColumnIndex(selectionColumnName));
        logger.i(LOG_TAG, "latest id for type '" + idType + "' is " + latestId);
        cursor.close();
        return latestId;
    }

    public static void setToZero(SQLiteDatabase writableDatabase, String idType) {
        String whereClause = COLUMN_NAME_ID_TYPE + " LIKE ? AND " + COLUMN_NAME_ID_COUNTER + " > 0";
        String[] whereArgs = {idType};
        writableDatabase.delete(TABLE_NAME, whereClause, whereArgs);
    }
}
