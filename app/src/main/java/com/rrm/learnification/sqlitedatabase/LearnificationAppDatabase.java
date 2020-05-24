package com.rrm.learnification.sqlitedatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rrm.learnification.learningitemstorage.LearningItemSqlTable;

public class LearnificationAppDatabase extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2; // If you change the database schema, you must increment the database version.
    private static final String DATABASE_NAME = "LearnificationApps.db";

    public LearnificationAppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LearningItemSqlTable.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(LearningItemSqlTable.deleteTable());
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(LearningItemSqlTable.deleteTable());
        onCreate(db);
    }
}