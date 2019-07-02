package com.rrm.learnification;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ScheduleConfigurationStorage {
    static final String FILE_NAME = "schedule_configuration";
    private static final String LOG_TAG = "ScheduleConfigurationStorage";

    static final int defaultEarliestStartTimeDelayMs = 5000;
    static final int defaultLatestStartTimeDelayMs = 10000;
    private final AndroidLogger logger;
    private final AndroidInternalStorageAdaptor androidInternalStorageAdaptor;
    private final ScheduleConfigurationFileParser scheduleConfigurationFileParser;

    ScheduleConfigurationStorage(AndroidLogger logger, AndroidInternalStorageAdaptor androidInternalStorageAdaptor) {
        this.logger = logger;
        this.androidInternalStorageAdaptor = androidInternalStorageAdaptor;
        this.scheduleConfigurationFileParser = new ScheduleConfigurationFileParser();
    }

    PeriodicityRange getPeriodicityRange() {
        try {
            if (!androidInternalStorageAdaptor.doesFileExist(FILE_NAME)) {
                List<String> defaultLines = new ArrayList<>();
                defaultLines.add("earliestStartTimeDelayMs=" + defaultEarliestStartTimeDelayMs);
                defaultLines.add("latestStartTimeDelayMs=" + defaultLatestStartTimeDelayMs);
                androidInternalStorageAdaptor.appendLines(FILE_NAME, defaultLines);
                return new PeriodicityRange(defaultEarliestStartTimeDelayMs, defaultLatestStartTimeDelayMs);
            }

            List<String> lines = androidInternalStorageAdaptor.readLines(FILE_NAME);
            return scheduleConfigurationFileParser.parseSchedulingConfigurationFile(lines);
        } catch (IOException e) {
            logger.e(LOG_TAG, e);
            return new PeriodicityRange(defaultEarliestStartTimeDelayMs, defaultLatestStartTimeDelayMs);
        }
    }
}
