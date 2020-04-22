package com.rrm.learnification.logdump;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rrm.learnification.R;
import com.rrm.learnification.logger.AndroidLogger;

import java.util.ArrayList;

public class LogDumpActivity extends AppCompatActivity implements LogLineListView {
    private final AndroidLogger logger = new AndroidLogger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logdump);

        LogDumpActivityBundle activityStartupParameters = LogDumpActivityBundle.fromBundle(this.getIntent().getExtras());
        ArrayList<String> logs = activityStartupParameters.logs;

        LogLineList logLineList = new LogLineList(logger, this, logs);

        // Todo: Set up a RecyclerView where each element is a log line. The view should be
        //       scrollable and you should be able to filter by log level.
        setLogLineListInterItemPadding();
    }

    @Override
    public RecyclerView learningItemsList() {
        return findViewById(R.id.log_line_list);
    }

    private void setLogLineListInterItemPadding() {
        RecyclerView recyclerView = this.learningItemsList();
        // RecyclerViews must have a layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        // Provide padding between the elements of the recycler list view
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
