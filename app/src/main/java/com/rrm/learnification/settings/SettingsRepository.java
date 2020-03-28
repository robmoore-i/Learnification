package com.rrm.learnification.settings;

import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.publication.LearnificationTextGenerator;
import com.rrm.learnification.settings.learnificationpromptstrategy.LearnificationPromptStrategy;
import com.rrm.learnification.storage.FileStorageAdaptor;
import com.rrm.learnification.storage.LearningItemSupplier;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.rrm.learnification.settings.learnificationpromptstrategy.LearnificationPromptStrategy.LEFT_TO_RIGHT;

public class SettingsRepository {
    private static final String LOG_TAG = "SettingsRepository";

    public static final String LEARNIFICATION_DELAY_FILE_NAME = "settings_delay";
    public static final String LEARNIFICATION_PROMPT_STRATEGY_FILE_NAME = "settings_prompt_strategy";

    static final int DEFAULT_LEARNIFICATION_DELAY_SECONDS = 5;
    static final LearnificationPromptStrategy DEFAULT_LEARNIFICATION_PROMPT_STRATEGY = LEFT_TO_RIGHT;

    private final AndroidLogger logger;
    private final FileStorageAdaptor fileStorageAdaptor;

    public SettingsRepository(AndroidLogger logger, FileStorageAdaptor fileStorageAdaptor) {
        this.logger = logger;
        this.fileStorageAdaptor = fileStorageAdaptor;
    }

    public void writeDelay(int learnificationDelayInSeconds) {
        writeFile(LEARNIFICATION_DELAY_FILE_NAME, "learnificationDelayInSeconds", String.valueOf(learnificationDelayInSeconds));
    }

    public int readDelaySeconds() {
        return readFile(LEARNIFICATION_DELAY_FILE_NAME, DEFAULT_LEARNIFICATION_DELAY_SECONDS, Integer::parseInt);
    }

    int readDelayMinutes() {
        return readDelaySeconds() / 60;
    }

    LearnificationPromptStrategy readLearnificationPromptStrategy() {
        return readFile(LEARNIFICATION_PROMPT_STRATEGY_FILE_NAME, DEFAULT_LEARNIFICATION_PROMPT_STRATEGY, LearnificationPromptStrategy::fromName);
    }

    public void writeLearnificationPromptStrategy(LearnificationPromptStrategy learnificationPromptStrategy) {
        writeFile(LEARNIFICATION_PROMPT_STRATEGY_FILE_NAME, "learnificationPromptStrategy", learnificationPromptStrategy.name());
    }

    private void writeFile(String fileName, String key, String value) {
        logger.i(LOG_TAG, "writing '" + key + "' as '" + value + "' in file '" + fileName + "'");

        try {
            fileStorageAdaptor.overwriteLines(fileName, Collections.singletonList(key + "=" + value));
        } catch (Exception e) {
            logger.i(LOG_TAG, "failed to write value '" + value + "' to file '" + fileName + "'");
            logger.e(LOG_TAG, e);
        }
    }

    private <T> T readFile(String fileName, T defaultValue, Function<String, T> function) {
        try {
            List<String> lines = fileStorageAdaptor.readLines(fileName).stream().filter(line -> !line.isEmpty()).collect(Collectors.toList());
            return function.apply(lines.get(0).split("=")[1]);
        } catch (FileNotFoundException e) {
            logger.i(LOG_TAG, "file '" + fileName + "' wasn't found. returning default value '" + defaultValue + "'");
            return defaultValue;
        } catch (Exception e) {
            logger.e(LOG_TAG, e);
            logger.i(LOG_TAG, "returning default value '" + defaultValue + "'");
            return defaultValue;
        }
    }

    public LearnificationTextGenerator learnificationTextGenerator(LearningItemSupplier learningItemSupplier) {
        return readLearnificationPromptStrategy().toLearnificationTextGenerator(learningItemSupplier);
    }
}
