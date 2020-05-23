package com.rrm.learnification.logger;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class LogcatLogParserTest {
    private final LogcatLogParser parser = new LogcatLogParser(new StdOutLogger());

    @Test
    public void parsesLearnificationLogLine() {
        String learnificationLogLine = "2020-04-25 13:55:49.000 26673-26673/com.rrm.learnification I/Learnification:AndroidInternalStorageAdaptor: appending " +
                "lines to 'generated-next-id-jobs'";
        LogLine log = parser.parse(learnificationLogLine);
        assertThat(log.date, equalTo("04-25"));
        assertThat(log.time, equalTo("13:55:49.000"));
        assertThat(log.level, equalTo("I"));
        assertThat(log.tag, equalTo("Learnification:AndroidInternalStorageAdaptor"));
        assertThat(log.creator, equalTo("AndroidInternalStorageAdaptor"));
        assertThat(log.message, equalTo("appending lines to 'generated-next-id-jobs'"));
    }

    @Test
    public void parsesAndroidLogLine() {
        String androidLogLine = "2020-04-25 13:55:50.306 26673-26687/com.rrm.learnification W/SQLiteConnectionPool: A SQLiteConnection object for database " +
                "'/data/user/0/com.rrm.learnification/databases/LearnificationApps.db' was leaked!  Please fix your application to end transactions in " +
                "progress properly and to close the database when it is no longer needed.";
        LogLine log = parser.parse(androidLogLine);
        assertThat(log.date, equalTo("04-25"));
        assertThat(log.time, equalTo("13:55:50.306"));
        assertThat(log.level, equalTo("W"));
        assertThat(log.tag, equalTo("SQLiteConnectionPool"));
        assertThat(log.creator, equalTo("Android"));
        assertThat(log.message, equalTo("A SQLiteConnection object for database '/data/user/0/com.rrm.learnification/databases/LearnificationApps.db' was " +
                "leaked!  Please fix your application to end transactions in progress properly and to close the database when it is no longer needed."));
    }

    @Test
    public void parsesLearnificationLogLineReadFromProcessReader() {
        String learnificationLogLineFromLogcatProcessReader = "04-25 14:42:10.889 28240 28240 I Learnification:MainActivity: initial learning items are " +
                "[LearningItem{left='ไม่เย็นไร', right='no problem', learningItemSetName='Thai'}, LearningItem{left='จำได้', right='remember', " +
                "learningItemSetName='Thai'}, LearningItem{left='เข้าใจ', right='understand', learningItemSetName='Thai'}, LearningItem{left='ไร้สาระ', " +
                "right='silly', learningItemSetName='Thai'}, LearningItem{left='นิสัย', right='personality', learningItemSetName='Thai'}]";
        LogLine log = parser.parse(learnificationLogLineFromLogcatProcessReader);
        assertThat(log.date, equalTo("04-25"));
        assertThat(log.time, equalTo("14:42:10.889"));
        assertThat(log.level, equalTo("I"));
        assertThat(log.tag, equalTo("Learnification:MainActivity"));
        assertThat(log.creator, equalTo("MainActivity"));
        assertThat(log.message, equalTo("initial learning items are [LearningItem{left='ไม่เย็นไร', right='no problem', learningItemSetName='Thai'}, " +
                "LearningItem{left='จำได้', right='remember', learningItemSetName='Thai'}, LearningItem{left='เข้าใจ', right='understand', " +
                "learningItemSetName='Thai'}, LearningItem{left='ไร้สาระ', right='silly', learningItemSetName='Thai'}, LearningItem{left='นิสัย', " +
                "right='personality', learningItemSetName='Thai'}]"));
    }

    @Test
    public void parsesZygoteLogLine() {
        String zygoteLogLine = "04-25 14:42:11.065 28240 28240 I zygote  :   at void android.support.v7.app.AppCompatDelegateImpl.ensureSubDecor() " +
                "(AppCompatDelegateImpl.java:518)";
        LogLine log = parser.parse(zygoteLogLine);
        assertThat(log.date, equalTo("04-25"));
        assertThat(log.time, equalTo("14:42:11.065"));
        assertThat(log.level, equalTo("I"));
        assertThat(log.tag, equalTo("Zygote"));
        assertThat(log.creator, equalTo("Android"));
        assertThat(log.message, equalTo("at void android.support.v7.app.AppCompatDelegateImpl.ensureSubDecor() (AppCompatDelegateImpl.java:518)"));
    }
}