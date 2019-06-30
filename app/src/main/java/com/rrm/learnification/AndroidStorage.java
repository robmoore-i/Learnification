package com.rrm.learnification;

import android.content.Context;
import android.content.ContextWrapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

class AndroidStorage {
    private final ContextWrapper activity;

    AndroidStorage(ContextWrapper activity) {
        this.activity = activity;
    }

    File directory() {
        return activity.getFilesDir();
    }

    FileOutputStream openFileOutput(String fileName) throws FileNotFoundException {
        return activity.openFileOutput(fileName, Context.MODE_PRIVATE);
    }

    void createNewEmptyFile(String fileName) {
    }

    boolean doesFileExist(String fileName) {
        return false;
    }
}
