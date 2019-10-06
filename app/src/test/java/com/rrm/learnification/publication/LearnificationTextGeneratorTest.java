package com.rrm.learnification.publication;

import com.rrm.learnification.common.LearnificationText;
import com.rrm.learnification.common.LearningItem;
import com.rrm.learnification.notification.CantGenerateNotificationTextException;
import com.rrm.learnification.random.Randomiser;
import com.rrm.learnification.storage.ItemRepository;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SuppressWarnings("unchecked")
public class LearnificationTextGeneratorTest {
    @Test
    public void generatesEgyptCapitalCityLearnification() throws CantGenerateNotificationTextException {
        Randomiser stubRandomiser = learningItems -> new LearnificationText(learningItems.get(0).left, learningItems.get(0).right, "Learn");
        ItemRepository<LearningItem> stubItemRepository = mock(ItemRepository.class);
        when(stubItemRepository.items()).thenReturn(Collections.singletonList(new LearningItem("Egypt", "Cairo")));
        LearnificationTextGenerator learnificationTextGenerator = new LearnificationTextGenerator(stubRandomiser, stubItemRepository);

        assertThat(learnificationTextGenerator.learnificationText().given, equalTo("Egypt"));
    }

    @Test(expected = CantGenerateNotificationTextException.class)
    public void itThrowsCantGenerateNotificationTextExceptionIfThereAreNoLearningItems() throws CantGenerateNotificationTextException {
        Randomiser stubRandomiser = learningItems -> new LearnificationText(learningItems.get(0).left, learningItems.get(0).right, "Learn");
        ItemRepository<LearningItem> stubItemRepository = mock(ItemRepository.class);
        when(stubItemRepository.items()).thenReturn(new ArrayList<>());
        LearnificationTextGenerator learnificationTextGenerator = new LearnificationTextGenerator(stubRandomiser, stubItemRepository);

        learnificationTextGenerator.learnificationText();
    }
}