package com.rrm.learnification;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainActivityViewInitialiserTest {
    private AndroidLogger dummyLogger = mock(AndroidLogger.class);
    private LearnificationRepository dummyLearnificationRepository = mock(LearnificationRepository.class);
    private SettingsRepository dummySettingsRepository = mock(SettingsRepository.class);

    @Test
    public void itSetsThePeriodicityPickerUsingTheValueStoredInTheSettingsRepositoryDividedBy60() {
        MainActivityView mockMainActivityView = mock(MainActivityView.class);
        SettingsRepository stubSettingsRepository = mock(SettingsRepository.class);
        int periodicityInMinutes = 10;
        int storedPeriodicityInSeconds = periodicityInMinutes * 60;
        when(stubSettingsRepository.readPeriodicitySeconds()).thenReturn(storedPeriodicityInSeconds);
        MainActivityViewInitialiser mainActivityViewInitialiser = new MainActivityViewInitialiser(dummyLogger, mockMainActivityView, dummyLearnificationRepository, stubSettingsRepository);

        mainActivityViewInitialiser.initialiseView();

        verify(mockMainActivityView, times(1)).setPeriodicityPickerToValue(periodicityInMinutes);
    }

    @Test
    public void itSetsThePeriodicityPickerTo5MinsIfThereIsNoValueStoredInTheSettingsRepository() {
        MainActivityView mockMainActivityView = mock(MainActivityView.class);
        SettingsRepository stubSettingsRepository = mock(SettingsRepository.class);
        when(stubSettingsRepository.readPeriodicitySeconds()).thenReturn(0);
        MainActivityViewInitialiser mainActivityViewInitialiser = new MainActivityViewInitialiser(dummyLogger, mockMainActivityView, dummyLearnificationRepository, stubSettingsRepository);

        mainActivityViewInitialiser.initialiseView();

        verify(mockMainActivityView, times(1)).setPeriodicityPickerToValue(5);
    }

    @Test
    public void initialisesToolbar() {
        MainActivityView mockMainActivityView = mock(MainActivityView.class);
        MainActivityViewInitialiser mainActivityViewInitialiser = new MainActivityViewInitialiser(dummyLogger, mockMainActivityView, dummyLearnificationRepository, dummySettingsRepository);

        mainActivityViewInitialiser.initialiseView();

        verify(mockMainActivityView, times(1)).initialiseToolbar("Learnification");
    }
}