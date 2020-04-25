package com.rrm.learnification.logger;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class LogStore {
    private final List<LogLine> logs = new ArrayList<>();

    List<String> dump() {
        return logs.stream().map(LogLine::toString).collect(Collectors.toList());
    }

    void i(String tag, String message) {
        store("INFO", tag, message);
    }

    void v(String tag, String message) {
        store("VERBOSE", tag, message);
    }

    void u(String tag, String message) {
        store("USER", tag, message);
    }

    void e(String tag, String message) {
        store("ERROR", tag, message);
    }

    private void store(String level, String tag, String message) {
        logs.add(new LogLine(level, tag, message));
    }
}
