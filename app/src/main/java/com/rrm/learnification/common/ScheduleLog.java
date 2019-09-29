package com.rrm.learnification.common;

import java.time.LocalDateTime;

interface ScheduleLog {
    boolean isAnythingScheduledForTomorrow();

    void mark(LocalDateTime localDateTime);
}
