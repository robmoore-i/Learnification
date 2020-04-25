package com.rrm.learnification.logger;

import java.util.Arrays;

public class LogcatLogParser {
    private static final String LOG_TAG = "LogcatLogParser";
    private final LearnificationLogger logger;

    public LogcatLogParser() {
        this(LearnificationLogger.noneLogger());
    }

    LogcatLogParser(LearnificationLogger logger) {
        this.logger = logger;
    }

    @SuppressWarnings("unused")
    LogLine parse(String line) {
        logger.v(LOG_TAG, "parsing log line '" + line + "'");
        String[] split = line.split(" ");
        String date = split[0];
        if (date.length() == 5) {
            return parseLogFromProcessReader(line);
        } else {
            return parseLogFromLogcatViewer(line);
        }
    }

    private LogLine parseLogFromLogcatViewer(String line) {
        try {
            String[] split = line.split(" ");
            String date = split[0].substring(5);
            logger.v(LOG_TAG, "date is " + date);
            String time = split[1];
            logger.v(LOG_TAG, "time is " + time);
            String packageInfo = split[2];
            logger.v(LOG_TAG, "package info is " + packageInfo);
            String levelAndTag = split[3];
            String level = levelAndTag.substring(0, 1);
            logger.v(LOG_TAG, "level is " + level);
            String tag = levelAndTag.substring(2, levelAndTag.length() - 1);
            logger.v(LOG_TAG, "tag is " + tag);
            String creator;
            if (tag.startsWith("Learnification:")) {
                creator = tag.substring("Learnification:".length());
            } else {
                creator = "Android";
            }
            logger.v(LOG_TAG, "creator is " + creator);
            String message = String.join(" ", Arrays.copyOfRange(split, 4, split.length));
            logger.v(LOG_TAG, "message is " + message);
            return new LogLine(date, time, level, tag, creator, message);
        } catch (IndexOutOfBoundsException e) {
            logger.e(LOG_TAG, new RuntimeException("failed to parse log line '" + line + "'", e));
            return LogLine.empty();
        }
    }

    private LogLine parseLogFromProcessReader(String line) {
        try {
            String[] split = line.split(" ");
            String date = "" + split[0];
            logger.v(LOG_TAG, "date is " + date);
            String time = split[1];
            logger.v(LOG_TAG, "time is " + time);
            String level = split[4];
            logger.v(LOG_TAG, "level is " + level);
            String tagUnknownCase = split[5];
            String tag = tagUnknownCase.replaceFirst(tagUnknownCase.substring(0, 1), tagUnknownCase.substring(0, 1).toUpperCase());
            if (tag.endsWith(":")) {
                tag = tag.substring(0, tag.length() - 1);
            }
            logger.v(LOG_TAG, "tag is " + tag);
            String creator;
            if (tag.startsWith("Learnification:")) {
                creator = tag.substring("Learnification:".length());
            } else {
                creator = "Android";
            }
            logger.v(LOG_TAG, "creator is " + creator);
            String messageElements = String.join(" ", Arrays.copyOfRange(split, 6, split.length)).trim();
            if (messageElements.startsWith(":")) {
                messageElements = messageElements.substring(1);
            }
            String message = messageElements.trim();
            logger.v(LOG_TAG, "message is " + message);
            return new LogLine(date, time, level, tag, creator, message);
        } catch (IndexOutOfBoundsException e) {
            logger.e(LOG_TAG, new RuntimeException("failed to parse log line '" + line + "'", e));
            return LogLine.empty();
        }
    }
}
