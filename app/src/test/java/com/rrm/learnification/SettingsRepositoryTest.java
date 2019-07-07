package com.rrm.learnification;

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
        when(mockFileStorageAdaptor.readLines(anyString())).thenReturn(Collections.singletonList("periodicityInSeconds=30"));
        SettingsRepository settingsRepository = new SettingsRepository(logger, mockFileStorageAdaptor);

        int periodicity = settingsRepository.readPeriodicitySeconds();

        assertThat(periodicity, equalTo(30));
    }
}