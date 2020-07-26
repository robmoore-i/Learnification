package com.rrm.learnification.jobs;

import com.rrm.learnification.time.AndroidClock;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ScheduledTimeFormatterTest {

    private final AndroidClock stubClock = mock(AndroidClock.class);
    private final ScheduledTimeFormatter formatter = new ScheduledTimeFormatter(stubClock);

    @Before
    public void setUp() {
        when(stubClock.now()).thenReturn(LocalDateTime.of(2020, 7, 26, 8, 0));
    }

    @Test
    public void formats9amToday() {
        assertEquals("9am today", formatter.format(LocalDateTime.of(2020, 7, 26, 9, 0)));
    }

    @Test
    public void formats9pmToday() {
        assertEquals("9pm today", formatter.format(LocalDateTime.of(2020, 7, 26, 21, 0)));
    }

    @Test
    public void formats930pmToday() {
        assertEquals("9:30pm today", formatter.format(LocalDateTime.of(2020, 7, 26, 21, 30)));
    }

    @Test
    public void formatsNoonToday() {
        assertEquals("noon today", formatter.format(LocalDateTime.of(2020, 7, 26, 12, 0)));
    }

    @Test
    public void formatsMidnight() {
        assertEquals("midnight today", formatter.format(LocalDateTime.of(2020, 7, 27, 0, 0)));
    }

    @Test
    public void formats10pastMidnightTomorrow() {
        assertEquals("00:30am tomorrow", formatter.format(LocalDateTime.of(2020, 7, 27, 0, 30)));
    }

    @Test
    public void formats9amTomorrow() {
        assertEquals("9am tomorrow", formatter.format(LocalDateTime.of(2020, 7, 27, 9, 0)));
    }

    @Test
    public void formats9amInTwoDays() {
        assertEquals("9am Tue", formatter.format(LocalDateTime.of(2020, 7, 28, 9, 0)));
    }
}