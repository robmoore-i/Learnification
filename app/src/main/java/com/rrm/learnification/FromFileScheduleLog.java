package com.rrm.learnification;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

class FromFileScheduleLog implements ScheduleLog {
    static final String LATEST_SCHEDULED_LEARNIFICATION_FILE_NAME = "latest_scheduled_learnification";
    private static final String LOG_TAG = "FromFileScheduleLog";
    private final AndroidLogger logger;
    private final FileStorageAdaptor fileStorageAdaptor;
    private final Clock clock;

    FromFileScheduleLog(AndroidLogger logger, FileStorageAdaptor fileStorageAdaptor, Clock clock) {
        this.logger = logger;
        this.fileStorageAdaptor = fileStorageAdaptor;
        this.clock = clock;
    }

    @Override
    public boolean isAnythingScheduledForTomorrow() {
        try {
            List<String> lines = fileStorageAdaptor.readLines(LATEST_SCHEDULED_LEARNIFICATION_FILE_NAME);
            String line = lines.get(0);
            LocalDateTime localDateTime = LocalDateTime.parse(line);
            int latestScheduledDayOfMonth = localDateTime.getDayOfMonth();
            int clockDayOfMonth = LocalDateTime.ofInstant(clock.instant(), ZoneId.systemDefault()).getDayOfMonth();
            return latestScheduledDayOfMonth - clockDayOfMonth == 1;
        } catch (IOException e) {
            logger.e(LOG_TAG, e);
            return false;
        }
    }
}
