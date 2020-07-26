package com.rrm.learnification.jobdump;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rrm.learnification.R;
import com.rrm.learnification.files.AndroidInternalStorageAdaptor;
import com.rrm.learnification.jobs.AndroidJobScheduler;
import com.rrm.learnification.jobs.JobIdGenerator;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.table.AndroidTable;
import com.rrm.learnification.time.AndroidClock;

public class JobDumpActivity extends AppCompatActivity implements ScheduledJobTableView {
    private static final String LOG_TAG = "JobDumpActivity";
    private final AndroidLogger logger = new AndroidLogger();
    private final AndroidClock clock = new AndroidClock();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobdump);
        logger.i(LOG_TAG, "Started activity");

        AndroidJobScheduler androidJobScheduler = new AndroidJobScheduler(logger, clock, this,
                new JobIdGenerator(logger, new AndroidInternalStorageAdaptor(logger, this)));

        new ScheduledJobsTable(this, androidJobScheduler);
    }

    @Override
    public AndroidTable scheduledJobsTable() {
        return new AndroidTable(this, findViewById(R.id.scheduled_jobs_table));
    }
}
