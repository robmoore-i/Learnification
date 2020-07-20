package com.rrm.learnification.learningitemseteditor.learningitemadd;

import com.rrm.learnification.learningitemseteditor.learningitemlist.dynamicbuttons.OnTextChangeListener;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LearningItemTextInputTest {
    private final AddLearningItemTextInputView mockView = mock(AddLearningItemTextInputView.class);

    @Test
    public void itDelegatesToTheUnderlyingViewWhenSettingOnTextChangeListener() {
        OnTextChangeListener dummy = mock(OnTextChangeListener.class);
        LearningItemTextInput learningItemTextInput = new LearningItemTextInput(mockView);

        learningItemTextInput.setOnTextChangeListener(dummy);

        verify(mockView, times(1)).addLearningItemOnTextChangeListener(dummy);
    }

    @Test
    public void itDelegatesToTheUnderlyingViewWhenSettingOnSubmitTextCommand() {
        OnSubmitTextCommand dummy = mock(OnSubmitTextCommand.class);
        LearningItemTextInput learningItemTextInput = new LearningItemTextInput(mockView);

        learningItemTextInput.setOnSubmitTextCommand(dummy);

        verify(mockView, times(1)).addLearningItemOnSubmitTextCommand(dummy);
    }

    @Test
    public void itDelegatesToTheUnderlyingViewWhenGettingLearningItem() {
        LearningItemTextInput learningItemTextInput = new LearningItemTextInput(mockView);

        learningItemTextInput.getText();

        verify(mockView, times(1)).addLearningItemTextInput();
    }

    @Test
    public void itDelegatesToTheUnderlyingViewWhenClearingView() {
        LearningItemTextInput learningItemTextInput = new LearningItemTextInput(mockView);

        learningItemTextInput.clear();

        verify(mockView, times(1)).addLearningItemClearTextInput();
    }
}