package com.rrm.learnification;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainActivityViewInitialiserTest {
    private final AndroidLogger dummyLogger = mock(AndroidLogger.class);
    private final LearningItemRepository dummyLearningItemRepository = mock(LearningItemRepository.class);
    private final SettingsRepository dummySettingsRepository = mock(SettingsRepository.class);
    private final PeriodicityPicker dummyPeriodicityPicker = mock(PeriodicityPicker.class);
    private final AppToolbar dummyAppToolbar = mock(AppToolbar.class);
    private final LearningItemListView dummyListView = mock(LearningItemListView.class);
    private final AddLearningItemButton dummyButton = mock(AddLearningItemButton.class);
    private final LearningItemTextInput dummyLearningItemTextInput = mock(LearningItemTextInput.class);

    @Test
    public void itSetsThePeriodicityPickerUsingTheValueStoredInTheSettingsRepositoryDividedBy60() {
        PeriodicityPicker mockPeriodicityPicker = mock(PeriodicityPicker.class);
        SettingsRepository stubSettingsRepository = mock(SettingsRepository.class);
        int periodicityInMinutes = 10;
        int storedPeriodicityInSeconds = periodicityInMinutes * 60;
        when(stubSettingsRepository.readPeriodicitySeconds()).thenReturn(storedPeriodicityInSeconds);
        MainActivityViewInitialiser mainActivityViewInitialiser = new MainActivityViewInitialiser(dummyLogger, dummyLearningItemRepository, stubSettingsRepository, dummyAppToolbar, dummyLearningItemTextInput, dummyButton, mockPeriodicityPicker, dummyListView);

        mainActivityViewInitialiser.initialiseView();

        verify(mockPeriodicityPicker, times(1)).setToValue(periodicityInMinutes);
    }

    @Test
    public void itSetsThePeriodicityPickerTo5MinsIfThereIsNoValueStoredInTheSettingsRepository() {
        PeriodicityPicker mockPeriodicityPicker = mock(PeriodicityPicker.class);
        SettingsRepository stubSettingsRepository = mock(SettingsRepository.class);
        when(stubSettingsRepository.readPeriodicitySeconds()).thenReturn(0);
        MainActivityViewInitialiser mainActivityViewInitialiser = new MainActivityViewInitialiser(dummyLogger, dummyLearningItemRepository, stubSettingsRepository, dummyAppToolbar, dummyLearningItemTextInput, dummyButton, mockPeriodicityPicker, dummyListView);

        mainActivityViewInitialiser.initialiseView();

        verify(mockPeriodicityPicker, times(1)).setToValue(5);
    }

    @Test
    public void initialisesToolbar() {
        AppToolbar mockAppToolbar = mock(AppToolbar.class);
        MainActivityViewInitialiser mainActivityViewInitialiser = new MainActivityViewInitialiser(dummyLogger, dummyLearningItemRepository, dummySettingsRepository, mockAppToolbar, dummyLearningItemTextInput, dummyButton, dummyPeriodicityPicker, dummyListView);

        mainActivityViewInitialiser.initialiseView();

        verify(mockAppToolbar, times(1)).setTitle("Learnification");
    }
}