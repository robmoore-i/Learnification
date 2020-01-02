package com.rrm.learnification.settings.learnificationdelay;

import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.settings.SettingsRepository;

import java.sql.Time;

public class ScheduleConfiguration {
    public static final int MAXIMUM_ACCEPTABLE_DELAY_SECONDS = 10;

    private static final String LOG_TAG = "ScheduleConfiguration";
    private final AndroidLogger logger;
    private final SettingsRepository settingsRepository;

    public ScheduleConfiguration(AndroidLogger logger, SettingsRepository settingsRepository) {
        this.logger = logger;
        this.settingsRepository = settingsRepository;
    }

    public DelayRange getDelayRange() {
        logger.v(LOG_TAG, "getting learnification delay range from settings repository");

        int delayInSeconds = settingsRepository.readDelaySeconds();
        int delayInMs = delayInSeconds * 1000;
        return new DelayRange(delayInMs, delayInMs + (MAXIMUM_ACCEPTABLE_DELAY_SECONDS * 1000));
    }

    public static DelayRange getImminentDelayRange() {
        return new DelayRange(1, 1);
    }

    public Time getFirstLearnificationTime() {
        return Time.valueOf("09:00:00");
    }
}
