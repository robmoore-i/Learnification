package com.rrm.learnification;

import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LearnificationPublisherTest {
    @Test
    public void ifTheTextGeneratorThrowsACantGenerateNotificationTextExceptionThenItDoesntPublishANotification() throws CantGenerateNotificationTextException {
        AndroidLogger dummyLogger = mock(AndroidLogger.class);

        LearnificationTextGenerator stubLearnificationTextGenerator = mock(LearnificationTextGenerator.class);
        when(stubLearnificationTextGenerator.notificationText()).thenThrow(CantGenerateNotificationTextException.class);

        AndroidNotificationFacade mockAndroidNotificationFacade = mock(AndroidNotificationFacade.class);

        LearnificationPublisher learnificationPublisher = new LearnificationPublisher(
                dummyLogger,
                stubLearnificationTextGenerator,
                mockAndroidNotificationFacade
        );

        learnificationPublisher.publishLearnification();

        verify(mockAndroidNotificationFacade, never()).publish(any());
    }

    @Test
    public void itLogsAnyThrownException() throws CantGenerateNotificationTextException {
        AndroidLogger mockLogger = mock(AndroidLogger.class);

        AndroidNotificationFacade mockAndroidNotificationFacade = mock(AndroidNotificationFacade.class);

        LearnificationTextGenerator stubLearnificationTextGenerator = mock(LearnificationTextGenerator.class);
        when(stubLearnificationTextGenerator.notificationText()).thenThrow(CantGenerateNotificationTextException.class);

        LearnificationPublisher learnificationPublisher = new LearnificationPublisher(
                mockLogger,
                stubLearnificationTextGenerator,
                mockAndroidNotificationFacade
        );

        learnificationPublisher.publishLearnification();

        verify(mockLogger, times(1)).e(eq("LearnificationPublisher"), any(CantGenerateNotificationTextException.class));
    }
}