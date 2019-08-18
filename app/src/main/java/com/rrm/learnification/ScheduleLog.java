package com.rrm.learnification;

import java.time.LocalDateTime;

interface ScheduleLog {
    boolean isAnythingScheduledForTomorrow();

    void mark(LocalDateTime localDateTime);
}
