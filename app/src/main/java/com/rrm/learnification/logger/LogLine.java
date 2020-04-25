package com.rrm.learnification.logger;

class LogLine {
    private final String tag;
    private final String message;
    private String level;

    LogLine(String level, String tag, String message) {
        this.level = level;
        this.tag = tag;
        this.message = message;
    }

    @Override
    public String toString() {
        return "[" + level + "] (" + tag + ") " + message;
    }
}
