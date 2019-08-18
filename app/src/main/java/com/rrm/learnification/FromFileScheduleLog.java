package com.rrm.learnification;

class FromFileScheduleLog implements ScheduleLog {
    FromFileScheduleLog(FileStorageAdaptor fileStorageAdaptor) {
    }

    @Override
    public boolean isAnythingScheduledForTomorrow() {
        return true;
    }
}
