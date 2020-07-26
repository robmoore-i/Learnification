package com.rrm.learnification.logdump;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rrm.learnification.R;
import com.rrm.learnification.logger.AndroidLogStore;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.logger.LogcatLogParser;

import java.util.ArrayList;

public class LogDumpActivity extends AppCompatActivity implements LogLineListView {
    private final AndroidLogger logger = new AndroidLogger();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logdump);

        ArrayList<String> logs = new ArrayList<>(new AndroidLogStore(new LogcatLogParser()).dump());

        LogLineList logLineList = new LogLineList(logger, this, logs);

        setLogLineListInterItemPadding();
    }

    @Override
    public RecyclerView logLineList() {
        return findViewById(R.id.log_line_list);
    }

    private void setLogLineListInterItemPadding() {
        RecyclerView recyclerView = this.logLineList();
        // RecyclerViews must have a layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        // Provide padding between the elements of the recycler list view
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
