package com.rrm.learnification.learningitemseteditor.learningitemadd;

import com.rrm.learnification.learningitemseteditor.learningitemlist.dynamicbuttons.OnTextChangeListener;
import com.rrm.learnification.learningitemseteditor.learningitemlistedit.OnFocusGainedCommand;

public interface TextInput {
    void setOnTextChangeListener(OnTextChangeListener onTextChangeListener);

    void setOnFocusGainedListener(OnFocusGainedCommand setButtonIntoAddModeOnFocusGained);

    void setOnSubmitTextCommand(OnSubmitTextCommand onSubmitTextCommand);

    void clear();
}
