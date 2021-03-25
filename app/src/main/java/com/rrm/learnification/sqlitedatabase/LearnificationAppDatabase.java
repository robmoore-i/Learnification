package com.rrm.learnification.sqlitedatabase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rrm.learnification.idgenerator.serializable.IdGeneratorSqlTable;
import com.rrm.learnification.learnificationresultstorage.LearnificationResultSqlTable;
import com.rrm.learnification.learningitemstorage.LearningItemSqlTable;

public class LearnificationAppDatabase extends SQLiteOpenHelper {
    /*

    If you change the database schema in any way, you must increment the database version.

    */
    private static final int DATABASE_VERSION = 5;
    private static final String DATABASE_NAME = "LearnificationApps.db";

    public LearnificationAppDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(LearningItemSqlTable.createTable());
        db.execSQL(LearnificationResultSqlTable.createTable());
        db.execSQL(IdGeneratorSqlTable.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(LearningItemSqlTable.deleteTable());
        db.execSQL(LearnificationResultSqlTable.deleteTable());
        db.execSQL(IdGeneratorSqlTable.deleteTable());
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(LearningItemSqlTable.deleteTable());
        db.execSQL(LearnificationResultSqlTable.deleteTable());
        db.execSQL(IdGeneratorSqlTable.deleteTable());
        onCreate(db);
    }
}
