package com.rrm.learnification.integration;

import android.support.test.rule.ActivityTestRule;

import com.rrm.learnification.learnificationresultstorage.LearnificationPrompt;
import com.rrm.learnification.learnificationresultstorage.LearnificationResult;
import com.rrm.learnification.learnificationresultstorage.LearnificationResultEvaluation;
import com.rrm.learnification.learnificationresultstorage.LearnificationResultSqlTableClient;
import com.rrm.learnification.main.MainActivity;
import com.rrm.learnification.support.IntegrationTestWrapper;
import com.rrm.learnification.test.AndroidTestObjectFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class LearnificationResultSqlTableClientTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    private IntegrationTestWrapper integrationTestWrapper;
    private LearnificationResultSqlTableClient client;

    @Before
    public void before() {
        integrationTestWrapper = new IntegrationTestWrapper(activityTestRule.getActivity());
        integrationTestWrapper.beforeEach();
        AndroidTestObjectFactory androidTestObjectFactory = new AndroidTestObjectFactory(activityTestRule.getActivity());
        client = new LearnificationResultSqlTableClient(androidTestObjectFactory.getLearnificationAppDatabase());
    }

    @After
    public void afterEach() {
        integrationTestWrapper.afterEach();
    }

    @Test
    public void canStoreLearnificationResponse() {
        LocalDateTime twoPmMayTwentyFourth = LocalDateTime.of(2020, 5, 24, 14, 0, 0);
        LearnificationResult learnificationResult = new LearnificationResult(twoPmMayTwentyFourth, LearnificationResultEvaluation.CORRECT,
                new LearnificationPrompt("Given", "Expected"));

        client.write(learnificationResult);

        List<LearnificationResult> learnificationResults = client.readAll();
        assertEquals(1, learnificationResults.size());
        assertEquals(learnificationResult, learnificationResults.get(0));
    }
}
