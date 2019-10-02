package com.rrm.learnification.learnification;

import java.time.LocalDateTime;

public interface ScheduleLog {
    boolean isAnythingScheduledForTomorrow();

    void mark(LocalDateTime localDateTime);
}
