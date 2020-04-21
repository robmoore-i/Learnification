package com.rrm.learnification.logdump;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.rrm.learnification.R;

import java.util.ArrayList;

public class LogDumpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logdump);

        LogDumpActivityBundle activityStartupParameters = LogDumpActivityBundle.fromBundle(this.getIntent().getExtras());
        ArrayList<String> logs = activityStartupParameters.logs;

        // Todo: Set up a RecyclerView where each element is a log line. The view should be
        //       scrollable and you should be able to filter by log level.
    }
}
