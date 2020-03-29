package com.rrm.learnification.learningitemseteditor;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.common.LearningItemText;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.storage.PersistentLearningItemRepository;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddLearningItemOnClickCommandTest {
    private final LearningItem testLearningItem = new LearningItem("left", "right", "default");

    private final AndroidLogger dummyLogger = mock(AndroidLogger.class);
    private final LearningItemTextInput stubInput = mock(LearningItemTextInput.class);
    private final PersistentLearningItemRepository mockRepository = mock(PersistentLearningItemRepository.class);
    private final LearningItemList mockList = mock(LearningItemList.class);

    @Test
    public void savesLearningItemFromTheTextInputIntoTheRepository() {
        LearningItemText testLearningItemText = testLearningItem.toDisplayString();
        when(stubInput.getText()).thenReturn(testLearningItemText);
        when(mockRepository.get(testLearningItemText)).thenReturn(testLearningItem);
        AddLearningItemOnClickCommand addLearningItemOnClickCommand = new AddLearningItemOnClickCommand(dummyLogger, stubInput, mockRepository, mockList);

        addLearningItemOnClickCommand.onClick();

        verify(mockRepository, times(1)).add(testLearningItemText);
    }

    @Test
    public void addsLearningItemSingleStringFormIntoTheList() {
        when(stubInput.getText()).thenReturn(testLearningItem.toDisplayString());
        when(mockRepository.get(testLearningItem.toDisplayString())).thenReturn(testLearningItem);
        AddLearningItemOnClickCommand addLearningItemOnClickCommand = new AddLearningItemOnClickCommand(dummyLogger, stubInput, mockRepository, mockList);

        addLearningItemOnClickCommand.onClick();

        verify(mockList, times(1)).addTextEntry(testLearningItem.toDisplayString());
    }

    @Test(expected = CantAddLearningItemException.class)
    public void ifInputThrowsExceptionThenItThrowsARuntimeException() {
        when(stubInput.getText()).thenThrow(new IllegalArgumentException());
        AddLearningItemOnClickCommand addLearningItemOnClickCommand = new AddLearningItemOnClickCommand(dummyLogger, stubInput, mockRepository, mockList);

        addLearningItemOnClickCommand.onClick();
    }
}