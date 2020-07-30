package com.rrm.learnification.learnificationresultstorage;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rrm.learnification.sqlitedatabase.LearnificationAppDatabase;

import java.util.ArrayList;
import java.util.List;

public class LearnificationResultSqlTableClient implements LearnificationResultProvider {
    private final LearnificationAppDatabase learnificationAppDatabase;

    public LearnificationResultSqlTableClient(LearnificationAppDatabase learnificationAppDatabase) {
        this.learnificationAppDatabase = learnificationAppDatabase;
    }

    public void write(LearnificationResult learnificationResult) {
        LearnificationResultSqlTable.store(learnificationAppDatabase.getWritableDatabase(), learnificationResult);
    }

    @Override
    public List<LearnificationResult> readAll() {
        Cursor cursor = LearnificationResultSqlTable.readAll(learnificationAppDatabase.getReadableDatabase());
        ArrayList<LearnificationResult> learnificationResults = new ArrayList<>();
        while (cursor.moveToNext()) {
            learnificationResults.add(LearnificationResultSqlTable.extract(cursor));
        }
        cursor.close();
        return learnificationResults;
    }

    public void deleteAll() {
        LearnificationResultSqlTable.deleteAll(learnificationAppDatabase.getWritableDatabase());
    }

    public void writeAll(List<LearnificationResult> learnificationResults) {
        SQLiteDatabase writableDatabase = learnificationAppDatabase.getWritableDatabase();
        writableDatabase.beginTransaction();
        try {
            for (LearnificationResult learnificationResult : learnificationResults) {
                write(learnificationResult);
            }
            writableDatabase.setTransactionSuccessful();
        } finally {
            writableDatabase.endTransaction();
        }
    }
}
