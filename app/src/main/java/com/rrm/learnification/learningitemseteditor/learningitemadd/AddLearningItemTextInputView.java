package com.rrm.learnification.learningitemseteditor.learningitemadd;

import com.rrm.learnification.common.LearningItemText;
import com.rrm.learnification.learningitemseteditor.buttonbinding.OnFocusGainedCommand;
import com.rrm.learnification.learningitemseteditor.learningitemlist.dynamicbuttons.OnTextChangeListener;

public interface AddLearningItemTextInputView {
    LearningItemText addLearningItemTextInput();

    void addLearningItemOnTextChangeListener(OnTextChangeListener onTextChangeListener);

    void addLearningItemOnSubmitTextCommand(OnSubmitTextCommand onSubmitTextCommand);

    void addLearningItemClearTextInput();

    void addLearningItemOnFocusTextBoxListener(OnFocusGainedCommand onFocusGainedCommand);
}
