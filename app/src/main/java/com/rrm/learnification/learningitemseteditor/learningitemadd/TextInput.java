package com.rrm.learnification.learningitemseteditor.learningitemadd;

import com.rrm.learnification.learningitemseteditor.buttonbinding.OnFocusGainedCommand;
import com.rrm.learnification.learningitemseteditor.learningitemlist.dynamicbuttons.OnTextChangeListener;

public interface TextInput {
    void setOnTextChangeListener(OnTextChangeListener onTextChangeListener);

    void setOnFocusGainedListener(OnFocusGainedCommand setButtonIntoAddModeOnFocusGained);

    void setOnSubmitTextCommand(OnSubmitTextCommand onSubmitTextCommand);

    void clear();
}
