package com.rrm.learnification;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
        androidLogger.v(LOG_TAG, "getting learnifications from file");

        if (!androidStorage.doesFileExist(FILE_NAME)) {
            androidStorage.createNewEmptyFile(FILE_NAME);
        }

        LearningItemTemplate learningItemTemplate = new LearningItemTemplate("What is the capital city of", "Which country has the capital city");
        ArrayList<LearningItem> learningItems = new ArrayList<>();
        learningItems.add(learningItemTemplate.build("Egypt", "Cairo"));
        learningItems.add(learningItemTemplate.build("Great Britain", "London"));
        learningItems.add(learningItemTemplate.build("Georgia", "Tbilisi"));
        return learningItems;
    }

    @Override
    public void write(LearningItem learningItems) {

    }

    private File getFile() {
        return new File(androidStorage.directory(), FILE_NAME);
    }
}
