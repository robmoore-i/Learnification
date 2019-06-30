package com.rrm.learnification;

import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
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
    public LearnificationListViewAdaptor getLearnificationList(OnSwipeCommand onSwipeCommand, LearnificationRepository learnificationRepository) {
        RecyclerView recyclerView = activity.findViewById(R.id.learnifications_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setEnabled(true);
        LearnificationListViewAdaptor adapter = new LearnificationListViewAdaptor(learnificationRepository.learningItemsAsStringList());
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir) {
                onSwipeCommand.onSwipe(adapter, viewHolder.getAdapterPosition());
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView1, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.setAdapter(adapter);
        return adapter;
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
