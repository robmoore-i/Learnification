package com.rrm.learnification.logger;

class LogLine {
    final String date;
    final String time;
    final String level;
    final String tag;
    final String creator;
    final String message;

    LogLine(String date, String time, String level, String tag, String creator, String message) {
        this.date = date;
        this.time = time;
        this.level = level;
        this.tag = tag;
        this.creator = creator;
        this.message = message;
    }

    static LogLine empty() {
        return new LogLine("", "", "", "", "", "") {
            @Override
            boolean notEmpty() {
                return false;
            }
        };
    }

    @Override
    public String toString() {
        return String.join(" ", date, time, level, creator, message);
    }

    boolean notEmpty() {
        return true;
    }
}
