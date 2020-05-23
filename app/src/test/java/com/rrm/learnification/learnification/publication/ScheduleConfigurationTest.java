package com.rrm.learnification.learnification.publication;

import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.settings.SettingsRepository;
import com.rrm.learnification.settings.learnificationdelay.DelayRange;
import com.rrm.learnification.settings.learnificationdelay.ScheduleConfiguration;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScheduleConfigurationTest {
    private final AndroidLogger logger = mock(AndroidLogger.class);
    private final SettingsRepository mockSettingsRepository = mock(SettingsRepository.class);

    @Test
    public void readsPeriodicityFromStorage() {
        when(mockSettingsRepository.readDelaySeconds()).thenReturn(10);
        ScheduleConfiguration scheduleConfiguration = new ScheduleConfiguration(logger, mockSettingsRepository);

        DelayRange delayRange = scheduleConfiguration.getConfiguredDelayRange();

        assertThat(delayRange.earliestStartTimeDelayMs, equalTo(10000));
    }

    @Test
    public void usesTheGreatestAcceptableDelayForTheLatestStartTimeDelayMs() {
        when(mockSettingsRepository.readDelaySeconds()).thenReturn(10);
        ScheduleConfiguration scheduleConfiguration = new ScheduleConfiguration(logger, mockSettingsRepository);

        DelayRange delayRange = scheduleConfiguration.getConfiguredDelayRange();

        assertThat(delayRange.latestStartTimeDelayMs, equalTo(10000 + (1000 * ScheduleConfiguration.MAXIMUM_ACCEPTABLE_DELAY_SECONDS)));
    }
}