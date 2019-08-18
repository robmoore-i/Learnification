package com.rrm.learnification;

import org.junit.Test;

import java.sql.Time;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class DelayCalculatorTest {
    private final DelayCalculator delayCalculator = new DelayCalculator();

    @Test
    public void timeBetween1800AndMidnightIs6Hours() {
        int millisBetween = delayCalculator.millisBetween(LocalDateTime.of(2019, 8, 18, 18, 0), Time.valueOf("00:00:00"));

        assertThat(millisBetween, equalTo(6 * 60 * 60 * 1000));
    }

    @Test
    public void timeBetween1830AndMidnightIs5AndAHalfHours() {
        int millisBetween = delayCalculator.millisBetween(LocalDateTime.of(2019, 8, 18, 18, 30), Time.valueOf("00:00:00"));

        assertThat(millisBetween, equalTo((6 * 60 * 60 * 1000) + (30 * 60 * 1000)));
    }

    @Test
    public void timeBetween1815AndMidnightIs5And3QuarterHours() {
        int millisBetween = delayCalculator.millisBetween(LocalDateTime.of(2019, 8, 18, 18, 15), Time.valueOf("00:00:00"));

        assertThat(millisBetween, equalTo((6 * 60 * 60 * 1000) + (45 * 60 * 1000)));
    }

    @Test
    public void timeBetween2300And9amNextDayIs10Hours() {
        int millisBetween = delayCalculator.millisBetween(LocalDateTime.of(2019, 8, 18, 23, 0), Time.valueOf("09:00:00"));

        assertThat(millisBetween, equalTo((10 * 60 * 60 * 1000)));
    }
}