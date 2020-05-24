package com.rrm.learnification.learnificationresultstorage;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class LearnificationResultSqlTable implements BaseColumns {

    private static final String TABLE_NAME = "learnificationresult";
    private static final String COLUMN_NAME_TIME_SUBMITTED = "time_submitted";
    private static final String COLUMN_NAME_RESULT = "result";
    private static final String COLUMN_NAME_GIVEN = "given";
    private static final String COLUMN_NAME_EXPECTED = "expected";

    private LearnificationResultSqlTable() {
    }

    public static String createTable() {
        return "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME_TIME_SUBMITTED + " TEXT," +
                COLUMN_NAME_RESULT + " TEXT," +
                COLUMN_NAME_GIVEN + " TEXT," +
                COLUMN_NAME_EXPECTED + " TEXT)";
    }

    public static String deleteTable() {
        return "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    static void store(SQLiteDatabase writableDatabase, LearnificationResult learnificationResult) {
        writableDatabase.insert(TABLE_NAME, null, from(learnificationResult));
    }

    static Cursor readAll(SQLiteDatabase readableDatabase) {
        return readableDatabase.query(TABLE_NAME,
                new String[]{_ID, COLUMN_NAME_TIME_SUBMITTED, COLUMN_NAME_RESULT, COLUMN_NAME_GIVEN, COLUMN_NAME_EXPECTED},
                null, null, null, null, null);
    }

    static LearnificationResult extract(Cursor cursor) {
        return new LearnificationResult(
                LocalDateTime.from(DateTimeFormatter.ISO_DATE_TIME.parse(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_TIME_SUBMITTED)))),
                LearnificationResultEvaluation.parse(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_RESULT))),
                new LearnificationPrompt(
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_GIVEN)), cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_EXPECTED)))
        );
    }

    private static ContentValues from(LearnificationResult learnificationResult) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_TIME_SUBMITTED, learnificationResult.timeSubmitted.format(DateTimeFormatter.ISO_DATE_TIME));
        contentValues.put(COLUMN_NAME_RESULT, learnificationResult.result.name());
        contentValues.put(COLUMN_NAME_GIVEN, learnificationResult.given);
        contentValues.put(COLUMN_NAME_EXPECTED, learnificationResult.expected);
        return contentValues;
    }

    static void deleteAll(SQLiteDatabase writableDatabase) {
        writableDatabase.delete(TABLE_NAME, null, null);
    }
}
