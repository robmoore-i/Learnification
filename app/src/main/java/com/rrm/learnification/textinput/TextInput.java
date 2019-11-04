package com.rrm.learnification.textinput;

public interface TextInput {
    void setOnTextChangeListener(OnTextChangeListener onTextChangeListener);

    void clear();

    void setOnSubmitAction(OnSubmitTextAction onSubmitTextAction);
}
