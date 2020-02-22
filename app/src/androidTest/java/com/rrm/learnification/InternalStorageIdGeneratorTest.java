package com.rrm.learnification;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.rrm.learnification.idgenerator.InternalStorageIdGenerator;
import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.main.MainActivity;
import com.rrm.learnification.storage.FileStorageAdaptor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.Collections;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class InternalStorageIdGeneratorTest {
    private final AndroidLogger logger = new AndroidLogger();

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);
    private InternalStorageIdGenerator generator;
    private FileStorageAdaptor fileStorageAdaptor;

    @Before
    public void beforeEach() {
        fileStorageAdaptor = activityTestRule.getActivity().androidTestObjectFactory().getFileStorageAdaptor();
        generator = new InternalStorageIdGenerator(logger, fileStorageAdaptor, "test");
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

    @Test
    public void ifThereIsAValueStoredOnStartupThenThatIsReadToDetermineTheNextId() throws IOException {
        fileStorageAdaptor.overwriteLines("generated-next-id-test", Collections.singletonList("5"));
        assertThat(generator.nextId(), equalTo(5));
    }

    @Test
    public void ifThereIsAValueStoredOnStartupThenThatIsReadToDetermineTheLastId() throws IOException {
        fileStorageAdaptor.overwriteLines("generated-next-id-test", Collections.singletonList("5"));
        assertThat(generator.lastId(), equalTo(4));
    }
}
