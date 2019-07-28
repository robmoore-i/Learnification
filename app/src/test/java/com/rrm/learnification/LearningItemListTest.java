package com.rrm.learnification;

import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LearningItemListTest {
    private AndroidLogger logger = mock(AndroidLogger.class);

    @Test
    public void swipingOnAnItemCallsTheOnSwipeCommand() {
        LearningItemListView stubLearningItemListView = mock(LearningItemListView.class);
        LearningItemList learningItemList = new LearningItemList(logger, stubLearningItemListView);

        OnSwipeCommand mockOnSwipeCommand = mock(OnSwipeCommand.class);
        learningItemList.setOnSwipeCommand(mockOnSwipeCommand);
        int index = 0;
        learningItemList.swipeOnItem(index);

        verify(mockOnSwipeCommand, times(1)).onSwipe(any(), eq(index));
    }
}