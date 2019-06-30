package com.rrm.learnification;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

class FromFileLearnificationProvider implements LearnificationProvider {
    private static final String LOG_TAG = "FromFileLearnificationProvider";

    private final AndroidLogger androidLogger;
    private final File filesDirectory;

    FromFileLearnificationProvider(AndroidLogger androidLogger, File filesDirectory) {
        this.androidLogger = androidLogger;
        this.filesDirectory = filesDirectory;
    }

    @Override
    public List<LearningItem> get() {
        androidLogger.v(LOG_TAG, "getting learnifications from file");
        LearningItemTemplate learningItemTemplate = new LearningItemTemplate("What is the capital city of", "Which country has the capital city");
        ArrayList<LearningItem> learningItems = new ArrayList<>();
        learningItems.add(learningItemTemplate.build("Egypt", "Cairo"));
        learningItems.add(learningItemTemplate.build("Great Britain", "London"));
        learningItems.add(learningItemTemplate.build("Georgia", "Tbilisi"));
        return learningItems;
    }
}
