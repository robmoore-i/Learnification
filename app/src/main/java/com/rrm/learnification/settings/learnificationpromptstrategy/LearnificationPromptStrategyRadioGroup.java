package com.rrm.learnification.settings.learnificationpromptstrategy;

import android.util.SparseArray;

import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.radiogroup.RadioGroupChangeListener;
import com.rrm.learnification.settings.SettingsActivityView;
import com.rrm.learnification.settings.SettingsRepository;

import static com.rrm.learnification.settings.learnificationpromptstrategy.LearnificationPromptStrategy.LEFT_TO_RIGHT;
import static com.rrm.learnification.settings.learnificationpromptstrategy.LearnificationPromptStrategy.MIXED;
import static com.rrm.learnification.settings.learnificationpromptstrategy.LearnificationPromptStrategy.RIGHT_TO_LEFT;

public class LearnificationPromptStrategyRadioGroup {
    private static final String LOG_TAG = "LearnificationPromptStrategyRadioGroup";

    private final AndroidLogger logger;
    private final SettingsRepository settingsRepository;

    public LearnificationPromptStrategyRadioGroup(AndroidLogger logger, SettingsRepository settingsRepository, SettingsActivityView settingsActivityView) {
        this.logger = logger;
        this.settingsRepository = settingsRepository;
        bindRadioButtons(settingsActivityView);
    }

    public void setToValue(LearnificationPromptStrategy learnificationPromptStrategy) {
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
