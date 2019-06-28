package com.rrm.learnification;

import android.support.design.widget.FloatingActionButton;
import android.widget.EditText;
import android.widget.ListView;

class AndroidMainActivityView implements MainActivityView {
    private MainActivity activity;

    AndroidMainActivityView(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public FloatingActionButton getLearnificationButton() {
        return activity.findViewById(R.id.addLearnificationButton);
    }

    @Override
    public ListView getLearnificationListView() {
        return activity.findViewById(R.id.learnificationsListView);
    }

    @Override
    public LearningItem getTextInput() {
        String left = ((EditText) activity.findViewById(R.id.left_input)).getText().toString();
        String right = ((EditText) activity.findViewById(R.id.right_input)).getText().toString();
        return new LearningItem(left, right);
    }
}
