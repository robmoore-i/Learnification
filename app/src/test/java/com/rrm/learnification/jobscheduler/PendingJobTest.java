package com.rrm.learnification.jobscheduler;

import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PendingJobTest {
    private final Class<?> serviceClass = Object.class;

    @Test
    public void canIdentifyAPendingJobOutOfAList() {
        Stream<PendingJob> pendingJobStream = Stream.of(
                new PendingJob(serviceClass.getName(), 100L),
                new PendingJob("com.rrm.learnification.someOtherClass", 100L),
                new PendingJob("com.rrm.learnification.someOtherClass", 100L)
        );

        assertTrue(pendingJobStream.anyMatch(pendingJob -> pendingJob.willTriggerService(serviceClass)));
    }

    @Test
    public void isIncomingLearnificationReturnsFalseIfItIsFartherInTheFutureThanTheGivenPeriodicity() {
        PendingJob pendingJob = new PendingJob(serviceClass.getName(), 100L);

        assertFalse(pendingJob.willTriggerBefore(50));
    }

    @Test
    public void isIncomingLearnificationReturnsTrueIfItIsCloserThanTheGivenPeriodicity() {
        PendingJob pendingJob = new PendingJob(serviceClass.getName(), 25L);

        assertTrue(pendingJob.willTriggerBefore(50));
    }
}