package com.rrm.learnification.logdump;

import androidx.recyclerview.widget.RecyclerView;

import com.rrm.learnification.logger.AndroidLogger;

import java.util.ArrayList;

class LogLineList {
    LogLineList(AndroidLogger logger, LogLineListView view, ArrayList<String> logs) {
        LogLineListViewAdaptor adapter = new LogLineListViewAdaptor(logger, logs);
        RecyclerView recyclerView = view.logLineList();
        recyclerView.setAdapter(adapter);
    }
}
