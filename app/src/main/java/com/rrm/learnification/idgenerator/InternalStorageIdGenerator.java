package com.rrm.learnification.idgenerator;

import com.rrm.learnification.files.FileStorageAdaptor;
import com.rrm.learnification.logger.AndroidLogger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class InternalStorageIdGenerator implements IdGenerator {
    private static final String LOG_TAG = "InternalStorageIdGenerator";

    private final AndroidLogger logger;
    private final FileStorageAdaptor fileStorageAdaptor;

    private final String fileName;

    private int nextId = 0;

    public InternalStorageIdGenerator(AndroidLogger logger, FileStorageAdaptor fileStorageAdaptor, String idType) {
        this.logger = logger;
        this.fileStorageAdaptor = fileStorageAdaptor;
        this.fileName = "generated-next-id-" + idType;
    }

    @Override
    public int nextId() {
        int nextId;
        try {
            nextId = readFromStorage();
        } catch (FileNotFoundException e) {
            logger.i(LOG_TAG, "Couldn't find the file storing the set of used ids, so resetting to 0");
            nextId = 0;
        } catch (IOException e) {
            nextId = this.nextId;
        }

        this.nextId = nextId + 1;
        saveToStorage(this.nextId);
        return nextId;
    }

    @Override
    public int lastId() {
        try {
            return readFromStorage() - 1;
        } catch (FileNotFoundException e) {
            return 0;
        } catch (IOException e) {
            return this.nextId - 1;
        }
    }

    @Override
    public void reset() {
        fileStorageAdaptor.deleteFile(fileName);
        nextId = 0;
    }

    private int readFromStorage() throws IOException {
        try {
            List<String> lines = fileStorageAdaptor.readLines(fileName);
            return Integer.parseInt(lines.get(0));
        } catch (IOException e) {
            logger.i(LOG_TAG, "couldn't get latest generated id from file '" + fileName + "'");
            throw e;
        }
    }

    private void saveToStorage(int idToSave) {
        try {
            logger.i(LOG_TAG, "saving id " + idToSave + " into storage");
            fileStorageAdaptor.overwriteLines(fileName, Collections.singletonList(String.valueOf(idToSave)));
        } catch (IOException e) {
            logger.i(LOG_TAG, "couldn't write next generated id from file '" + fileName + "'");
            logger.e(LOG_TAG, e);
        }
    }
}
