package com.rrm.learnification.schedulelog;

import java.time.LocalDateTime;

public interface ScheduleLog {
    boolean isAnythingScheduledForTomorrow();

    void mark(LocalDateTime localDateTime);
}
