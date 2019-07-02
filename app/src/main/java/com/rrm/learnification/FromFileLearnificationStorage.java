package com.rrm.learnification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class FromFileLearnificationStorage implements LearnificationStorage {
    private static final String LOG_TAG = "FromFileLearnificationStorage";
    static final String FILE_NAME = "learning_items";

    private final AndroidLogger logger;
    private final AndroidInternalStorageAdaptor androidInternalStorageAdaptor;

    FromFileLearnificationStorage(AndroidLogger androidLogger, AndroidInternalStorageAdaptor androidInternalStorageAdaptor) {
        this.logger = androidLogger;
        this.androidInternalStorageAdaptor = androidInternalStorageAdaptor;
    }

    @Override
    public List<LearningItem> read() {
        logger.v(LOG_TAG, "reading learnifications from file");

        try {
            if (!androidInternalStorageAdaptor.doesFileExist(FILE_NAME)) {
                List<String> lines = defaultLearningItems().stream().map(LearningItem::asSingleString).collect(Collectors.toList());
                androidInternalStorageAdaptor.appendLines(FILE_NAME, lines);
            }

            return androidInternalStorageAdaptor.readLines(FILE_NAME).stream().filter(line -> !line.isEmpty()).map(LearningItem::fromLine).collect(Collectors.toList());
        } catch (IOException e) {
            logger.e(LOG_TAG, e);
            return new ArrayList<>();
        }
    }

    @Override
    public void write(LearningItem learningItem) {
        logger.v(LOG_TAG, "writing a learning-item '" + learningItem.asSingleString() + "'");

        try {
            androidInternalStorageAdaptor.appendLines(FILE_NAME, Collections.singletonList(learningItem.asSingleString()));
        } catch (IOException e) {
            logger.e(LOG_TAG, e);
        }
    }

    @Override
    public void remove(List<LearningItem> learningItems, int index) {
        rewrite(learningItems);
    }

    void rewrite(List<LearningItem> learningItems) {
        logger.v(LOG_TAG, "rewriting learning items");

        androidInternalStorageAdaptor.deleteFile(FILE_NAME);
        try {
            androidInternalStorageAdaptor.appendLines(FILE_NAME, learningItems.stream().map(LearningItem::asSingleString).collect(Collectors.toList()));
        } catch (IOException e) {
            logger.e(LOG_TAG, e);
        }
    }

    static ArrayList<LearningItem> defaultLearningItems() {
        ArrayList<LearningItem> learningItems = new ArrayList<>();
        learningItems.add(new LearningItem("speak", "ლაპარაკობ"));
        learningItems.add(new LearningItem("live", "ცხოვრობ"));
        learningItems.add(new LearningItem("think", "ფიქრობ"));
        learningItems.add(new LearningItem("travel", "მგზავრობ"));
        learningItems.add(new LearningItem("use", "ხმარობ"));
        learningItems.add(new LearningItem("work", "მუშაობ"));
        learningItems.add(new LearningItem("exercise", "ვარჯიშობ"));
        return learningItems;
    }
}
