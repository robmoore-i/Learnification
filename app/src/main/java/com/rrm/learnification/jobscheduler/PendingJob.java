package com.rrm.learnification.jobscheduler;

class PendingJob {
    private final String serviceClassName;
    private final long earliestStartTimeDelayMs;
    private final long latestStartTimeDelayMs;

    PendingJob(String serviceClassName, long earliestStartTimeDelayMs, long latestStartTimeDelayMs) {
        this.serviceClassName = serviceClassName;
        this.earliestStartTimeDelayMs = earliestStartTimeDelayMs;
        this.latestStartTimeDelayMs = latestStartTimeDelayMs;
    }


    /**
     * @param serviceClass
     * @return True, if the PendingJob is for the given serviceClass
     */
    boolean willTriggerService(Class<?> serviceClass) {
        return serviceClassName.equals(serviceClass.getName());
    }

    /**
     * @param maxDelayTimeMs
     * @return True, if the PendingJob will trigger before the given maxDelayTime elapses.
     */
    boolean willTriggerBefore(int maxDelayTimeMs) {
        return earliestStartTimeDelayMs < maxDelayTimeMs;
    }
}
