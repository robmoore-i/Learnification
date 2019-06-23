package com.rrm.learnification;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class NotificationIdGeneratorTest {
    @Before
    public void setUp() {
        NotificationIdGenerator.getInstance().reset();
    }

    @Test
    public void firstIdIs0() {
        assertThat(NotificationIdGenerator.getInstance().nextNotificationId(), equalTo(0));
    }

    @Test
    public void secondIdIs1() {
        NotificationIdGenerator notificationIdGenerator = NotificationIdGenerator.getInstance();
        notificationIdGenerator.nextNotificationId();
        assertThat(notificationIdGenerator.nextNotificationId(), equalTo(1));
    }

    @Test
    public void afterReturningAnIdOnceTheLastIdIs0() {
        NotificationIdGenerator.getInstance().nextNotificationId();
        assertThat(NotificationIdGenerator.getInstance().lastNotificationId(), equalTo(0));
    }

    @Test
    public void afterReturningAnIdTwiceTheLastIdIs1() {
        NotificationIdGenerator.getInstance().nextNotificationId();
        NotificationIdGenerator.getInstance().nextNotificationId();
        assertThat(NotificationIdGenerator.getInstance().lastNotificationId(), equalTo(1));
    }

    @Test
    public void afterReturningAnIdTwiceThenResettingTheLastIdIsStill1() {
        NotificationIdGenerator.getInstance().nextNotificationId();
        NotificationIdGenerator.getInstance().nextNotificationId();
        NotificationIdGenerator.getInstance().reset();
        assertThat(NotificationIdGenerator.getInstance().lastNotificationId(), equalTo(1));
    }
}