package com.rrm.learnification.main;

import com.rrm.learnification.button.ConfigurableButton;
import com.rrm.learnification.textinput.SimulateButtonClickOnSubmitTextCommand;

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