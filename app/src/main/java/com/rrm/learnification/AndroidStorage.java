package com.rrm.learnification;

import android.content.ContextWrapper;

import java.util.List;

class AndroidStorage {
    private final ContextWrapper activity;

    AndroidStorage(ContextWrapper activity) {
        this.activity = activity;
    }

    void createNewEmptyFile(String fileName) {
    }

    boolean doesFileExist(String fileName) {
        return false;
    }

    void appendLines(String fileName, List<String> lines) {
    }
}
