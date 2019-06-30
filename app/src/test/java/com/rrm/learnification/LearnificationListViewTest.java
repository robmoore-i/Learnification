package com.rrm.learnification;

import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LearnificationListViewTest {
    private AndroidLogger logger = mock(AndroidLogger.class);

    @Test
    public void swipingLeftOnAnItemRemovesItFromTheLearnificationRepository() {
        MainActivityView stubMainActivityView = mock(MainActivityView.class);
        LearnificationListViewAdaptor stubLearnificationListViewAdaptor = mock(LearnificationListViewAdaptor.class);
        when(stubMainActivityView.getLearnificationList(any(), any())).thenReturn(stubLearnificationListViewAdaptor);

        LearnificationListView learnificationListView = new LearnificationListView(logger, stubMainActivityView);
        LearnificationRepository mockLearnificationRepository = mock(LearnificationRepository.class);
        learnificationListView.populate(mockLearnificationRepository);

        learnificationListView.swipeOnItem(0);

        verify(mockLearnificationRepository, times(1)).removeAt(0);
    }
}