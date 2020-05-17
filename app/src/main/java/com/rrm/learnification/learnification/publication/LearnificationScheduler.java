package com.rrm.learnification.learnification.publication;

import java.util.Optional;

public interface LearnificationScheduler {
    void scheduleJob();

    void scheduleImminentJob();

    void triggerNext();

    boolean learnificationAvailable();

    Optional<Integer> secondsUntilNextLearnification();
}
