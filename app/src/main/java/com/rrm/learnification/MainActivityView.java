package com.rrm.learnification;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

class MainActivityView implements ToolbarView, AddLearningItemView, PeriodicityPickerView, LearningItemListView {
    private final MainActivity activity;

    MainActivityView(MainActivity activity) {
        this.activity = activity;

        activity.setSupportActionBar(toolbar());

        learningItemsList().setEnabled(true);
        setLearningItemListInterItemPadding();
        setLearningItemListViewBounds();
    }

    private void setLearningItemListViewBounds() {
        RecyclerView recyclerView = this.learningItemsList();
        // A bit of a hack: Makes sure that the recycler view height doesn't spill over the bottom of the screen,
        //                  because if it does this, then the items below the bottom become invisible.
        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        params.height = 500;
        recyclerView.setLayoutParams(params);
    }

    private void setLearningItemListInterItemPadding() {
        RecyclerView recyclerView = this.learningItemsList();
        // RecyclerViews must have a layout manager
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        // Provide padding between the elements of the recycler list view
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(activity, linearLayoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public Toolbar toolbar() {
        return activity.findViewById(R.id.toolbar);
    }

    @Override
    public LearningItem getLearningItemTextInput() {
        String left = ((EditText) activity.findViewById(R.id.left_input)).getText().toString();
        String right = ((EditText) activity.findViewById(R.id.right_input)).getText().toString();
        return new LearningItem(left, right);
    }

    @Override
    public Button addLearningItemButton() {
        return activity.findViewById(R.id.add_learning_item_button);
    }

    @Override
    public NumberPicker periodicityPicker() {
        return activity.findViewById(R.id.periodicity_picker);
    }

    @Override
    public RecyclerView learningItemsList() {
        return activity.findViewById(R.id.learnifications_list);
    }
}
