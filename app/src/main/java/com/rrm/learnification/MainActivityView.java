package com.rrm.learnification;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

interface MainActivityView {
    FloatingActionButton getLearnificationButton();

    ListView getLearnificationListView();

    LearningItem getTextInput();

    ArrayAdapter<String> getListViewAdapter(List<String> listViewContents);

    void setLearnificationButtonOnClickListener(View.OnClickListener onClickListener);
}
