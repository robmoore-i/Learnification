package com.rrm.learnification;

import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;

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

    @Override
    public ArrayAdapter<String> getListViewAdapter(List<String> listViewContents) {
        return new ArrayAdapter<>(activity, android.R.layout.simple_list_item_1, listViewContents);
    }

    @Override
    public void setLearnificationButtonOnClickListener(View.OnClickListener onClickListener) {
        FloatingActionButton button = getLearnificationButton();
        button.setOnClickListener(onClickListener);
    }
}
