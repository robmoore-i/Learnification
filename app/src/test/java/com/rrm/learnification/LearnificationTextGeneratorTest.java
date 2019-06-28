package com.rrm.learnification;

import org.junit.Test;

import java.util.Collections;
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
        LearnificationRepository stubLearnificationRepository = new LearnificationRepository() {
            @Override
            public List<LearningItem> learningItems() {
                LearningItemTemplate learningItemTemplate = new LearningItemTemplate("What is the capital city of", "Which country has the capital city");
                return Collections.singletonList(learningItemTemplate.build("Egypt", "Cairo"));
            }

            @Override
            public List<String> learningItemsAsStringList() {
                return null;
            }

            @Override
            public void add(LearningItem learningItem) {
            }
        };

        LearnificationTextGenerator learnificationTextGenerator = new LearnificationTextGenerator(stubRandomiser, stubLearnificationRepository);

        assertThat(learnificationTextGenerator.notificationText(), equalTo("What is the capital city of Egypt?"));
    }
}