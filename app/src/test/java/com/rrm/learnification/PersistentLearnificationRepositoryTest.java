package com.rrm.learnification;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PersistentLearnificationRepositoryTest {
    private final AndroidLogger androidLogger = mock(AndroidLogger.class);

    @Test
    public void singleLearnificationIsReturnedAsAHyphenSeparatedString() {
        LearningItemTemplate learningItemTemplate = new LearningItemTemplate("What is the capital city of", "Which country has the capital city");
        List<LearningItem> learningItems = Collections.singletonList(learningItemTemplate.build("Egypt", "Cairo"));
        LearnificationStorage stubLearnificationStorage = mock(LearnificationStorage.class);
        when(stubLearnificationStorage.read()).thenReturn(learningItems);
        LearnificationRepository learnificationRepository = new PersistentLearnificationRepository(androidLogger, stubLearnificationStorage);

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
        LearnificationStorage stubLearnificationStorage = mock(LearnificationStorage.class);
        when(stubLearnificationStorage.read()).thenReturn(learningItems);
        LearnificationRepository learnificationRepository = new PersistentLearnificationRepository(androidLogger, stubLearnificationStorage);

        List<String> strings = learnificationRepository.learningItemsAsStringList();

        assertThat(strings.size(), equalTo(2));
        assertThat(strings.get(0), equalTo("What is the capital city of Egypt? - Which country has the capital city Cairo?"));
        assertThat(strings.get(1), equalTo("What is the capital city of France? - Which country has the capital city Paris?"));
    }

    @Test
    public void canAddLearningItems() {
        LearnificationStorage stubLearnificationStorage = mock(LearnificationStorage.class);
        when(stubLearnificationStorage.read()).thenReturn(new ArrayList<LearningItem>());
        LearnificationRepository learnificationRepository = new PersistentLearnificationRepository(androidLogger, stubLearnificationStorage);
        PersistentLearnificationRepository persistentLearnificationRepository = new PersistentLearnificationRepository(androidLogger, stubLearnificationStorage);

        persistentLearnificationRepository.add(new LearningItem("L", "R"));

        List<LearningItem> learningItems = persistentLearnificationRepository.learningItems();
        assertThat(learningItems.size(), equalTo(1));
        assertThat(learningItems.get(0).left, equalTo("L"));
        assertThat(learningItems.get(0).right, equalTo("R"));
    }
}