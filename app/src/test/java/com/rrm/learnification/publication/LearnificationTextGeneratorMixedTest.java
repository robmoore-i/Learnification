package com.rrm.learnification.publication;

import com.rrm.learnification.common.LearnificationText;
import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.random.Randomiser;
import com.rrm.learnification.settings.learnificationpromptstrategy.LearnificationPromptStrategy;
import com.rrm.learnification.storage.PersistentLearningItemRepository;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LearnificationTextGeneratorMixedTest {
    @Test
    public void generatesEgyptCapitalCityLearnification() {
        LearnificationTextGenerator learnificationTextGenerator = getLearnificationTextGenerator(Collections.singletonList(new LearningItem("Egypt", "Cairo", "default")));

        assertThat(learnificationTextGenerator.learnificationText().given, equalTo("Egypt"));
    }

    @Test(expected = IllegalStateException.class)
    public void itThrowsCantGenerateNotificationTextExceptionIfThereAreNoLearningItems() {
        LearnificationTextGenerator learnificationTextGenerator = getEmptyLearnificationTextGenerator();

        learnificationTextGenerator.learnificationText();
    }

    private LearnificationTextGenerator getEmptyLearnificationTextGenerator() {
        Randomiser stubRandomiser = stubRandomiser();
        PersistentLearningItemRepository stubItemRepository = mock(PersistentLearningItemRepository.class);
        when(stubItemRepository.itemsOrThrowIfEmpty()).thenThrow(new IllegalStateException("oh no!"));
        return new LearnificationPromptStrategy.MixedLearnificationPromptStrategy(stubRandomiser).toLearnificationTextGenerator(stubItemRepository);
    }

    private LearnificationTextGenerator getLearnificationTextGenerator(List<LearningItem> learningItems) {
        Randomiser stubRandomiser = stubRandomiser();
        PersistentLearningItemRepository stubItemRepository = mock(PersistentLearningItemRepository.class);
        when(stubItemRepository.itemsOrThrowIfEmpty()).thenReturn(learningItems);
        return new LearnificationPromptStrategy.MixedLearnificationPromptStrategy(stubRandomiser).toLearnificationTextGenerator(stubItemRepository);
    }

    private Randomiser stubRandomiser() {
        return new Randomiser() {
            @Override
            public LearnificationText randomMixedLearnificationQuestion(List<LearningItem> learningItems) {
                return new LearnificationText(learningItems.get(0).left, learningItems.get(0).right);
            }

            @Override
            public LearnificationText randomLeftToRightLearnificationQuestion(List<LearningItem> learningItems) {
                return null;
            }

            @Override
            public LearnificationText randomRightToLeftLearnificationQuestion(List<LearningItem> learningItems) {
                return null;
            }
        };
    }
}