package com.rrm.learnification.storage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.logger.AndroidLogger;

import static com.rrm.learnification.assertion.Assert.assertTrue;

final class LearningItemSqlTable implements BaseColumns {
    private static final String LOG_TAG = "LearningItemSqlTable";

    private static final String TABLE_NAME = "learningitem";
    private static final String COLUMN_NAME_LEARNING_ITEM_SET_NAME = "learning_item_set";
    private static final String COLUMN_NAME_LEFT = "left";
    private static final String COLUMN_NAME_RIGHT = "right";

    private LearningItemSqlTable() {
    }

    static String createTable() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_LEARNING_ITEM_SET_NAME + " TEXT," +
                COLUMN_NAME_LEFT + " TEXT," +
                COLUMN_NAME_RIGHT + " TEXT)";
    }

    static String deleteTable() {
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    static Cursor all(SQLiteDatabase readableDatabase, String learningItemSetName) {
        String selection = COLUMN_NAME_LEARNING_ITEM_SET_NAME + " LIKE ?";
        String[] selectionArgs = {learningItemSetName};
        return readableDatabase.query(TABLE_NAME, new String[]{_ID, COLUMN_NAME_LEARNING_ITEM_SET_NAME, COLUMN_NAME_LEFT, COLUMN_NAME_RIGHT},
                selection, selectionArgs, null, null, null);
    }

    static Cursor all(SQLiteDatabase readableDatabase) {
        return readableDatabase.query(TABLE_NAME, new String[]{_ID, COLUMN_NAME_LEARNING_ITEM_SET_NAME, COLUMN_NAME_LEFT, COLUMN_NAME_RIGHT},
                null, null, null, null, null);
    }

    static LearningItem extract(Cursor cursor) {
        return new LearningItem(
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_LEFT)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_RIGHT)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_LEARNING_ITEM_SET_NAME))
        );
    }

    static void insert(SQLiteDatabase writableDatabase, LearningItem item) {
        writableDatabase.insert(LearningItemSqlTable.TABLE_NAME, null, from(item));
    }

    static void delete(SQLiteDatabase writableDatabase, LearningItem learningItemToRemove) {
        String selection = COLUMN_NAME_LEARNING_ITEM_SET_NAME + " LIKE ? AND " + COLUMN_NAME_LEFT + " LIKE ? AND " + COLUMN_NAME_RIGHT + " LIKE ?";
        String[] selectionArgs = {learningItemToRemove.learningItemSetName, learningItemToRemove.left, learningItemToRemove.right};
        writableDatabase.delete(TABLE_NAME, selection, selectionArgs);
    }

    static void replace(SQLiteDatabase writableDatabase, LearningItem target, LearningItem replacement) {
        assertTrue(target.learningItemSetName.equals(replacement.learningItemSetName), "attempted to replace learning item with one that isn't in " +
                "the same set. target=" + target.toString() + ", replacement=" + replacement.toString());
        String selection = COLUMN_NAME_LEARNING_ITEM_SET_NAME + " LIKE ? AND " + COLUMN_NAME_LEFT + " LIKE ? AND " + COLUMN_NAME_RIGHT + " LIKE ?";
        String[] selectionArgs = {target.learningItemSetName, target.left, target.right};
        writableDatabase.update(TABLE_NAME, from(replacement), selection, selectionArgs);
    }

    static void deleteAll(SQLiteDatabase writableDatabase, String learningItemSetName) {
        String whereClause = COLUMN_NAME_LEARNING_ITEM_SET_NAME + " LIKE ?";
        String[] whereArgs = {learningItemSetName};
        writableDatabase.delete(LearningItemSqlTable.TABLE_NAME, whereClause, whereArgs);
    }

    static void deleteAll(SQLiteDatabase writableDatabase) {
        writableDatabase.delete(LearningItemSqlTable.TABLE_NAME, null, null);
    }

    static String mostPopulousLearningItemSetName(SQLiteDatabase readableDatabase) {
        String viewName = "numberOfDistinctLearningItemSets";
        Cursor cursor =
                readableDatabase.rawQuery("SELECT " + COLUMN_NAME_LEARNING_ITEM_SET_NAME + ",(COUNT(DISTINCT " + COLUMN_NAME_LEARNING_ITEM_SET_NAME + ")) "
                        + "AS " + viewName + " FROM " + TABLE_NAME, null);
        cursor.moveToFirst();
        String mostPopulousLearningItemSetName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_LEARNING_ITEM_SET_NAME));
        cursor.close();
        if (mostPopulousLearningItemSetName == null) {
            return "default";
        } else {
            return mostPopulousLearningItemSetName;
        }
    }

    static Cursor learningItemSetNamesOrderedBySetSize(SQLiteDatabase readableDatabase) {
        return readableDatabase.rawQuery("SELECT " +
                COLUMN_NAME_LEARNING_ITEM_SET_NAME +
                ",(COUNT(*)) AS count" +
                " FROM " + TABLE_NAME +
                " GROUP BY " + COLUMN_NAME_LEARNING_ITEM_SET_NAME +
                " ORDER BY count DESC", null);
    }

    static void updateSetName(AndroidLogger logger, SQLiteDatabase writableDatabase, String learningItemSetName, String newLearningItemSetName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_LEARNING_ITEM_SET_NAME, newLearningItemSetName);
        String whereClause = COLUMN_NAME_LEARNING_ITEM_SET_NAME + " LIKE ?";
        String[] whereArgs = {learningItemSetName};
        int numberOfRowsAffected = writableDatabase.update(TABLE_NAME, contentValues, whereClause, whereArgs);
        logger.i(LOG_TAG, "updated learning item set name for " + numberOfRowsAffected + " items, from '" + learningItemSetName + "' " +
                "to '" + newLearningItemSetName + "'");
    }

    static String learningItemSetNameFrom(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex(COLUMN_NAME_LEARNING_ITEM_SET_NAME));
    }

    private static ContentValues from(LearningItem learningItem) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_LEARNING_ITEM_SET_NAME, learningItem.learningItemSetName);
        contentValues.put(COLUMN_NAME_LEFT, learningItem.left);
        contentValues.put(COLUMN_NAME_RIGHT, learningItem.right);
        return contentValues;
    }
}
