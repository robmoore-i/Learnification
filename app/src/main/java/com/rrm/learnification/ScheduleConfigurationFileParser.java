package com.rrm.learnification;

import java.util.List;
import java.util.stream.Collectors;

class ScheduleConfigurationFileParser {
    PeriodicityRange parseSchedulingConfigurationFile(List<String> lines) {
        List<String> nonEmptyLines = lines.stream().filter(line -> !line.isEmpty()).collect(Collectors.toList());

        int earliestStartTimeDelayMs = Integer.parseInt(nonEmptyLines.get(0).split("=")[1]);
        int latestStartTimeDelayMs = Integer.parseInt(nonEmptyLines.get(1).split("=")[1]);
        return new PeriodicityRange(earliestStartTimeDelayMs, latestStartTimeDelayMs);
    }
}
