package com.rrm.learnification.learningitemseteditor;

import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.EditText;
import android.widget.ImageView;

import com.rrm.learnification.R;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.storage.SqlLearningItemSetRecordStore;

class LearningItemSetTitle {
    private static final String LOG_TAG = "LearningItemSetTitle";

    private final AndroidLogger logger;

    private final SqlLearningItemSetRecordStore recordStore;
    private final SoftKeyboardView softKeyboardView;

    private final EditText textBox;
    private final ImageView changeIcon;

    LearningItemSetTitle(AndroidLogger logger, SqlLearningItemSetRecordStore recordStore, LearningItemSetTitleView learningItemSetTitleView) {
        this.logger = logger;
        this.recordStore = recordStore;
        this.softKeyboardView = learningItemSetTitleView;
        this.textBox = learningItemSetTitleView.textBox();
        this.changeIcon = learningItemSetTitleView.changeIcon();
        setTextBoxStyle();
        disableEditing();
    }

    public void set(String learningItemSetName) {
        textBox.setText(learningItemSetName);
    }

    private void setTextBoxStyle() {
        textBox.setTextColor(Color.BLACK);
        textBox.setTypeface(Typeface.DEFAULT_BOLD);
        textBox.setBackgroundResource(android.R.color.transparent);
    }

    private void disableEditing() {
        textBox.setFocusable(false);
        changeIcon.setImageResource(R.drawable.edit_pencil_icon);
        changeIcon.setOnClickListener(v -> {
            logger.i(LOG_TAG, "learning item set title clicked to edit");
            LearningItemSetTitle.this.startEditing();
        });
        softKeyboardView.hideSoftKeyboard();
    }

    private void startEditing() {
        enableEditing();
    }

    private void enableEditing() {
        textBox.setEnabled(true);
        textBox.setFocusableInTouchMode(true);
        textBox.requestFocus();
        changeIcon.setImageResource(R.drawable.save_icon);
        changeIcon.setOnClickListener(v -> {
            String updatedLearningItemSetTitle = textBox.getText().toString();
            logger.u(LOG_TAG, "renamed learning item set to '" + updatedLearningItemSetTitle + "'");
            logger.i(LOG_TAG, "learning item set title clicked to save");
            LearningItemSetTitle.this.save(updatedLearningItemSetTitle);
        });
        softKeyboardView.showSoftKeyboard();
    }

    private void save(String updatedLearningItemSetTitle) {
        disableEditing();
        recordStore.renameSet(updatedLearningItemSetTitle);
    }
}
