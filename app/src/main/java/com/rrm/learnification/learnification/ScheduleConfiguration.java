package com.rrm.learnification.learnification;

import com.rrm.learnification.common.AndroidLogger;
import com.rrm.learnification.settings.SettingsRepository;

import java.sql.Time;

public class ScheduleConfiguration {
    static final int MAXIMUM_ACCEPTABLE_DELAY_SECONDS = 10;

    private static final String LOG_TAG = "ScheduleConfiguration";
    private final AndroidLogger logger;
    private final SettingsRepository settingsRepository;

    public ScheduleConfiguration(AndroidLogger logger, SettingsRepository settingsRepository) {
        this.logger = logger;
        this.settingsRepository = settingsRepository;
    }

    DelayRange getPeriodicityRange() {
        logger.v(LOG_TAG, "getting periodicity range from settings repository");

        int periodicityInSeconds = settingsRepository.readPeriodicitySeconds();
        int periodicityMs = periodicityInSeconds * 1000;
        return new DelayRange(periodicityMs, periodicityMs + (MAXIMUM_ACCEPTABLE_DELAY_SECONDS * 1000));
    }

    DelayRange getImminentDelayRange() {
        return new DelayRange(0, MAXIMUM_ACCEPTABLE_DELAY_SECONDS * 1000);
    }

    Time getFirstLearnificationTime() {
        return Time.valueOf("09:00:00");
    }
}
