package com.rrm.learnification.jobs;

import java.sql.Time;
import java.time.LocalDateTime;

public class DelayCalculator {
    public int millisBetween(LocalDateTime now, Time then) {
        int nowHour = now.getHour();
        int nowMinute = now.getMinute();

        int thenHour = Integer.parseInt(then.toString().split(":")[0]);
        int thenMinute = Integer.parseInt(then.toString().split(":")[1]);

        boolean nowIsEarlierInTheDayThanThen = thenHour > nowHour || (thenHour == nowHour && thenMinute > nowMinute);
        if (nowIsEarlierInTheDayThanThen) {
            int hourDifference = thenHour - nowHour;
            int minuteDifference = thenMinute - nowMinute;
            return (hourDifference * 60 * 60 * 1000) + (minuteDifference * 60 * 1000);
        } else {
            int millisFromNowUntilMidnight = ((24 - nowHour) * 60 * 60 * 1000) - (nowMinute * 60 * 1000);
            int millisFromMidnightUntilThen = thenHour * 60 * 60 * 1000;
            return millisFromNowUntilMidnight + millisFromMidnightUntilThen;
        }
    }
}
