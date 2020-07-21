package com.rrm.learnification.learnification.publication;

import com.rrm.learnification.jobs.DelayCalculator;

import org.junit.Test;

import java.sql.Time;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("FieldCanBeLocal")
public class DelayCalculatorTest {
    private final DelayCalculator delayCalculator = new DelayCalculator();

    @Test
    public void timeBetween1800AndMidnightIs6Hours() {
        int millisBetween = delayCalculator.millisBetween(LocalDateTime.of(2019, 8, 18, 18, 0), Time.valueOf("00:00:00"));

        assertEquals(hours(6), millisBetween);
    }

    @Test
    public void timeBetween1830AndMidnightIs5AndAHalfHours() {
        int millisBetween = delayCalculator.millisBetween(LocalDateTime.of(2019, 8, 18, 18, 30), Time.valueOf("00:00:00"));

        assertEquals(hours(5) + mins(30), millisBetween);
    }

    @Test
    public void timeBetween1815AndMidnightIs5And3QuarterHours() {
        int millisBetween = delayCalculator.millisBetween(LocalDateTime.of(2019, 8, 18, 18, 15), Time.valueOf("00:00:00"));

        assertEquals(hours(5) + mins(45), millisBetween);
    }

    @Test
    public void timeBetween1845AndMidnightIs5AndAQuarterHours() {
        int millisBetween = delayCalculator.millisBetween(LocalDateTime.of(2019, 8, 18, 18, 45), Time.valueOf("00:00:00"));

        assertEquals(hours(5) + mins(15), millisBetween);
    }

    @Test
    public void timeBetween2300And9amNextDayIs10Hours() {
        int millisBetween = delayCalculator.millisBetween(LocalDateTime.of(2019, 8, 18, 23, 0), Time.valueOf("09:00:00"));

        assertEquals(hours(10), millisBetween);
    }

    @Test
    public void timeBetween2215And9amNextDayIs10Hours45Minutes() {
        int millisBetween = delayCalculator.millisBetween(LocalDateTime.of(2019, 8, 18, 22, 15), Time.valueOf("09:00:00"));

        assertEquals(hours(10) + mins(45), millisBetween);
    }

    @Test
    public void timeBetween8amAnd9pmSameDayIsThirteenHours() {
        int millisBetween = delayCalculator.millisBetween(LocalDateTime.of(2020, 7, 21, 8, 0), Time.valueOf("21:00:00"));

        assertEquals(hours(13), millisBetween);
    }

    @Test
    public void timeBetween0815amAnd9pmSameDayIs12Hours45Minutes() {
        int millisBetween = delayCalculator.millisBetween(LocalDateTime.of(2020, 7, 21, 8, 15), Time.valueOf("21:00:00"));

        assertEquals(hours(12) + mins(45), millisBetween);
    }

    private int hours(int n) {
        return n * 60 * 60 * 1000;
    }

    private int mins(int n) {
        return n * 60 * 1000;
    }
}