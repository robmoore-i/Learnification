package com.rrm.learnification.main;

import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import com.rrm.learnification.R;
import com.rrm.learnification.common.AndroidLogger;
import com.rrm.learnification.common.AndroidTextWatcher;
import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.common.OnTextChangeListener;
import com.rrm.learnification.settings.PeriodicityPickerView;
import com.rrm.learnification.toolbar.ToolbarView;
import com.rrm.learnification.toolbar.ToolbarViewParameters;
import com.rrm.learnification.toolbar.ToolbarViewUpdate;

class MainActivityView implements ToolbarView, AddLearningItemView, PeriodicityPickerView, LearningItemListView {
    private static final String LOG_TAG = "MainActivityView";

    private final AndroidLogger logger;
    private final MainActivity activity;

    MainActivityView(AndroidLogger logger, MainActivity activity) {
        this.logger = logger;
        this.activity = activity;

        activity.setSupportActionBar(activity.findViewById(R.id.toolbar));

        learningItemsList().setEnabled(true);
        setLearningItemListInterItemPadding();
        setLearningItemListViewBounds();
        setToolbarTitle("Learnification");
    }

    private void setLearningItemListViewBounds() {
        RecyclerView recyclerView = this.learningItemsList();
        // A bit of a hack: Makes sure that the recycler view height doesn't spill over the bottom of the screen,
        //                  because if it does this, then the items below the bottom become invisible.
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int maxHeight = displayMetrics.heightPixels;
        logger.v(LOG_TAG, "window height is " + maxHeight);

        int halfTheScreenVertically = maxHeight / 2;
        logger.v(LOG_TAG, "setting learning item list view height to " + halfTheScreenVertically);

        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        params.height = halfTheScreenVertically;
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
    public void updateToolbar(ToolbarViewParameters toolbarViewParameters) {
        setToolbarTitle(toolbarViewParameters.toolbarTitle());
    }

    @Override
    public Button toolbarButton() {
        return activity.findViewById(R.id.toolbar_button);
    }

    @Override
    public LearningItem getLearningItemTextInput() {
        return new LearningItem(
                ((EditText) activity.findViewById(R.id.left_input)).getText().toString(),
                ((EditText) activity.findViewById(R.id.right_input)).getText().toString());
    }

    @Override
    public Button addLearningItemButton() {
        return activity.findViewById(R.id.add_learning_item_button);
    }

    @Override
    public void setOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
        onTextChangeListener.addTextSource(new AndroidTextWatcher("left", activity.findViewById(R.id.left_input)));
        onTextChangeListener.addTextSource(new AndroidTextWatcher("right", activity.findViewById(R.id.right_input)));
    }

    @Override
    public void clearTextInput() {
        activity.<EditText>findViewById(R.id.left_input).setText("");
        activity.<EditText>findViewById(R.id.right_input).setText("");
    }

    @Override
    public NumberPicker periodicityPicker() {
        return activity.findViewById(R.id.periodicity_picker);
    }

    @Override
    public RecyclerView learningItemsList() {
        return activity.findViewById(R.id.learnifications_list);
    }

    void addToolbarViewUpdate(ToolbarViewUpdate toolbarViewUpdate) {
        int period = 1000;
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(period);
                        activity.runOnUiThread(() -> toolbarViewUpdate.update(MainActivityView.this));
                    }
                } catch (InterruptedException ignored) {
                }
            }
        };
        thread.start();
    }

    private void setToolbarTitle(String title) {
        activity.setTitle(title);
    }
}
