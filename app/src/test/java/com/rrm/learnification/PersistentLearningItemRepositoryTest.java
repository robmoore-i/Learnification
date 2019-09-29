package com.rrm.learnification;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersistentLearningItemRepositoryTest {
    private final AndroidLogger androidLogger = mock(AndroidLogger.class);

    @Test
    public void canAddLearningItems() {
        LearningItemStorage stubLearningItemStorage = mock(LearningItemStorage.class);
        when(stubLearningItemStorage.read()).thenReturn(new ArrayList<>());
        PersistentLearningItemRepository persistentLearnificationRepository = new PersistentLearningItemRepository(androidLogger, stubLearningItemStorage);

        persistentLearnificationRepository.add(new LearningItem("L", "R"));

        List<LearningItem> learningItems = persistentLearnificationRepository.learningItems();
        assertThat(learningItems.size(), equalTo(1));
        assertThat(learningItems.get(0).left, equalTo("L"));
        assertThat(learningItems.get(0).right, equalTo("R"));
    }

    @Test
    public void returnsLearningItemsWithLatestEntryFirst() {
        LearningItemStorage stubLearningItemStorage = mock(LearningItemStorage.class);
        when(stubLearningItemStorage.read()).thenReturn(new ArrayList<>());
        PersistentLearningItemRepository persistentLearnificationRepository = new PersistentLearningItemRepository(androidLogger, stubLearningItemStorage);

        persistentLearnificationRepository.add(new LearningItem("L1", "R1"));
        persistentLearnificationRepository.add(new LearningItem("L2", "R2"));
        persistentLearnificationRepository.add(new LearningItem("L3", "R3"));

        List<LearningItem> learningItems = persistentLearnificationRepository.learningItems();
        assertThat(learningItems.get(0).left, equalTo("L3"));
        assertThat(learningItems.get(0).right, equalTo("R3"));
    }
}