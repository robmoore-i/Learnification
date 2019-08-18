package com.rrm.learnification;

import java.sql.Time;
import java.time.LocalDateTime;

class DelayCalculator {
    int millisBetween(LocalDateTime now, Time time) {
        int hour = now.getHour();
        int hoursLeftToday = 24 - hour;

        int minute = now.getMinute();
        int minutesLeftInHour = (minute - 60) % 60;

        int timeUntilMidnight = (hoursLeftToday * 60 * 60 * 1000) - (minutesLeftInHour * 60 * 1000);

        return timeUntilMidnight;
    }
}
