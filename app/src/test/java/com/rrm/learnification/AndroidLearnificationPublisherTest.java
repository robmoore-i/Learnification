package com.rrm.learnification;

import org.junit.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AndroidLearnificationPublisherTest {
    @Test
    public void ifTheTextGeneratorThrowsACantGenerateNotificationTextExceptionThenItDoesntPublishANotification() throws CantGenerateNotificationTextException {
        AndroidLogger dummyLogger = mock(AndroidLogger.class);
        AndroidLearnificationFactory dummyAndroidLearnificationFactory = mock(AndroidLearnificationFactory.class);
        NotificationIdGenerator dummyNotificationIdGenerator = mock(NotificationIdGenerator.class);

        AndroidNotificationManager mockNotificationManager = mock(AndroidNotificationManager.class);

        LearnificationTextGenerator stubLearnificationTextGenerator = mock(LearnificationTextGenerator.class);
        when(stubLearnificationTextGenerator.notificationText()).thenThrow(CantGenerateNotificationTextException.class);

        AndroidLearnificationPublisher androidLearnificationPublisher = new AndroidLearnificationPublisher(
                dummyLogger,
                dummyAndroidLearnificationFactory,
                dummyNotificationIdGenerator,
                stubLearnificationTextGenerator,
                mockNotificationManager
        );

        androidLearnificationPublisher.createLearnification();

        verify(mockNotificationManager, never()).notify(anyInt(), any());
    }

    @Test
    public void itLogsAnyThrownException() throws CantGenerateNotificationTextException {
        AndroidLogger mockLogger = mock(AndroidLogger.class);
        AndroidLearnificationFactory stubAndroidLearnificationFactory = mock(AndroidLearnificationFactory.class);
        NotificationIdGenerator stubNotificationIdGenerator = mock(NotificationIdGenerator.class);

        AndroidNotificationManager mockNotificationManager = mock(AndroidNotificationManager.class);
        LearnificationTextGenerator stubLearnificationTextGenerator = mock(LearnificationTextGenerator.class);
        when(stubLearnificationTextGenerator.notificationText()).thenThrow(CantGenerateNotificationTextException.class);

        AndroidLearnificationPublisher androidLearnificationPublisher = new AndroidLearnificationPublisher(
                mockLogger,
                stubAndroidLearnificationFactory,
                stubNotificationIdGenerator,
                stubLearnificationTextGenerator,
                mockNotificationManager
        );

        androidLearnificationPublisher.createLearnification();

        verify(mockLogger, times(1)).e(eq("AndroidLearnificationPublisher"), any(CantGenerateNotificationTextException.class));
    }
}