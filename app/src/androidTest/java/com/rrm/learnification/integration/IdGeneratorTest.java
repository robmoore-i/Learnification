package com.rrm.learnification.integration;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rrm.learnification.idgenerator.IdGenerator;
import com.rrm.learnification.idgenerator.serializable.SqlDatabaseIdGenerator;
import com.rrm.learnification.learningitemseteditor.LearningItemSetEditorActivity;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.test.AndroidTestObjectFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class IdGeneratorTest {
    private final AndroidLogger logger = new AndroidLogger();

    @Rule
    public ActivityTestRule<LearningItemSetEditorActivity> activityTestRule = new ActivityTestRule<>(LearningItemSetEditorActivity.class);
    private IdGenerator generator;

    @Before
    public void beforeEach() {
        AndroidTestObjectFactory androidTestObjectFactory = new AndroidTestObjectFactory(activityTestRule.getActivity());
        generator = new SqlDatabaseIdGenerator(logger, androidTestObjectFactory.getLearnificationAppDatabase(), "test");
        generator.reset();
    }

    @Test
    public void firstIdIs0() {
        assertThat(generator.nextId(), equalTo(0));
    }

    @Test
    public void secondIdIs1() {
        generator.nextId();
        assertThat(generator.nextId(), equalTo(1));
    }

    @Test
    public void thirdIdIs2() {
        generator.nextId();
        generator.nextId();
        assertThat(generator.nextId(), equalTo(2));
    }

    @Test
    public void afterReturningAnIdOnceTheLastIdIs0() {
        generator.nextId();
        assertThat(generator.lastId(), equalTo(0));
    }

    @Test
    public void afterReturningAnIdTwiceTheLastIdIs1() {
        generator.nextId();
        generator.nextId();
        assertThat(generator.lastId(), equalTo(1));
    }

    @Test
    public void ifIGetLastIdWithoutHavingSetOneBeforeItIs0() {
        assertThat(generator.lastId(), equalTo(0));
    }
}
