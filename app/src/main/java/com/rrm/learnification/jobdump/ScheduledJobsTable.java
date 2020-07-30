package com.rrm.learnification.jobdump;

import com.rrm.learnification.jobs.JobScheduler;

class ScheduledJobsTable {
    public ScheduledJobsTable(ScheduledJobTableView view, JobScheduler jobScheduler) {
        jobScheduler.insertJobInfoInto(view.scheduledJobsTable());
    }
}
