package com.rrm.learnification.common;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LearnificationTextGeneratorTest {
    @Test
    public void generatesEgyptCapitalCityLearnification() throws CantGenerateNotificationTextException {
        Randomiser stubRandomiser = learningItems -> new LearnificationText(learningItems.get(0).left, learningItems.get(0).right, "Learn");
        LearningItemRepository stubLearningItemRepository = mock(LearningItemRepository.class);
        when(stubLearningItemRepository.learningItems()).thenReturn(Collections.singletonList(new LearningItem("Egypt", "Cairo")));
        LearnificationTextGenerator learnificationTextGenerator = new LearnificationTextGenerator(stubRandomiser, stubLearningItemRepository);

        assertThat(learnificationTextGenerator.learnificationText().given, equalTo("Egypt"));
    }

    @Test(expected = CantGenerateNotificationTextException.class)
    public void itThrowsCantGenerateNotificationTextExceptionIfThereAreNoLearningItems() throws CantGenerateNotificationTextException {
        Randomiser stubRandomiser = learningItems -> new LearnificationText(learningItems.get(0).left, learningItems.get(0).right, "Learn");
        LearningItemRepository stubLearningItemRepository = mock(LearningItemRepository.class);
        when(stubLearningItemRepository.learningItems()).thenReturn(new ArrayList<>());
        LearnificationTextGenerator learnificationTextGenerator = new LearnificationTextGenerator(stubRandomiser, stubLearningItemRepository);

        learnificationTextGenerator.learnificationText();
    }
}