package com.rrm.learnification;

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

        PeriodicityRange periodicityRange = scheduleConfiguration.getPeriodicityRange();

        assertThat(periodicityRange.earliestStartTimeDelayMs, equalTo(10000));
    }

    @Test
    public void usesTheGreatestAcceptableDelayForTheLatestStartTimeDelayMs() {
        when(mockSettingsRepository.readPeriodicitySeconds()).thenReturn(10);
        ScheduleConfiguration scheduleConfiguration = new ScheduleConfiguration(logger, mockSettingsRepository);

        PeriodicityRange periodicityRange = scheduleConfiguration.getPeriodicityRange();

        assertThat(periodicityRange.latestStartTimeDelayMs, equalTo(10000 + (1000 * ScheduleConfiguration.MAXIMUM_ACCEPTABLE_DELAY_SECONDS)));
    }
}