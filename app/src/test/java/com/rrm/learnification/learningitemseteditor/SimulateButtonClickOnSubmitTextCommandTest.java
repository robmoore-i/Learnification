package com.rrm.learnification.learningitemseteditor;

import com.rrm.learnification.button.ConfigurableButton;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SimulateButtonClickOnSubmitTextCommandTest {
    @Test
    public void itClicksTheAddLearningItemButtonOnSubmit() {
        ConfigurableButton mockButton = mock(ConfigurableButton.class);
        SimulateButtonClickOnSubmitTextCommand simulateButtonClickOnSubmitTextCommand = new SimulateButtonClickOnSubmitTextCommand(mockButton);

        simulateButtonClickOnSubmitTextCommand.onSubmit();

        verify(mockButton, times(1)).click();
    }
}