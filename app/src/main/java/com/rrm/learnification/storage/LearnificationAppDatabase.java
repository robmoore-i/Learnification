package com.rrm.learnification.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LearnificationAppDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1; // If you change the database schema, you must increment the database version.
    private static final String DATABASE_NAME = "LearnificationApps.db";

    public LearnificationAppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static String sql_create_entries() {
        return LearningItemSqlTable.createTable();
    }

    private static String sql_delete_entries() {
        return LearningItemSqlTable.deleteTable();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql_create_entries());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(sql_delete_entries());
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
