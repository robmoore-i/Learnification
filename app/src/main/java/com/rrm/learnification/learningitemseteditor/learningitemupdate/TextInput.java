package com.rrm.learnification.learningitemseteditor.learningitemupdate;

import com.rrm.learnification.learningitemseteditor.learningitemadd.OnSubmitTextCommand;
import com.rrm.learnification.learningitemseteditor.learningitemlist.dynamicbuttons.OnTextChangeListener;

public interface TextInput {
    void setOnTextChangeListener(OnTextChangeListener onTextChangeListener);

    void setOnSubmitTextCommand(OnSubmitTextCommand onSubmitTextCommand);

    void clear();
}
