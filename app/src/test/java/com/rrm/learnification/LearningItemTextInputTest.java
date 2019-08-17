package com.rrm.learnification;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LearningItemTextInputTest {
    @Test
    public void delegatesToAddLearningItemViewWhenTextChanges() {
        AddLearningItemView mock = mock(AddLearningItemView.class);
        LearningItemTextInput learningItemTextInput = new LearningItemTextInput(mock);
        OnTextChangeListener onTextChangeListener = mock(OnTextChangeListener.class);

        learningItemTextInput.setOnTextChangeListener(onTextChangeListener);

        verify(mock, times(1)).setOnTextChangeListener(onTextChangeListener);
    }
}