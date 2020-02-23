package com.rrm.learnification.learningitemseteditor;

import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.EditText;
import android.widget.ImageView;

import com.rrm.learnification.R;
import com.rrm.learnification.logger.AndroidLogger;

class LearningItemSetTitle {
    private static final String LOG_TAG = "LearningItemSetTitle";

    private final AndroidLogger logger;

    private final SoftKeyboardView softKeyboardView;

    private final EditText textBox;
    private final ImageView changeIcon;

    LearningItemSetTitle(AndroidLogger logger, SoftKeyboardView softKeyboardView, EditText textBox, ImageView changeIcon) {
        this.logger = logger;
        this.softKeyboardView = softKeyboardView;
        this.textBox = textBox;
        this.changeIcon = changeIcon;
        setTextBoxStyle();
        disableEditing();
    }

    void setLearningItemSetName(String learningItemSetName) {
        textBox.setText(learningItemSetName);
    }

    private void disableEditing() {
        textBox.setFocusable(false);
        changeIcon.setImageResource(R.drawable.edit_pencil_icon);
        changeIcon.setOnClickListener(v -> {
            logger.v(LOG_TAG, "learning item set title clicked to edit");
            LearningItemSetTitle.this.enableEditing();
        });
        softKeyboardView.hideSoftKeyboard();
    }

    private void enableEditing() {
        textBox.setEnabled(true);
        textBox.setFocusableInTouchMode(true);
        textBox.requestFocus();
        changeIcon.setImageResource(R.drawable.save_icon);
        changeIcon.setOnClickListener(v -> {
            logger.v(LOG_TAG, "learning item set title clicked to save");
            LearningItemSetTitle.this.save();
        });
        softKeyboardView.showSoftKeyboard();
    }

    private void save() {
        disableEditing();
    }

    private void setTextBoxStyle() {
        textBox.setTextColor(Color.BLACK);
        textBox.setTypeface(Typeface.DEFAULT_BOLD);
        textBox.setBackgroundResource(android.R.color.transparent);
    }
}
