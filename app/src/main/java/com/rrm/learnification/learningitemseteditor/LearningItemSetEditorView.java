package com.rrm.learnification.learningitemseteditor;

import android.app.Activity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.rrm.learnification.R;
import com.rrm.learnification.common.LearningItemText;
import com.rrm.learnification.learningitemseteditor.learningitemupdate.AndroidTextWatcher;
import com.rrm.learnification.learningitemseteditor.learningitemupdate.UpdateLearningItemView;
import com.rrm.learnification.learningitemseteditor.toolbar.ToolbarView;
import com.rrm.learnification.learningitemseteditor.toolbar.ToolbarViewUpdate;
import com.rrm.learnification.learningitemseteditor.toolbar.viewparameters.ToolbarViewParameters;
import com.rrm.learnification.logger.AndroidLogger;

class LearningItemSetEditorView implements ToolbarView, LearningItemSetSelectorView, LearningItemSetTitleView, AddLearningItemView, LearningItemListView,
        UpdateLearningItemView, SoftKeyboardView {
    private static final String LOG_TAG = "LearningItemSetEditorView";

    private final AndroidLogger logger;
    private final LearningItemSetEditorActivity activity;

    LearningItemSetEditorView(AndroidLogger logger, LearningItemSetEditorActivity activity) {
        this.logger = logger;
        this.activity = activity;

        activity.setSupportActionBar(activity.findViewById(R.id.toolbar));

        learningItemsList().setEnabled(true);
        setLearningItemListInterItemPadding();
        setLearningItemListViewBounds();
        setToolbarTitle("Learnification");
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

    private void setLearningItemListViewBounds() {
        RecyclerView recyclerView = this.learningItemsList();
        // A bit of a hack: Makes sure that the recycler view height doesn't spill over the bottom of the screen,
        //                  because if it does this, then the items below the bottom become invisible.
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int maxHeight = displayMetrics.heightPixels;
        logger.i(LOG_TAG, "window height is " + maxHeight);

        int halfTheScreenVertically = maxHeight / 2;
        logger.i(LOG_TAG, "setting learning item list view height to " + halfTheScreenVertically);

        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        params.height = halfTheScreenVertically;
        recyclerView.setLayoutParams(params);
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
    public Spinner learningItemSetSelector() {
        return activity.findViewById(R.id.learning_item_set_selector);
    }

    @Override
    public EditText textBox() {
        return activity.findViewById(R.id.learning_item_set_name_textbox);
    }

    @Override
    public ImageView changeIcon() {
        return activity.findViewById(R.id.learning_item_set_name_change_icon);
    }

    @Override
    public LearningItemText addLearningItemTextInput() {
        return new LearningItemText(
                leftEditText().getText().toString().trim(),
                rightEditText().getText().toString().trim());
    }

    @Override
    public Button addLearningItemButton() {
        return activity.findViewById(R.id.add_learning_item_button);
    }

    @Override
    public void addLearningItemOnTextChangeListener(OnTextChangeListener onTextChangeListener) {
        onTextChangeListener.addTextSource(new AndroidTextWatcher("left", leftEditText()));
        onTextChangeListener.addTextSource(new AndroidTextWatcher("right", rightEditText()));
    }

    @Override
    public void addLearningItemOnSubmitTextCommand(OnSubmitTextCommand onSubmitTextCommand) {
        TextView.OnEditorActionListener onEditorActionListener = (textView, actionId, event) -> {
            logger.i(LOG_TAG, "learning item text input received an action with id '" + actionId + "'");
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                logger.i(LOG_TAG, "learning item submitted via the virtual keyboard");
                onSubmitTextCommand.onSubmit();
            }
            return true;
        };
        leftEditText().setOnEditorActionListener(onEditorActionListener);
        rightEditText().setOnEditorActionListener(onEditorActionListener);
    }

    @Override
    public void addLearningItemClearTextInput() {
        leftEditText().setText("");
        rightEditText().setText("");
    }

    @Override
    public RecyclerView learningItemsList() {
        return activity.findViewById(R.id.learning_item_list);
    }

    @Override
    public Button updateLearningItemButton() {
        return activity.findViewById(R.id.update_learning_item_button);
    }

    @Override
    public void hideSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View focusedView = activity.getCurrentFocus();
        if (focusedView != null) {
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }

    @Override
    public void showSoftKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View focusedView = activity.getCurrentFocus();
        if (focusedView != null) {
            inputMethodManager.showSoftInput(focusedView, 0);
        }
    }

    void addToolbarViewUpdate(ToolbarViewUpdate toolbarViewUpdate) {
        int period = 1000;
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(period);
                        activity.runOnUiThread(() -> toolbarViewUpdate.update(LearningItemSetEditorView.this));
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

    private EditText leftEditText() {
        return activity.findViewById(R.id.left_input);
    }

    private EditText rightEditText() {
        return activity.findViewById(R.id.right_input);
    }
}
