package com.rrm.learnification;

import android.content.Context;
import android.content.ContextWrapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

class AndroidInternalStorageAdaptor implements FileStorageAdaptor {
    private static final String LOG_TAG = "AndroidInternalStorageAdaptor";

    private final ContextWrapper contextWrapper;
    private final AndroidLogger logger;

    AndroidInternalStorageAdaptor(AndroidLogger logger, ContextWrapper contextWrapper) {
        this.contextWrapper = contextWrapper;
        this.logger = logger;
    }

    @Override
    public boolean doesFileExist(String fileName) {
        logger.v(LOG_TAG, "checking if file exists '" + fileName + "'");

        return new File(contextWrapper.getFilesDir(), fileName).exists();
    }

    @Override
    public void appendLines(String fileName, List<String> lines) throws IOException {
        logger.v(LOG_TAG, "appending lines to '" + fileName + "'");

        try {
            FileOutputStream fileOutputStream = contextWrapper.openFileOutput(fileName, Context.MODE_APPEND | Context.MODE_PRIVATE);
            String fileContent = String.join("\n", lines) + "\n";
            fileOutputStream.write(fileContent.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            logger.e(LOG_TAG, e);
            throw e;
        }
    }

    @Override
    public List<String> readLines(String fileName) throws IOException {
        logger.v(LOG_TAG, "reading lines from '" + fileName + "'");

        try {
            FileInputStream fileInputStream = contextWrapper.openFileInput(fileName);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            ArrayList<String> lines = new ArrayList<>();
            String line = bufferedReader.readLine();
            while (line != null) {
                logger.v(LOG_TAG, "read line from file '" + line + "'");
                lines.add(line);
                line = bufferedReader.readLine();
            }

            return lines;
        } catch (IOException e) {
            logger.e(LOG_TAG, e);
            throw e;
        }
    }

    @Override
    public void deleteFile(String fileName) {
        logger.v(LOG_TAG, "deleting file '" + fileName + "'");

        File file = new File(contextWrapper.getFilesDir(), fileName);
        if (file.exists()) {
            boolean success = file.delete();
            if (!success) {
                logger.v(LOG_TAG, "file.delete() returned false for file '" + fileName + "'");
            }
        }
    }

    @Override
    public void overwriteLines(String fileName, List<String> lines) throws IOException {
        deleteFile(fileName);
        appendLines(fileName, lines);
    }
}
