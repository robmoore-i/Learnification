package com.rrm.learnification;

import java.sql.Time;
import java.time.LocalDateTime;

class DelayCalculator {
    int millisBetween(LocalDateTime now, Time time) {
        int hour = now.getHour();
        int hoursLeftToday = 24 - hour;

        int minute = now.getMinute();
        int minutesLeftInHour = 60 - minute;

        int millisUntilMidnight = ((hoursLeftToday - 1) * 60 * 60 * 1000) + (minutesLeftInHour * 60 * 1000);

        int hourOfNextDay = Integer.parseInt(time.toString().split(":")[0]);
        int millisFromMidnightUntilTime = hourOfNextDay * 60 * 60 * 1000;

        return millisUntilMidnight + millisFromMidnightUntilTime;
    }
}
