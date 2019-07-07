package com.rrm.learnification;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LearnificationButtonTest {
    private final AndroidLogger logger = mock(AndroidLogger.class);

    @Test
    public void settingOnClickHandlerAndClickingButtonInvokesTheOnClickComand() {
        MainActivityView mockMainActivityView = mock(MainActivityView.class);
        LearnificationButton learnificationButton = new LearnificationButton(logger, mockMainActivityView);
        OnClickCommand mockOnClickCommand = mock(OnClickCommand.class);

        learnificationButton.setOnClickHandler(mockOnClickCommand);
        learnificationButton.click();

        verify(mockOnClickCommand, times(1)).onClick();
    }
}