package com.rrm.learnification.learnification;

import com.rrm.learnification.common.AndroidLogger;
import com.rrm.learnification.settings.SettingsRepository;

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
        when(mockSettingsRepository.readPeriodicitySeconds()).thenReturn(10);
        ScheduleConfiguration scheduleConfiguration = new ScheduleConfiguration(logger, mockSettingsRepository);

        DelayRange delayRange = scheduleConfiguration.getPeriodicityRange();

        assertThat(delayRange.earliestStartTimeDelayMs, equalTo(10000));
    }

    @Test
    public void usesTheGreatestAcceptableDelayForTheLatestStartTimeDelayMs() {
        when(mockSettingsRepository.readPeriodicitySeconds()).thenReturn(10);
        ScheduleConfiguration scheduleConfiguration = new ScheduleConfiguration(logger, mockSettingsRepository);

        DelayRange delayRange = scheduleConfiguration.getPeriodicityRange();

        assertThat(delayRange.latestStartTimeDelayMs, equalTo(10000 + (1000 * ScheduleConfiguration.MAXIMUM_ACCEPTABLE_DELAY_SECONDS)));
    }
}