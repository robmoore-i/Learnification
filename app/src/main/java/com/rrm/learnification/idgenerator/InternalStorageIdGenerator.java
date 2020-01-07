package com.rrm.learnification.idgenerator;

import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.storage.FileStorageAdaptor;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.max;

public class InternalStorageIdGenerator implements IdGenerator {
    private static final String LOG_TAG = "InternalStorageIdGenerator";

    private final AndroidLogger logger;
    private final FileStorageAdaptor fileStorageAdaptor;

    /**
     * This file stores the id for the next generated one, i.e. the id stored in this file does not
     * correspond to an an id which has been returned yet.
     */
    private final String fileName;
    /**
     * This stores the next id to be generated. At any time, the value in this variable represents
     * an id that has not yet been given for use.
     */
    private int nextId = 0;

    public InternalStorageIdGenerator(AndroidLogger logger, FileStorageAdaptor fileStorageAdaptor, String idType) {
        this.logger = logger;
        this.fileStorageAdaptor = fileStorageAdaptor;
        this.fileName = "generated-next-id-" + idType;
    }

    @Override
    public int nextId() {
        int returnValue = nextId;
        incrementNextId();
        return returnValue;
    }

    @Override
    public int lastId() {
        int nextId;
        try {
            nextId = readFromStorage();
        } catch (IOException e) {
            nextId = this.nextId;
        }
        return max(0, nextId - 1);
    }

    private int readFromStorage() throws IOException {
        try {
            List<String> lines = fileStorageAdaptor.readLines(fileName);
            return Integer.parseInt(lines.get(0));
        } catch (IOException e) {
            logger.v(LOG_TAG, "couldn't get latest generated id from file '" + fileName + "'");
            logger.e(LOG_TAG, e);
            throw e;
        }
    }

    private void saveToStorage(int idToSave) {
        try {
            fileStorageAdaptor.overwriteLines(fileName, Collections.singletonList(String.valueOf(idToSave)));
        } catch (IOException e) {
            logger.v(LOG_TAG, "couldn't write next generated id from file '" + fileName + "'");
            logger.e(LOG_TAG, e);
        }
    }

    private void incrementNextId() {
        try {
            int lastId = readFromStorage();
            nextId = lastId + 1;
        } catch (IOException e) {
            nextId += 1;
        }
        saveToStorage(nextId);
    }

    public void reset() {
        fileStorageAdaptor.deleteFile(fileName);
        nextId = 0;
    }
}
