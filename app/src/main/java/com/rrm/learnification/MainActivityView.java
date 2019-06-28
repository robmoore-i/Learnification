package com.rrm.learnification;

import android.support.design.widget.FloatingActionButton;
import android.widget.ListView;

interface MainActivityView {
    FloatingActionButton getLearnificationButton();

    ListView getLearnificationListView();

    LearningItem getTextInput();
}
