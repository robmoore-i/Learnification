package com.rrm.learnification.textinput;

public interface TextInput {
    void setOnTextChangeListener(OnTextChangeListener onTextChangeListener);

    void setOnSubmitTextCommand(OnSubmitTextCommand onSubmitTextCommand);

    void clear();
}
