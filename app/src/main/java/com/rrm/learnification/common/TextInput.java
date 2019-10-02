package com.rrm.learnification.common;

import com.rrm.learnification.main.OnTextChangeListener;

public interface TextInput {
    void setOnTextChangeListener(OnTextChangeListener onTextChangeListener);

    void clear();
}
