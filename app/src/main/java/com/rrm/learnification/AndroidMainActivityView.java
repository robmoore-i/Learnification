package com.rrm.learnification;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

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
    public RecyclerView getLearnificationList() {
        RecyclerView recyclerView = activity.findViewById(R.id.learnifications_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        return recyclerView;
    }

    @Override
    public LearningItem getTextInput() {
        String left = ((EditText) activity.findViewById(R.id.left_input)).getText().toString();
        String right = ((EditText) activity.findViewById(R.id.right_input)).getText().toString();
        return new LearningItem(left, right);
    }

    @Override
    public void setLearnificationButtonOnClickListener(View.OnClickListener onClickListener) {
        FloatingActionButton button = getLearnificationButton();
        button.setOnClickListener(onClickListener);
    }
}
