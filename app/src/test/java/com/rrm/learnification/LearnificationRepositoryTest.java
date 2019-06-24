package com.rrm.learnification;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class LearnificationRepositoryTest {
    @Test
    public void singleLearnificationIsReturnedAsAHyphenSeparatedString() {
        LearningItemTemplate learningItemTemplate = new LearningItemTemplate("What is the capital city of", "Which country has the capital city");
        List<LearningItem> learningItems = Collections.singletonList(learningItemTemplate.build("Egypt", "Cairo"));
        LearnificationRepository learnificationRepository = new LearnificationRepository(learningItems);

        List<String> strings = learnificationRepository.learningItemsAsStringList();

        assertThat(strings.size(), equalTo(1));
        assertThat(strings.get(0), equalTo("What is the capital city of Egypt? - Which country has the capital city Cairo?"));
    }

    @Test
    public void twoLearnificationsAreReturnedAsHyphenSeparatedStrings() {
        LearningItemTemplate learningItemTemplate = new LearningItemTemplate("What is the capital city of", "Which country has the capital city");
        ArrayList<LearningItem> learningItems = new ArrayList<>();
        learningItems.add(learningItemTemplate.build("Egypt", "Cairo"));
        learningItems.add(learningItemTemplate.build("France", "Paris"));
        LearnificationRepository learnificationRepository = new LearnificationRepository(learningItems);

        List<String> strings = learnificationRepository.learningItemsAsStringList();

        assertThat(strings.size(), equalTo(2));
        assertThat(strings.get(0), equalTo("What is the capital city of Egypt? - Which country has the capital city Cairo?"));
        assertThat(strings.get(1), equalTo("What is the capital city of France? - Which country has the capital city Paris?"));
    }
}