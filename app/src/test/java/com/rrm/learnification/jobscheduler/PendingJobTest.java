package com.rrm.learnification.jobscheduler;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PendingJobTest {
    private final Class<?> serviceClass = Object.class;
    private final LocalDateTime nineAmOctSixth = LocalDateTime.of(2019, 10, 6, 9, 0, 0);

    @Test
    public void canIdentifyAPendingJobOutOfAList() {
        Stream<PendingJob> pendingJobStream = Stream.of(
                new PendingJob(serviceClass.getName(), 100L, nineAmOctSixth),
                new PendingJob("com.rrm.learnification.someOtherClass", 100L, nineAmOctSixth),
                new PendingJob("com.rrm.learnification.someOtherClass", 100L, nineAmOctSixth)
        );

        assertTrue(pendingJobStream.anyMatch(pendingJob -> pendingJob.willTriggerService(serviceClass)));
    }

    @Test
    public void isIncomingLearnificationReturnsFalseIfItIsFartherInTheFutureThanTheGivenPeriodicity() {
        PendingJob pendingJob = new PendingJob(serviceClass.getName(), 100L, nineAmOctSixth);

        assertFalse(pendingJob.hasDelayTimeNoMoreThan(50));
    }

    @Test
    public void isIncomingLearnificationReturnsTrueIfItIsCloserThanTheGivenPeriodicity() {
        PendingJob pendingJob = new PendingJob(serviceClass.getName(), 25L, nineAmOctSixth);

        assertTrue(pendingJob.hasDelayTimeNoMoreThan(50));
    }
}