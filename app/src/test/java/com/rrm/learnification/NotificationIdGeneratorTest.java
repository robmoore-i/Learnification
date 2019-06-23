package com.rrm.learnification;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class NotificationIdGeneratorTest {
    @Test
    public void firstIdIs0() {
        assertThat(new NotificationIdGenerator().getNotificationId(), equalTo(0));
    }

    @Test
    public void secondIdIs1() {
        NotificationIdGenerator notificationIdGenerator = new NotificationIdGenerator();
        notificationIdGenerator.getNotificationId();
        assertThat(notificationIdGenerator.getNotificationId(), equalTo(1));
    }
}