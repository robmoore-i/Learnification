package com.rrm.learnification;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ScheduleConfigurationFileParserTest {
    private final ScheduleConfigurationFileParser scheduleConfigurationFileParser = new ScheduleConfigurationFileParser();

    @Test
    public void itIgnoresBlankLines() {
        List<String> lines = new ArrayList<>();
        lines.add("");
        lines.add("earliestStartTimeDelayMs=1000");
        lines.add("latestStartTimeDelayMs=2000");

        PeriodicityRange periodicityRange = scheduleConfigurationFileParser.parseSchedulingConfigurationFile(lines);

        assertThat(periodicityRange.earliestStartTimeDelayMs, equalTo(1000));
        assertThat(periodicityRange.latestStartTimeDelayMs, equalTo(2000));
    }
}