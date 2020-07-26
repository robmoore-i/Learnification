package com.rrm.learnification.jobdump;

import com.rrm.learnification.jobs.JobScheduler;
import com.rrm.learnification.table.AndroidTable;

class ScheduledJobsTable {
    public ScheduledJobsTable(ScheduledJobTableView view, JobScheduler jobScheduler) {
        AndroidTable table = view.scheduledJobsTable();
        jobScheduler.insertJobInfoInto(table);
    }
}
