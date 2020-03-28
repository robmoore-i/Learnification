package com.rrm.learnification.learningitemseteditor;

import android.widget.EditText;
import android.widget.ImageView;

interface LearningItemSetTitleView extends SoftKeyboardView {
    EditText textBox();

    ImageView changeIcon();
}
