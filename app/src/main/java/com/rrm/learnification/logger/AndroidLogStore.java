package com.rrm.learnification.logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AndroidLogStore {
    private final LogcatLogParser logcatLogParser;

    public AndroidLogStore(LogcatLogParser logcatLogParser) {
        this.logcatLogParser = logcatLogParser;
    }

    public List<String> dump() {
        try {
            Process process = Runtime.getRuntime().exec("logcat -d");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            ArrayList<LogLine> logs = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                logs.add(logcatLogParser.parse(line));
            }
            Collections.reverse(logs);
            return logs.stream().filter(LogLine::notEmpty).map(LogLine::toString).collect(Collectors.toList());
        } catch (IOException e) {
            return Collections.singletonList("Failed to dump raw logs from logcat due to an IO exception '" + e.getMessage() + "'");
        }
    }
}
