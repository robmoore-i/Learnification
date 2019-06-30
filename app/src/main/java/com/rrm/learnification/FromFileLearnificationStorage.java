package com.rrm.learnification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class FromFileLearnificationStorage implements LearnificationStorage {
    private static final String LOG_TAG = "FromFileLearnificationStorage";
    static final String FILE_NAME = "learning_items";

    private final AndroidLogger androidLogger;
    private final AndroidStorage androidStorage;

    FromFileLearnificationStorage(AndroidLogger androidLogger, AndroidStorage androidStorage) {
        this.androidLogger = androidLogger;
        this.androidStorage = androidStorage;
    }

    @Override
    public List<LearningItem> read() {
        androidLogger.v(LOG_TAG, "reading learnifications from file");

        if (!androidStorage.doesFileExist(FILE_NAME)) {
            androidStorage.createNewEmptyFile(FILE_NAME);

            List<String> lines = defaultLearningItems().stream().map(LearningItem::asSingleString).collect(Collectors.toList());
            androidStorage.appendLines(FILE_NAME, lines);
        }

        return defaultLearningItems();
    }

    @Override
    public void write(LearningItem learningItems) {

    }

    static ArrayList<LearningItem> defaultLearningItems() {
        ArrayList<LearningItem> learningItems = new ArrayList<>();
        learningItems.add(new LearningItem("What is the capital city of Egypt?", "Cairo"));
        learningItems.add(new LearningItem("What is the capital city of Great Britain?", "London"));
        learningItems.add(new LearningItem("What is the capital city of Georgia?", "Tbilisi"));
        return learningItems;
    }
}
