package com.rrm.learnification.dailyreport.creation;

import com.rrm.learnification.learnificationresultstorage.LearnificationPrompt;
import com.rrm.learnification.learnificationresultstorage.LearnificationResult;
import com.rrm.learnification.learnificationresultstorage.LearnificationResultEvaluation;
import com.rrm.learnification.learnificationresultstorage.LearnificationResultSqlTableClient;
import com.rrm.learnification.time.AndroidClock;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DailyReportTextGeneratorTest {
    private final LearnificationResultSqlTableClient stubStorage = mock(LearnificationResultSqlTableClient.class);
    private final AndroidClock stubClock = mock(AndroidClock.class);
    private final DailyReportTextGenerator dailyReportTextGenerator = new DailyReportTextGenerator(stubClock, stubStorage);

    @Test
    public void readsOneLearnificationResultFromStorage() {
        List<LearnificationResult> results = Collections.singletonList(
                new LearnificationResult(LocalDateTime.of(2020, 7, 25, 11, 30),
                        LearnificationResultEvaluation.CORRECT, new LearnificationPrompt("given", "expected")));
        when(stubStorage.readAll()).thenReturn(results);
        when(stubClock.now()).thenReturn(LocalDateTime.of(2020, 7, 25, 21, 0));

        assertEquals("Completed 1 learnification today! 🎉", dailyReportTextGenerator.getText());
    }

    @Test
    public void readsTwoLearnificationResultsFromStorage() {
        List<LearnificationResult> results = Arrays.asList(
                new LearnificationResult(LocalDateTime.of(2020, 7, 25, 11, 30),
                        LearnificationResultEvaluation.CORRECT, new LearnificationPrompt("given1", "expected1")),
                new LearnificationResult(LocalDateTime.of(2020, 7, 25, 12, 30),
                        LearnificationResultEvaluation.CORRECT, new LearnificationPrompt("given2", "expected2")));
        when(stubStorage.readAll()).thenReturn(results);
        when(stubClock.now()).thenReturn(LocalDateTime.of(2020, 7, 25, 21, 0));

        assertEquals("Completed 2 learnifications today! 🎉", dailyReportTextGenerator.getText());
    }

    @Test
    public void doesntCountLearnificationResultsFromOtherDays() {
        List<LearnificationResult> results = Arrays.asList(
                new LearnificationResult(LocalDateTime.of(2020, 7, 24, 11, 30),
                        LearnificationResultEvaluation.CORRECT, new LearnificationPrompt("anotherDayGiven", "anotherDayExpected")),
                new LearnificationResult(LocalDateTime.of(2020, 7, 25, 11, 30),
                        LearnificationResultEvaluation.CORRECT, new LearnificationPrompt("given1", "expected1")),
                new LearnificationResult(LocalDateTime.of(2020, 7, 25, 12, 30),
                        LearnificationResultEvaluation.CORRECT, new LearnificationPrompt("given2", "expected2")));
        when(stubStorage.readAll()).thenReturn(results);
        when(stubClock.now()).thenReturn(LocalDateTime.of(2020, 7, 25, 21, 0));

        assertEquals("Completed 2 learnifications today! 🎉", dailyReportTextGenerator.getText());
    }

    @Test
    public void sendsEncouragementIfNoneCompleted() {
        List<LearnificationResult> results = Collections.emptyList();
        when(stubStorage.readAll()).thenReturn(results);
        when(stubClock.now()).thenReturn(LocalDateTime.of(2020, 7, 25, 21, 0));

        assertEquals("Tomorrow is a new day 🌞", dailyReportTextGenerator.getText());
    }
}