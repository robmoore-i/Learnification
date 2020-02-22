package com.rrm.learnification.learningitemseteditor;

import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.storage.ItemRepository;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AddLearningItemOnClickCommandTest {
    private final LearningItem testLearningItem = new LearningItem("left", "right");
    private LearningItemTextInput stubInput = mock(LearningItemTextInput.class);
    @SuppressWarnings("unchecked")
    private ItemRepository<LearningItem> mockRepository = mock(ItemRepository.class);
    private LearningItemList mockList = mock(LearningItemList.class);

    @Before
    public void beforeEach() {
        reset(stubInput, mockRepository, mockList);
    }

    @Test
    public void savesLearningItemFromTheTextInputIntoTheRepository() {
        when(stubInput.getLearningItem()).thenReturn(testLearningItem);
        AddLearningItemOnClickCommand addLearningItemOnClickCommand = new AddLearningItemOnClickCommand(stubInput, mockRepository, mockList);

        addLearningItemOnClickCommand.onClick();

        verify(mockRepository, times(1)).add(testLearningItem);
    }

    @Test
    public void addsLearningItemSingleStringFormIntoTheList() {
        when(stubInput.getLearningItem()).thenReturn(testLearningItem);
        AddLearningItemOnClickCommand addLearningItemOnClickCommand = new AddLearningItemOnClickCommand(stubInput, mockRepository, mockList);

        addLearningItemOnClickCommand.onClick();

        verify(mockList, times(1)).addTextEntry(testLearningItem.asSingleString());
    }

    @Test(expected = CantAddLearningItemException.class)
    public void ifInputThrowsExceptionThenItThrowsARuntimeException() {
        when(stubInput.getLearningItem()).thenThrow(new IllegalArgumentException());
        AddLearningItemOnClickCommand addLearningItemOnClickCommand = new AddLearningItemOnClickCommand(stubInput, mockRepository, mockList);

        addLearningItemOnClickCommand.onClick();
    }
}