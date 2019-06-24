package com.rrm.learnification;

import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class LearnificationTextGeneratorTest {
    @Test
    public void generatesEgyptCapitalCityLearnification() {
        Randomiser stubRandomiser = new Randomiser() {
            @Override
            public String randomLearnificationQuestion(List<LearningItem> learningItems) {
                return learningItems.get(0).left;
            }
        };
        LearnificationTextGenerator learnificationTextGenerator = new LearnificationTextGenerator(stubRandomiser, LearnificationRepository.getInstance());

        assertThat(learnificationTextGenerator.notificationText(), equalTo("What is the capital city of Egypt?"));
    }
}