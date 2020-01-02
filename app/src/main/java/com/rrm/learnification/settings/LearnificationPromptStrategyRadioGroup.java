package com.rrm.learnification.settings;

import android.util.SparseArray;

import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.radiogroup.RadioGroupChangeListener;

import static com.rrm.learnification.settings.LearnificationPromptStrategy.LEFT_TO_RIGHT;
import static com.rrm.learnification.settings.LearnificationPromptStrategy.MIXED;
import static com.rrm.learnification.settings.LearnificationPromptStrategy.RIGHT_TO_LEFT;

class LearnificationPromptStrategyRadioGroup {
    private static final String LOG_TAG = "LearnificationPromptStrategyRadioGroup";

    private final AndroidLogger logger;
    private final SettingsRepository settingsRepository;

    LearnificationPromptStrategyRadioGroup(AndroidLogger logger, SettingsRepository settingsRepository, SettingsActivityView settingsActivityView) {
        this.logger = logger;
        this.settingsRepository = settingsRepository;
        bindRadioButtons(settingsActivityView);
    }

    void setToValue(LearnificationPromptStrategy learnificationPromptStrategy) {
        logger.v(LOG_TAG, "setting learnification prompt strategy to '" + learnificationPromptStrategy.name() + "'");
        settingsRepository.writeLearnificationPromptStrategy(learnificationPromptStrategy);
    }

    private void bindRadioButtons(SettingsActivityView settingsActivityView) {
        SparseArray<LearnificationPromptStrategy> radioGroupButtons = settingsActivityView.learnificationPromptStrategyRadioGroupOptions();
        RadioGroupChangeListener<LearnificationPromptStrategy> radioGroupChangeListener = new RadioGroupChangeListener<>(radioGroupButtons);
        radioGroupChangeListener.setAction(LEFT_TO_RIGHT, () -> setToValue(LEFT_TO_RIGHT));
        radioGroupChangeListener.setAction(RIGHT_TO_LEFT, () -> setToValue(RIGHT_TO_LEFT));
        radioGroupChangeListener.setAction(MIXED, () -> setToValue(MIXED));
        settingsActivityView.bindLearnificationPromptStrategyRadioGroup(radioGroupChangeListener);
    }
}
