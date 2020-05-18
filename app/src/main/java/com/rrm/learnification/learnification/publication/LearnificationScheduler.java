package com.rrm.learnification.learnification.publication;

import java.util.Optional;

public interface LearnificationScheduler {
    void scheduleLearnification();

    void scheduleImminentLearnification();

    void triggerNextLearnification();

    boolean isLearnificationAvailable();

    Optional<Integer> secondsUntilNextLearnification();
}
