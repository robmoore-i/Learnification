package com.rrm.learnification;

import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LearnificationTextGeneratorTest {
    @Test
    public void generatesEgyptCapitalCityLearnification() {
        Randomiser stubRandomiser = learningItems -> learningItems.get(0).left;
        LearnificationRepository stubLearnificationRepository = mock(LearnificationRepository.class);
        when(stubLearnificationRepository.learningItems()).thenReturn(Collections.singletonList(new LearningItem("Egypt", "Cairo")));
        LearnificationTextGenerator learnificationTextGenerator = new LearnificationTextGenerator(stubRandomiser, stubLearnificationRepository);

        assertThat(learnificationTextGenerator.notificationText(), equalTo("Egypt"));
    }
}