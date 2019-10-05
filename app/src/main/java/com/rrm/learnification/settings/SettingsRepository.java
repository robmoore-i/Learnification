package com.rrm.learnification.settings;

import com.rrm.learnification.common.AndroidLogger;
import com.rrm.learnification.storage.FileStorageAdaptor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SettingsRepository {
    private static final String LOG_TAG = "SettingsRepository";

    public static final String LEARNIFICATION_DELAY_FILE_NAME = "settings_delay";
    static final int DEFAULT_LEARNIFICATION_DELAY_SECONDS = 5;

    private final AndroidLogger logger;
    private final FileStorageAdaptor fileStorageAdaptor;

    public SettingsRepository(AndroidLogger logger, FileStorageAdaptor fileStorageAdaptor) {
        this.logger = logger;
        this.fileStorageAdaptor = fileStorageAdaptor;
    }

    void writePeriodicity(int learnificationDelayInSeconds) {
        logger.v(LOG_TAG, "writing periodicity as " + learnificationDelayInSeconds + " seconds");

        try {
            fileStorageAdaptor.overwriteLines(LEARNIFICATION_DELAY_FILE_NAME, Collections.singletonList("learnificationDelayInSeconds=" + learnificationDelayInSeconds));
        } catch (Exception e) {
            logger.v(LOG_TAG, "failed to write periodicity (" + learnificationDelayInSeconds + ") to file (" + LEARNIFICATION_DELAY_FILE_NAME + ")");
            logger.e(LOG_TAG, e);
        }
    }

    public int readDelaySeconds() {
        try {
            List<String> lines = fileStorageAdaptor.readLines(LEARNIFICATION_DELAY_FILE_NAME).stream().filter(line -> !line.isEmpty()).collect(Collectors.toList());
            return Integer.parseInt(lines.get(0).split("=")[1]);
        } catch (Exception e) {
            logger.e(LOG_TAG, e);
            logger.v(LOG_TAG, "returning default learnification delay in seconds (" + DEFAULT_LEARNIFICATION_DELAY_SECONDS + ")");
            return DEFAULT_LEARNIFICATION_DELAY_SECONDS;
        }
    }

    int getInitialLearnificationDelayPickerValue() {
        int valueReadFromSettings = readDelaySeconds() / 60;
        if (valueReadFromSettings == 0) {
            return 5;
        } else {
            return valueReadFromSettings;
        }
    }
}
