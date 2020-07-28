package com.rrm.learnification.jobs;

import com.rrm.learnification.time.AndroidClock;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/*
Provides the formatting I want for the times of scheduled jobs. See the tests for details of what this is,
because really trying to decipher this code is just not worth the effort.
*/
public class ScheduledTimeFormatter {
    private final AndroidClock clock;

    public ScheduledTimeFormatter(AndroidClock clock) {
        this.clock = clock;
    }

    public String format(LocalDateTime localDateTime) {
        String timeMarker = timeMarker(localDateTime);
        String dayMarker = dayMarker(timeMarker, localDateTime);
        return timeMarker + " " + dayMarker;
    }

    private String dayMarker(String timeMarker, LocalDateTime localDateTime) {
        String dayMarker;
        LocalDate nowDate = clock.now().toLocalDate();
        LocalDate otherDate = localDateTime.toLocalDate();
        if (nowDate.equals(otherDate)) {
            dayMarker = "today";
        } else if (nowDate.plusDays(1).equals(otherDate)) {
            dayMarker = "tomorrow";
        } else {
            dayMarker = localDateTime.format(DateTimeFormatter.ofPattern("E"));
        }
        if (timeMarker.equals("midnight") && dayMarker.equals("tomorrow")) {
            return "today";
        }
        return dayMarker;
    }

    private String timeMarker(LocalDateTime localDateTime) {
        String amPm = localDateTime.format(DateTimeFormatter.ofPattern("a")).toLowerCase();
        String hour = localDateTime.format(DateTimeFormatter.ofPattern("hh")) + amPm;
        if (hour.startsWith("0")) {
            hour = hour.substring(1);
        }
        String minute = localDateTime.format(DateTimeFormatter.ofPattern("mm"));
        if (hour.equals("12am") && minute.equals("00")) {
            return "midnight";
        } else if (hour.equals("12pm") && minute.equals("00")) {
            return "noon";
        } else {
            if (minute.equals("00")) {
                minute = "";
            } else {
                minute = ":" + minute;
            }
            if (hour.equals("12am")) {
                hour = "00am";
            }
            hour = hour.substring(0, hour.length() - 2);
            return hour + minute + amPm;
        }
    }
}
