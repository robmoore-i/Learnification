package com.rrm.learnification;

import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LearnificationListViewTest {
    private AndroidLogger logger = mock(AndroidLogger.class);

    @Test
    public void swipingOnAnItemCallsTheOnSwipeCommand() {
        MainActivityView stubMainActivityView = mock(MainActivityView.class);
        LearnificationListView learnificationListView = new LearnificationListView(logger, stubMainActivityView);

        OnSwipeCommand mockOnSwipeCommand = mock(OnSwipeCommand.class);
        learnificationListView.setOnSwipeCommand(mockOnSwipeCommand);
        int index = 0;
        learnificationListView.swipeOnItem(index);

        verify(mockOnSwipeCommand, times(1)).onSwipe(any(), eq(index));
    }
}