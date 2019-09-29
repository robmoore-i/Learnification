package com.rrm.learnification.settings;

import com.rrm.learnification.common.AndroidLogger;
import com.rrm.learnification.common.FileStorageAdaptor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SettingsRepository {
    private static final String LOG_TAG = "SettingsRepository";

    public static final int DEFAULT_PERIODICITY_SECONDS = 5;
    public static final String PERIODICITY_FILE_NAME = "settings_periodicity";

    private final AndroidLogger logger;
    private final FileStorageAdaptor fileStorageAdaptor;

    public SettingsRepository(AndroidLogger logger, FileStorageAdaptor fileStorageAdaptor) {
        this.logger = logger;
        this.fileStorageAdaptor = fileStorageAdaptor;
    }

    void writePeriodicity(int periodicityInSeconds) {
        logger.v(LOG_TAG, "writing periodicity as " + periodicityInSeconds + " seconds");

        try {
            fileStorageAdaptor.overwriteLines(PERIODICITY_FILE_NAME, Collections.singletonList("periodicityInSeconds=" + periodicityInSeconds));
        } catch (Exception e) {
            logger.v(LOG_TAG, "failed to write periodicity (" + periodicityInSeconds + ") to file (" + PERIODICITY_FILE_NAME + ")");
            logger.e(LOG_TAG, e);
        }
    }

    public int readPeriodicitySeconds() {
        try {
            List<String> lines = fileStorageAdaptor.readLines(PERIODICITY_FILE_NAME).stream().filter(line -> !line.isEmpty()).collect(Collectors.toList());
            return Integer.parseInt(lines.get(0).split("=")[1]);
        } catch (Exception e) {
            logger.e(LOG_TAG, e);
            logger.v(LOG_TAG, "returning default periodicity in seconds (" + DEFAULT_PERIODICITY_SECONDS + ")");
            return DEFAULT_PERIODICITY_SECONDS;
        }
    }

    public int getInitialPeriodicityPickerValue() {
        int valueReadFromSettings = readPeriodicitySeconds() / 60;
        if (valueReadFromSettings == 0) {
            return 5;
        } else {
            return valueReadFromSettings;
        }
    }
}
