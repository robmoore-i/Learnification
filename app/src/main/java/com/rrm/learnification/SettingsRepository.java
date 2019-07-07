package com.rrm.learnification;

import java.util.Collections;
import java.util.List;

class SettingsRepository {
    private static final String LOG_TAG = "SettingsRepository";

    static final int DEFAULT_PERIODICITY = 5;

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
            fileStorageAdaptor.overwriteLines(PERIODICITY_FILE, Collections.singletonList("periodicityInSeconds=" + periodicityInSeconds));
        } catch (Exception e) {
            logger.v(LOG_TAG, "failed to write periodicity (" + periodicityInSeconds + ") to file (" + PERIODICITY_FILE + ")");
            logger.e(LOG_TAG, e);
        }
    }

    int readPeriodicitySeconds() {
        try {
            List<String> lines = fileStorageAdaptor.readLines(PERIODICITY_FILE);
            return Integer.parseInt(lines.get(0).split("=")[1]);
        } catch (Exception e) {
            logger.e(LOG_TAG, e);
            logger.v(LOG_TAG, "returning default periodicity in seconds (" + DEFAULT_PERIODICITY + ")");
            return DEFAULT_PERIODICITY;
        }
    }
}
