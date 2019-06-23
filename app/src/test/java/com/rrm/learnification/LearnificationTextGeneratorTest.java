package com.rrm.learnification;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class LearnificationTextGeneratorTest {
    @Test
    public void generatesEgyptCapitalCityLearnification() {
        LearnificationTextGenerator learnificationTextGenerator = new LearnificationTextGenerator();

        assertThat(learnificationTextGenerator.notificationText(), equalTo("What is the capital city of Egypt?"));
    }
}