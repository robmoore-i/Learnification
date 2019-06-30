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
    private final AndroidStorage androidStorage;

    FromFileLearnificationStorage(AndroidLogger androidLogger, AndroidStorage androidStorage) {
        this.logger = androidLogger;
        this.androidStorage = androidStorage;
    }

    @Override
    public List<LearningItem> read() {
        logger.v(LOG_TAG, "reading learnifications from file");

        try {
            if (!androidStorage.doesFileExist(FILE_NAME)) {
                androidStorage.createNewEmptyFile(FILE_NAME);

                List<String> lines = defaultLearningItems().stream().map(LearningItem::asSingleString).collect(Collectors.toList());
                androidStorage.appendLines(FILE_NAME, lines);
            }

            return androidStorage.readLines(FILE_NAME).stream().filter(line -> !line.isEmpty()).map(LearningItem::fromLine).collect(Collectors.toList());
        } catch (IOException e) {
            logger.e(LOG_TAG, e);
            return new ArrayList<>();
        }
    }

    @Override
    public void write(LearningItem learningItem) {
        logger.v(LOG_TAG, "writing a learning-item '" + learningItem.asSingleString() + "'");

        try {
            androidStorage.appendLines(FILE_NAME, Collections.singletonList(learningItem.asSingleString()));
        } catch (IOException e) {
            logger.e(LOG_TAG, e);
        }
    }

    @Override
    public void rewrite(List<LearningItem> learningItems) {
        logger.v(LOG_TAG, "rewriting learning items");

        androidStorage.deleteFile(FILE_NAME);
        try {
            androidStorage.appendLines(FILE_NAME, learningItems.stream().map(LearningItem::asSingleString).collect(Collectors.toList()));
        } catch (IOException e) {
            logger.e(LOG_TAG, e);
        }
    }

    static ArrayList<LearningItem> defaultLearningItems() {
        ArrayList<LearningItem> learningItems = new ArrayList<>();
        learningItems.add(new LearningItem("What is the capital city of Egypt?", "Cairo"));
        learningItems.add(new LearningItem("What is the capital city of Great Britain?", "London"));
        learningItems.add(new LearningItem("What is the capital city of Georgia?", "Tbilisi"));
        return learningItems;
    }
}
