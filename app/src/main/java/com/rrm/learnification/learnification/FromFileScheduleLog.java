package com.rrm.learnification.learnification;

import com.rrm.learnification.common.AndroidLogger;
import com.rrm.learnification.common.FileStorageAdaptor;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

public class FromFileScheduleLog implements ScheduleLog {
    public static final String LATEST_SCHEDULED_LEARNIFICATION_FILE_NAME = "latest_scheduled_learnification";
    private static final String LOG_TAG = "FromFileScheduleLog";
    private final AndroidLogger logger;
    private final FileStorageAdaptor fileStorageAdaptor;
    private final Clock clock;

    public FromFileScheduleLog(AndroidLogger logger, FileStorageAdaptor fileStorageAdaptor, Clock clock) {
        this.logger = logger;
        this.fileStorageAdaptor = fileStorageAdaptor;
        this.clock = clock;
    }

    @Override
    public boolean isAnythingScheduledForTomorrow() {
        try {
            LocalDateTime currentlyStoredLatestScheduledTime = currentlyStoredLatestScheduledTime();
            logger.v(LOG_TAG, "latest scheduled learnification is at " + currentlyStoredLatestScheduledTime.toString());
            int latestScheduledDayOfMonth = currentlyStoredLatestScheduledTime.getDayOfMonth();
            int clockDayOfMonth = now().getDayOfMonth();
            return latestScheduledDayOfMonth - clockDayOfMonth == 1;
        } catch (IOException e) {
            logger.e(LOG_TAG, e);
            return false;
        }
    }

    @Override
    public void mark(LocalDateTime scheduledTime) {
        try {
            try {
                LocalDateTime currentlyStoredLatestScheduledTime = currentlyStoredLatestScheduledTime();
                if (scheduledTime.isAfter(currentlyStoredLatestScheduledTime)) {
                    fileStorageAdaptor.overwriteLines(LATEST_SCHEDULED_LEARNIFICATION_FILE_NAME, Collections.singletonList(scheduledTime.toString()));
                }
            } catch (IOException e) {
                fileStorageAdaptor.overwriteLines(LATEST_SCHEDULED_LEARNIFICATION_FILE_NAME, Collections.singletonList(scheduledTime.toString()));
            }
        } catch (IOException e) {
            logger.e(LOG_TAG, e);
        }
    }

    private LocalDateTime now() {
        return LocalDateTime.ofInstant(clock.instant(), ZoneId.systemDefault());
    }

    private LocalDateTime currentlyStoredLatestScheduledTime() throws IOException {
        List<String> lines = fileStorageAdaptor.readLines(LATEST_SCHEDULED_LEARNIFICATION_FILE_NAME);
        String line = lines.get(0);
        return LocalDateTime.parse(line);
    }
}
