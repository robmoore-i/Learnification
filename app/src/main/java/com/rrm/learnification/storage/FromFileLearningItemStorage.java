package com.rrm.learnification.storage;

import com.rrm.learnification.common.AndroidLogger;
import com.rrm.learnification.common.LearningItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FromFileLearningItemStorage implements LearningItemStorage {
    private static final String LOG_TAG = "FromFileLearningItemStorage";
    public static final String LEARNING_ITEMS_FILE_NAME = "learning_items";

    private final AndroidLogger logger;
    private final FileStorageAdaptor fileStorageAdaptor;

    public FromFileLearningItemStorage(AndroidLogger androidLogger, FileStorageAdaptor fileStorageAdaptor) {
        this.logger = androidLogger;
        this.fileStorageAdaptor = fileStorageAdaptor;
    }

    @Override
    public List<LearningItem> read() {
        logger.v(LOG_TAG, "reading learnifications from file");

        try {
            if (!fileStorageAdaptor.doesFileExist(LEARNING_ITEMS_FILE_NAME)) {
                List<String> lines = defaultLearningItems().stream().map(LearningItem::asSingleString).collect(Collectors.toList());
                fileStorageAdaptor.appendLines(LEARNING_ITEMS_FILE_NAME, lines);
            }

            return fileStorageAdaptor.readLines(LEARNING_ITEMS_FILE_NAME).stream().map(LearningItem::fromLine).filter(learningItem -> !learningItem.isEmpty()).collect(Collectors.toList());
        } catch (IOException e) {
            logger.e(LOG_TAG, e);
            return new ArrayList<>();
        }
    }

    @Override
    public void write(LearningItem learningItem) {
        logger.v(LOG_TAG, "writing a learning-item '" + learningItem.asSingleString() + "'");

        try {
            fileStorageAdaptor.appendLines(LEARNING_ITEMS_FILE_NAME, Collections.singletonList(learningItem.asSingleString()));
        } catch (IOException e) {
            logger.e(LOG_TAG, e);
        }
    }

    @Override
    public void remove(List<LearningItem> learningItems, int index) {
        rewrite(learningItems);
    }

    public static ArrayList<LearningItem> defaultLearningItems() {
        ArrayList<LearningItem> learningItems = new ArrayList<>();
        learningItems.add(new LearningItem("live", "ცხოვრობ"));
        learningItems.add(new LearningItem("travel", "მგზავრობ"));
        learningItems.add(new LearningItem("use", "ხმარობ"));
        learningItems.add(new LearningItem("exercise", "ვარჯიშობ"));
        return learningItems;
    }

    public void rewrite(List<LearningItem> learningItems) {
        logger.v(LOG_TAG, "rewriting learning items");

        fileStorageAdaptor.deleteFile(LEARNING_ITEMS_FILE_NAME);
        try {
            fileStorageAdaptor.appendLines(LEARNING_ITEMS_FILE_NAME, learningItems.stream().map(LearningItem::asSingleString).collect(Collectors.toList()));
        } catch (IOException e) {
            logger.e(LOG_TAG, e);
        }
    }
}
