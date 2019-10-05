package com.rrm.learnification.schedulelog;

import com.rrm.learnification.common.AndroidClock;
import com.rrm.learnification.common.AndroidLogger;
import com.rrm.learnification.storage.FileStorageAdaptor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class FromFileScheduleLog implements ScheduleLog {
    private static final String LOG_TAG = "FromFileScheduleLog";

    public static final String LATEST_SCHEDULED_LEARNIFICATION_FILE_NAME = "latest_scheduled_learnification";

    private final AndroidLogger logger;
    private final FileStorageAdaptor fileStorageAdaptor;
    private final AndroidClock clock;

    public FromFileScheduleLog(AndroidLogger logger, FileStorageAdaptor fileStorageAdaptor, AndroidClock clock) {
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
            int clockDayOfMonth = clock.now().getDayOfMonth();
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

    private LocalDateTime currentlyStoredLatestScheduledTime() throws IOException {
        List<String> lines = fileStorageAdaptor.readLines(LATEST_SCHEDULED_LEARNIFICATION_FILE_NAME);
        String line = lines.get(0);
        return LocalDateTime.parse(line);
    }
}
