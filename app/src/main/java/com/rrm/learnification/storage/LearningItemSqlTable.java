package com.rrm.learnification.storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.rrm.learnification.common.LearningItem;

final class LearningItemSqlTable implements BaseColumns {
    static final String TABLE_NAME = "learningitem";
    private static final String COLUMN_NAME_LEFT = "left";
    private static final String COLUMN_NAME_RIGHT = "right";

    private LearningItemSqlTable() {
    }

    static String createTable() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_LEFT + " TEXT," +
                COLUMN_NAME_RIGHT + " TEXT)";
    }

    static String deleteTable() {
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    static ContentValues from(LearningItem learningItem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_LEFT, learningItem.left);
        contentValues.put(COLUMN_NAME_RIGHT, learningItem.right);
        return contentValues;
    }

    static Cursor all(SQLiteDatabase readableDatabase) {
        return readableDatabase.query(TABLE_NAME, new String[]{_ID, COLUMN_NAME_LEFT, COLUMN_NAME_RIGHT},
                null, null, null, null, null);
    }

    static LearningItem extract(Cursor cursor) {
        return new LearningItem(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_LEFT)), cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_RIGHT)));
    }

    static void delete(SQLiteDatabase writableDatabase, LearningItem learningItemToRemove) {
        String selection = COLUMN_NAME_LEFT + " LIKE ? AND " + COLUMN_NAME_RIGHT + " LIKE ?";
        String[] selectionArgs = {learningItemToRemove.left, learningItemToRemove.right};
        writableDatabase.delete(TABLE_NAME, selection, selectionArgs);
    }

    static void replace(SQLiteDatabase writableDatabase, LearningItem target, LearningItem replacement) {
        String selection = COLUMN_NAME_LEFT + " LIKE ? AND " + COLUMN_NAME_RIGHT + " LIKE ?";
        String[] selectionArgs = {target.left, target.right};
        writableDatabase.update(TABLE_NAME, from(replacement), selection, selectionArgs);
    }
}
