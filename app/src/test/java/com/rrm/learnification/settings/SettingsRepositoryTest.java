package com.rrm.learnification.settings;

import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.settings.learnificationpromptstrategy.LearnificationPromptStrategy;
import com.rrm.learnification.storage.FileStorageAdaptor;

import org.junit.Test;

import java.io.IOException;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SettingsRepositoryTest {
    private final AndroidLogger logger = mock(AndroidLogger.class);
    private final FileStorageAdaptor mockFileStorageAdaptor = mock(FileStorageAdaptor.class);

    @Test
    public void canReadPeriodicityFile() throws IOException {
        when(mockFileStorageAdaptor.readLines(anyString())).thenReturn(Collections.singletonList("learnificationDelayInSeconds=30"));
        SettingsRepository settingsRepository = new SettingsRepository(logger, mockFileStorageAdaptor);

        int periodicity = settingsRepository.readDelaySeconds();

        assertThat(periodicity, equalTo(30));
    }

    @Test
    public void ifLineIsEmptyItReturnsTeDefault() throws IOException {
        when(mockFileStorageAdaptor.readLines(anyString())).thenReturn(Collections.singletonList(""));
        SettingsRepository settingsRepository = new SettingsRepository(logger, mockFileStorageAdaptor);

        int periodicity = settingsRepository.readDelaySeconds();

        assertThat(periodicity, equalTo(SettingsRepository.DEFAULT_LEARNIFICATION_DELAY_SECONDS));
    }

    @Test
    public void delayIsSecondsDividedBy60() throws IOException {
        int delayInMinutes = 10;
        int storedPeriodicityInSeconds = delayInMinutes * 60;
        when(mockFileStorageAdaptor.readLines(anyString())).thenReturn(Collections.singletonList("learnificationDelayInSeconds=" + storedPeriodicityInSeconds));
        SettingsRepository settingsRepository = new SettingsRepository(logger, mockFileStorageAdaptor);

        int delayMinutes = settingsRepository.readDelayMinutes();

        assertThat(delayMinutes, equalTo(delayInMinutes));
    }

    @Test
    public void delayInMinutesIs0IfDelayFileNotFound() throws IOException {
        when(mockFileStorageAdaptor.readLines(anyString())).thenThrow(new IOException("Can't get the delay from file!"));
        SettingsRepository settingsRepository = new SettingsRepository(logger, mockFileStorageAdaptor);

        int delayMinutes = settingsRepository.readDelayMinutes();

        assertThat(delayMinutes, equalTo(0));
    }

    @Test
    public void ifPromptStrategyFileIsEmptyItReturnsTeDefault() throws IOException {
        when(mockFileStorageAdaptor.readLines(anyString())).thenReturn(Collections.singletonList(""));
        SettingsRepository settingsRepository = new SettingsRepository(logger, mockFileStorageAdaptor);

        LearnificationPromptStrategy learnificationPromptStrategy = settingsRepository.readLearnificationPromptStrategy();

        assertThat(learnificationPromptStrategy, equalTo(SettingsRepository.DEFAULT_LEARNIFICATION_PROMPT_STRATEGY));
    }
}