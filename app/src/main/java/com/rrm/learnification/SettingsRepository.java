package com.rrm.learnification;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

class SettingsRepository {
    private static final String LOG_TAG = "SettingsRepository";

    private final String PERIODICITY_FILE = "settings_periodicity";
    private final AndroidLogger logger;
    private final FileStorageAdaptor fileStorageAdaptor;

    SettingsRepository(AndroidLogger logger, FileStorageAdaptor fileStorageAdaptor) {
        this.logger = logger;
        this.fileStorageAdaptor = fileStorageAdaptor;
    }

    void writePeriodicity(int periodicityInSeconds) {
        logger.v(LOG_TAG, "writing periodicity as " + periodicityInSeconds + " seconds");

        try {
            fileStorageAdaptor.appendLines(PERIODICITY_FILE, Collections.singletonList("periodicityInSeconds=" + periodicityInSeconds));
        } catch (IOException e) {
            logger.v(LOG_TAG, "failed to write periodicity (" + periodicityInSeconds + ") to file (" + PERIODICITY_FILE + ")");
            logger.e(LOG_TAG, e);
        }
    }

    int readPeriodicitySeconds() {
        try {
            List<String> lines = fileStorageAdaptor.readLines(PERIODICITY_FILE);
            return Integer.parseInt(lines.get(0).split("=")[1]);
        } catch (IOException e) {
            logger.e(LOG_TAG, e);
            int defaultPeriodicity = 5;
            logger.v(LOG_TAG, "returning default periodicity in seconds (" + defaultPeriodicity + ")");
            return defaultPeriodicity;
        }
    }
}
