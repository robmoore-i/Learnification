package com.rrm.learnification;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LearnificationTextGeneratorTest {
    @Test
    public void generatesEgyptCapitalCityLearnification() {
        Randomiser stubRandomiser = new Randomiser() {
            @Override
            public String randomLearnificationQuestion(List<LearningItem> learningItems) {
                return learningItems.get(0).left;
            }
        };
        LearnificationRepository stubLearnificationRepository = mock(LearnificationRepository.class);
        LearningItemTemplate learningItemTemplate = new LearningItemTemplate("What is the capital city of", "Which country has the capital city");
        when(stubLearnificationRepository.learningItems()).thenReturn(Collections.singletonList(learningItemTemplate.build("Egypt", "Cairo")));
        LearnificationTextGenerator learnificationTextGenerator = new LearnificationTextGenerator(stubRandomiser, stubLearnificationRepository);

        assertThat(learnificationTextGenerator.notificationText(), equalTo("What is the capital city of Egypt?"));
    }
}