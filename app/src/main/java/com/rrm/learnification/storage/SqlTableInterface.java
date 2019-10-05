package com.rrm.learnification.storage;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

interface SqlTableInterface<T> {
    List<T> readAll(SQLiteDatabase readableDatabase);

    void write(SQLiteDatabase writableDatabase, T item);

    void writeAll(SQLiteDatabase writableDatabase, List<T> items);

    void deleteAll(SQLiteDatabase writableDatabase);

    void delete(SQLiteDatabase writableDatabase, T item);
}
