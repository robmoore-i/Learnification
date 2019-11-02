package com.rrm.learnification.response;

import com.rrm.learnification.common.AndroidLogger;
import com.rrm.learnification.notification.NotificationManager;
import com.rrm.learnification.publication.LearnificationPublishingService;
import com.rrm.learnification.publication.LearnificationScheduler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LearnificationResponseServiceEntryPointTest {
    private final String userExpectedResponse = "expected";
    private final String userActualResponse = "actual";
    private NotificationTextContent content = new NotificationTextContent("title", "text");

    private AndroidLogger mockLogger = mock(AndroidLogger.class);
    private NotificationManager mockNotificationManager = mock(NotificationManager.class);
    private LearnificationScheduler mockLearnificationScheduler = mock(LearnificationScheduler.class);
    private LearnificationResponseContentGenerator mockContentGenerator = mock(LearnificationResponseContentGenerator.class);

    private LearnificationResponseServiceEntryPoint learnificationResponseServiceEntryPoint;

    private LearnificationResponseIntent stubIntent = mock(LearnificationResponseIntent.class);

    @Before
    public void beforeEach() {
        Mockito.reset(mockLogger, mockNotificationManager, mockLearnificationScheduler, mockContentGenerator, stubIntent);

        when(stubIntent.expectedUserResponse()).thenReturn(userExpectedResponse);
        when(stubIntent.actualUserResponse()).thenReturn(userActualResponse);
        when(mockContentGenerator.getResponseNotificationTextContent(anyString(), anyString())).thenReturn(content);

        learnificationResponseServiceEntryPoint = new LearnificationResponseServiceEntryPoint(
                mockLogger,
                mockNotificationManager,
                mockLearnificationScheduler,
                mockContentGenerator
        );
    }

    @Test
    public void ifNotSkippedAndHasRemoteInputItCreatesResponseContentUsingGeneratorFromLearningItem() {
        when(stubIntent.isShowMeResponse()).thenReturn(false);
        when(stubIntent.hasRemoteInput()).thenReturn(true);

        learnificationResponseServiceEntryPoint.onHandleIntent(stubIntent);

        verify(mockContentGenerator, times(1)).getResponseNotificationTextContent(userExpectedResponse, userActualResponse);
    }

    @Test
    public void ifNotSkippedAndHasRemoteInputItUsesResponseContentToUpdateLatestNotification() {
        when(stubIntent.isShowMeResponse()).thenReturn(false);
        when(stubIntent.hasRemoteInput()).thenReturn(true);

        learnificationResponseServiceEntryPoint.onHandleIntent(stubIntent);

        verify(mockNotificationManager, times(1)).updateLatestWithReply(content);
    }

    @Test
    public void itSchedulesALearnification() {
        learnificationResponseServiceEntryPoint.onHandleIntent(stubIntent);

        verify(mockLearnificationScheduler, times(1)).scheduleJob(LearnificationPublishingService.class);
    }
}