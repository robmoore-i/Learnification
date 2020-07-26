package com.rrm.learnification.jobs;

import com.rrm.learnification.table.AndroidTable;
import com.rrm.learnification.time.AndroidClock;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
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
    public void addsSelfAsRowToTableWithCompletionTime() {
        AndroidTable mockTable = mock(AndroidTable.class);
        AndroidClock stubClock = mock(AndroidClock.class);
        when(stubClock.now()).thenReturn(LocalDateTime.of(2020, 7, 26, 19, 45));
        long twoHours = 2 * 60 * 60 * 1000;
        PendingJob pendingJob = new PendingJob(serviceClass.getName(), twoHours,
                LocalDateTime.of(2020, 7, 26, 13, 0), id);

        pendingJob.addAsRowOf(stubClock, mockTable);

        verify(mockTable, times(1)).addRow(serviceClass.getSimpleName(), "9:45pm today");
    }
}