package com.rrm.learnification.jobs;

import com.rrm.learnification.table.Table;
import com.rrm.learnification.time.AndroidClock;

import org.junit.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PendingJobTest {
    private final Class<?> serviceClass = Object.class;
    private final LocalDateTime nineAmOctSixth = LocalDateTime.of(2019, 10, 6, 9, 0, 0);
    private final int id = 1;

    @Test
    public void canIdentifyAPendingJobOutOfAList() {
        Stream<PendingJob> pendingJobStream = Stream.of(
                new PendingJob(serviceClass.getName(), 100L, nineAmOctSixth, id),
                new PendingJob("com.rrm.learnification.someOtherClass", 100L, nineAmOctSixth, id),
                new PendingJob("com.rrm.learnification.someOtherClass", 100L, nineAmOctSixth, id)
        );

        assertTrue(pendingJobStream.anyMatch(pendingJob -> pendingJob.willTriggerService(serviceClass)));
    }

    @Test
    public void isIncomingLearnificationReturnsFalseIfItIsFartherInTheFutureThanTheGivenDelayRange() {
        PendingJob pendingJob = new PendingJob(serviceClass.getName(), 100L, nineAmOctSixth, id);

        assertFalse(pendingJob.hasDelayTimeNoMoreThan(50));
    }

    @Test
    public void isIncomingLearnificationReturnsTrueIfItIsCloserThanTheGivenDelayRange() {
        PendingJob pendingJob = new PendingJob(serviceClass.getName(), 25L, nineAmOctSixth, id);

        assertTrue(pendingJob.hasDelayTimeNoMoreThan(50));
    }

    @Test
    public void addsSelfAsRowToTableWithServiceClassSimpleNameAndCorrectCompletionTime() {
        Table mockTable = mock(Table.class);

        addPendingJobToTable(mockTable, serviceClass.getName());

        verify(mockTable, times(1)).addRow(serviceClass.getSimpleName(), "9:45pm today");
    }

    @Test
    public void omitsPublishingServiceInNameWhenAddingSelfAsRowToTable() {
        Table mockTable = mock(Table.class);

        addPendingJobToTable(mockTable, "DailyReportPublishingService");

        verify(mockTable, times(1)).addRow(eq("DailyReport"), anyString());
    }

    @Test
    public void doesntUseTheFullyQualifiedNameOfServiceWhenAddingSelfAsRowToTable() {
        Table mockTable = mock(Table.class);

        addPendingJobToTable(mockTable, "com.rrm.learnification.stuff.DailyReportPublishingService");

        verify(mockTable, times(1)).addRow(eq("DailyReport"), anyString());
    }

    @Test
    public void handlesPastScheduledTimeGracefully() {
        PendingJob pendingJob = new PendingJob(serviceClass.getName(), 5000L, nineAmOctSixth, 1);
        assertThat(pendingJob.msUntilExecution(nineAmOctSixth.plus(6000L, ChronoUnit.MILLIS)),
                equalTo(Optional.empty()));
    }

    private void addPendingJobToTable(Table mockTable, String serviceClassName) {
        AndroidClock stubClock = mock(AndroidClock.class);
        when(stubClock.now()).thenReturn(LocalDateTime.of(2020, 7, 26, 19, 45));
        long twoHours = 2 * 60 * 60 * 1000;
        PendingJob pendingJob = new PendingJob(serviceClassName, twoHours,
                LocalDateTime.of(2020, 7, 26, 13, 0), id);
        pendingJob.addAsRowOf(stubClock, mockTable);
    }
}