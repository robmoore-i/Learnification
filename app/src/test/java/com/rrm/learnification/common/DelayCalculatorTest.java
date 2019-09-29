package com.rrm.learnification.common;

import org.junit.Test;

import java.sql.Time;
import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@SuppressWarnings("FieldCanBeLocal")
public class DelayCalculatorTest {
    private final int sixHours = 6 * 60 * 60 * 1000;
    private final int fiveHours = 5 * 60 * 60 * 1000;
    private final int tenHours = 10 * 60 * 60 * 1000;
    private final int thirtyMins = 30 * 60 * 1000;
    private final int fortyFiveMins = 45 * 60 * 1000;
    private final int fifteenMins = 15 * 60 * 1000;

    private final DelayCalculator delayCalculator = new DelayCalculator();

    @Test
    public void timeBetween1800AndMidnightIs6Hours() {
        int millisBetween = delayCalculator.millisBetween(LocalDateTime.of(2019, 8, 18, 18, 0), Time.valueOf("00:00:00"));

        assertThat(millisBetween, equalTo(sixHours));
    }

    @Test
    public void timeBetween1830AndMidnightIs5AndAHalfHours() {
        int millisBetween = delayCalculator.millisBetween(LocalDateTime.of(2019, 8, 18, 18, 30), Time.valueOf("00:00:00"));

        assertThat(millisBetween, equalTo(fiveHours + thirtyMins));
    }

    @Test
    public void timeBetween1815AndMidnightIs5And3QuarterHours() {
        int millisBetween = delayCalculator.millisBetween(LocalDateTime.of(2019, 8, 18, 18, 15), Time.valueOf("00:00:00"));

        assertThat(millisBetween, equalTo(fiveHours + fortyFiveMins));
    }

    @Test
    public void timeBetween1845AndMidnightIs5AndAQuarterHours() {
        int millisBetween = delayCalculator.millisBetween(LocalDateTime.of(2019, 8, 18, 18, 45), Time.valueOf("00:00:00"));

        assertThat(millisBetween, equalTo(fiveHours + fifteenMins));
    }

    @Test
    public void timeBetween2300And9amNextDayIs10Hours() {
        int millisBetween = delayCalculator.millisBetween(LocalDateTime.of(2019, 8, 18, 23, 0), Time.valueOf("09:00:00"));

        assertThat(millisBetween, equalTo(tenHours));
    }

    @Test
    public void timeBetween2215And9amNextDayIs10Hours45Minutes() {
        int millisBetween = delayCalculator.millisBetween(LocalDateTime.of(2019, 8, 18, 22, 15), Time.valueOf("09:00:00"));

        assertThat(millisBetween, equalTo(tenHours + fortyFiveMins));
    }
}