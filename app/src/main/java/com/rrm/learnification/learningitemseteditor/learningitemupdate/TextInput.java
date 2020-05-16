package com.rrm.learnification.learningitemseteditor.learningitemupdate;

import com.rrm.learnification.learningitemseteditor.OnSubmitTextCommand;
import com.rrm.learnification.learningitemseteditor.OnTextChangeListener;

public interface TextInput {
    void setOnTextChangeListener(OnTextChangeListener onTextChangeListener);

    void setOnSubmitTextCommand(OnSubmitTextCommand onSubmitTextCommand);

    void clear();
}
