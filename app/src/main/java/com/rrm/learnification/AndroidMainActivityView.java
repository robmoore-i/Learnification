package com.rrm.learnification;

import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.util.stream.Collectors;

class AndroidMainActivityView implements MainActivityView {
    private static final String LOG_TAG = "AndroidMainActivityView";

    private final MainActivity activity;
    private final AndroidLogger logger;

    AndroidMainActivityView(AndroidLogger logger, MainActivity activity) {
        this.logger = logger;
        this.activity = activity;

        activity.setSupportActionBar(toolbar());
    }

    @Override
    public LearnificationListViewAdaptor createLearnificationListViewDataBinding(OnSwipeCommand onSwipeCommand, LearnificationRepository learnificationRepository) {
        RecyclerView recyclerView = activity.findViewById(R.id.learnifications_list);

        // RecyclerViews must have a layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        // Provide padding between the elements of the recycler list view
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(activity, linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setEnabled(true);

        // A bit of a hack: Makes sure that the recycler view height doesn't spill over the bottom of the screen,
        //                  because if it does this, then the items below the bottom become invisible.
        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        params.height = 500;
        recyclerView.setLayoutParams(params);

        // Set up data binding between the learnificationRepository and the RecyclerView.
        LearnificationListViewAdaptor adapter = new LearnificationListViewAdaptor(learnificationRepository.learningItems().stream().map(LearningItem::asSingleString).collect(Collectors.toList()));
        recyclerView.setAdapter(adapter);

        // Set up onSwipe behaviour using the onSwipeCommand
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int listViewIndex = viewHolder.getAdapterPosition();
                logger.v(LOG_TAG, "learning-item swiped at position " + listViewIndex + " to the " + swipeDirectionToString(swipeDir));
                onSwipeCommand.onSwipe(adapter, listViewIndex);
            }

            private String swipeDirectionToString(int swipeDir) {
                if (swipeDir == ItemTouchHelper.LEFT) {
                    return "left";
                } else if (swipeDir == ItemTouchHelper.RIGHT) {
                    return "right";
                } else {
                    return "neither left nor right";
                }
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView1, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return adapter;
    }

    @Override
    public LearningItem getLearningItemTextInput() {
        String left = ((EditText) activity.findViewById(R.id.left_input)).getText().toString();
        String right = ((EditText) activity.findViewById(R.id.right_input)).getText().toString();
        return new LearningItem(left, right);
    }


    @Override
    public NumberPicker periodicityPicker() {
        return activity.findViewById(R.id.periodicity_picker);
    }

    @Override
    public Button addLearningItemButton() {
        return activity.findViewById(R.id.add_learning_item_button);
    }

    @Override
    public Toolbar toolbar() {
        return activity.findViewById(R.id.toolbar);
    }
}
