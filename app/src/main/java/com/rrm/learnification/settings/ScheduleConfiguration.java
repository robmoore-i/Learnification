package com.rrm.learnification.settings;

import com.rrm.learnification.common.AndroidLogger;

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

    public DelayRange getImminentDelayRange() {
        return new DelayRange(0, MAXIMUM_ACCEPTABLE_DELAY_SECONDS * 1000);
    }

    public Time getFirstLearnificationTime() {
        return Time.valueOf("09:00:00");
    }
}
