package com.rrm.learnification;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LearnificationButtonTest {
    @Test
    public void settingOnClickHandlerAndClickingButtonInvokesTheOnClickComand() {
        MainActivityView mockMainActivityView = mock(MainActivityView.class);
        LearnificationButton learnificationButton = new LearnificationButton(new AndroidLogger(), mockMainActivityView);
        OnClickCommand mockOnClickCommand = mock(OnClickCommand.class);

        learnificationButton.setOnClickHandler(mockOnClickCommand);
        learnificationButton.click();

        verify(mockOnClickCommand, times(1)).onClick();
    }
}