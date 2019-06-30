package com.rrm.learnification;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersistentLearnificationRepositoryTest {
    private final AndroidLogger androidLogger = mock(AndroidLogger.class);

    @Test
    public void canAddLearningItems() {
        LearnificationStorage stubLearnificationStorage = mock(LearnificationStorage.class);
        when(stubLearnificationStorage.read()).thenReturn(new ArrayList<>());
        PersistentLearnificationRepository persistentLearnificationRepository = new PersistentLearnificationRepository(androidLogger, stubLearnificationStorage);

        persistentLearnificationRepository.add(new LearningItem("L", "R"));

        List<LearningItem> learningItems = persistentLearnificationRepository.learningItems();
        assertThat(learningItems.size(), equalTo(1));
        assertThat(learningItems.get(0).left, equalTo("L"));
        assertThat(learningItems.get(0).right, equalTo("R"));
    }
}