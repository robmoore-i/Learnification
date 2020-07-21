package com.rrm.learnification.dailyreport.creation;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DailyReportTextGeneratorTest {
    @Test
    public void returnsTextCongratulatingUser() {
        DailyReportTextGenerator dailyReportTextGenerator = new DailyReportTextGenerator();
        String text = dailyReportTextGenerator.getText();
        assertEquals("You completed at least one learnification today! Well done! 🎉", text);
    }
}