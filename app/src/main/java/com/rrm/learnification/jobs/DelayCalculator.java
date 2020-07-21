package com.rrm.learnification.jobs;

import java.sql.Time;
import java.time.LocalDateTime;

public class DelayCalculator {
    public int millisBetween(LocalDateTime now, Time time) {
        int hour = now.getHour();
        int minute = now.getMinute();
        int minutesLeftInHour = 60 - minute;

        int hourOfDay = Integer.parseInt(time.toString().split(":")[0]);
        int millisFromMidnightUntilTime = hourOfDay * 60 * 60 * 1000;

        if (hourOfDay < hour) {
            int hoursLeftToday = 24 - hour;
            int millisUntilMidnight = ((hoursLeftToday - 1) * 60 * 60 * 1000) + (minutesLeftInHour * 60 * 1000);
            return millisUntilMidnight + millisFromMidnightUntilTime;
        } else {
            int millisFromPastMidnightUntilNow = (hour * 60 * 60 * 1000) + (minute * 60 * 1000);
            return millisFromMidnightUntilTime - millisFromPastMidnightUntilNow;
        }
    }
}
