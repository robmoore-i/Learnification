package com.rrm.learnification.learningitemseteditor;

import com.rrm.learnification.textinput.OnSubmitTextCommand;
import com.rrm.learnification.textinput.OnTextChangeListener;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LearningItemTextInputTest {
    private final AddLearningItemView mockView = mock(AddLearningItemView.class);

    @Before
    public void beforeEach() {
        reset(mockView);
    }

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

        learningItemTextInput.getLearningItem();

        verify(mockView, times(1)).addLearningItemTextInput();
    }

    @Test
    public void itDelegatesToTheUnderlyingViewWhenClearingView() {
        LearningItemTextInput learningItemTextInput = new LearningItemTextInput(mockView);

        learningItemTextInput.clear();

        verify(mockView, times(1)).addLearningItemClearTextInput();
    }
}