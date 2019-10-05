package com.rrm.learnification.jobscheduler;

class PendingJob {
    private final String serviceClassName;
    private final long earliestStartTimeDelayMs;

    PendingJob(String serviceClassName, long earliestStartTimeDelayMs) {
        this.serviceClassName = serviceClassName;
        this.earliestStartTimeDelayMs = earliestStartTimeDelayMs;
    }

    /**
     * @param serviceClass The service class for the jobs you want to check for
     * @return True, if the PendingJob is for the given serviceClass
     */
    boolean willTriggerService(Class<?> serviceClass) {
        return serviceClassName.equals(serviceClass.getName());
    }

    /**
     * @param maxDelayTimeMs The delay time you're checking against, in milliseconds
     * @return True, if the PendingJob will trigger before the given maxDelayTime elapses.
     */
    boolean willTriggerBefore(int maxDelayTimeMs) {
        return earliestStartTimeDelayMs < maxDelayTimeMs;
    }

    Long delayTime() {
        return earliestStartTimeDelayMs;
    }
}
