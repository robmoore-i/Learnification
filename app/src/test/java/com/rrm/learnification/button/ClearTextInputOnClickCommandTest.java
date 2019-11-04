package com.rrm.learnification.button;

import com.rrm.learnification.textinput.TextInput;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ClearTextInputOnClickCommandTest {
    @Test
    public void itClearsTheTextInput() {
        TextInput mockTextInput = mock(TextInput.class);
        ClearTextInputOnClickCommand clearTextInputOnClickCommand = new ClearTextInputOnClickCommand(mockTextInput);

        clearTextInputOnClickCommand.onClick();

        verify(mockTextInput, times(1)).clear();
    }
}