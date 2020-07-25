package com.rrm.learnification.dailyreport.creation;

import com.rrm.learnification.learnificationresultstorage.LearnificationResult;
import com.rrm.learnification.learnificationresultstorage.LearnificationResultSqlTableClient;
import com.rrm.learnification.time.AndroidClock;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class DailyReportTextGenerator {
    private final AndroidClock clock;
    private final LearnificationResultSqlTableClient storage;

    public DailyReportTextGenerator(AndroidClock clock, LearnificationResultSqlTableClient storage) {
        this.clock = clock;
        this.storage = storage;
    }

    public String getText() {
        LocalDateTime now = clock.now();
        List<LearnificationResult> learnificationResults = storage.readAll()
                .stream()
                .filter(learnificationResult -> learnificationResult.submittedOnDayOf(now))
                .collect(Collectors.toList());
        int numberOfCompletedLearnifications = learnificationResults.size();
        if (numberOfCompletedLearnifications == 0) {
            return "Tomorrow is a new day 🌞";
        }
        String pluralMarker = numberOfCompletedLearnifications > 1 ? "s" : "";
        return "Completed " + numberOfCompletedLearnifications + " learnification" + pluralMarker + " today! 🎉";
    }
}
