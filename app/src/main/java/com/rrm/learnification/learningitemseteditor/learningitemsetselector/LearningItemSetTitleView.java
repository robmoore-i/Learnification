package com.rrm.learnification.learningitemseteditor.learningitemsetselector;

import android.widget.EditText;
import android.widget.ImageView;

import com.rrm.learnification.learningitemseteditor.SoftKeyboardView;

public interface LearningItemSetTitleView extends SoftKeyboardView {
    EditText textBox();

    ImageView changeIcon();
}
