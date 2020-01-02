package com.rrm.learnification.settings.learnificationpromptstrategy;

import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.radiogroup.RadioGroupMappings;
import com.rrm.learnification.settings.SettingsRepository;

import static com.rrm.learnification.settings.learnificationpromptstrategy.LearnificationPromptStrategy.LEFT_TO_RIGHT;
import static com.rrm.learnification.settings.learnificationpromptstrategy.LearnificationPromptStrategy.MIXED;
import static com.rrm.learnification.settings.learnificationpromptstrategy.LearnificationPromptStrategy.RIGHT_TO_LEFT;

public class LearnificationPromptStrategyRadioGroup {
    private static final String LOG_TAG = "LearnificationPromptStrategyRadioGroup";

    private final AndroidLogger logger;
    private final SettingsRepository settingsRepository;
    private final RadioGroupMappings<LearnificationPromptStrategy> radioGroupMappings;
    private final LearnificationPromptStrategyRadioGroupView view;

    public LearnificationPromptStrategyRadioGroup(AndroidLogger logger, SettingsRepository settingsRepository, LearnificationPromptStrategyRadioGroupView view) {
        this.logger = logger;
        this.settingsRepository = settingsRepository;
        this.view = view;
        this.radioGroupMappings = this.view.radioGroupMappings();
        bindRadioButtons();
    }

    public void checkValue(LearnificationPromptStrategy learnificationPromptStrategy) {
        int radioButtonViewId = radioGroupMappings.idOfOption(learnificationPromptStrategy);
        logger.v(LOG_TAG, "checking radio button for learnification prompt strategy '" + learnificationPromptStrategy.name() + "', with view id '" + radioButtonViewId + "'");
        view.checkLearnificationPromptStrategy(radioButtonViewId);
    }

    private void writeValue(LearnificationPromptStrategy learnificationPromptStrategy) {
        logger.v(LOG_TAG, "setting learnification prompt strategy to '" + learnificationPromptStrategy.name() + "'");
        settingsRepository.writeLearnificationPromptStrategy(learnificationPromptStrategy);
    }

    private void bindRadioButtons() {
        radioGroupMappings.setAction(LEFT_TO_RIGHT, () -> writeValue(LEFT_TO_RIGHT));
        radioGroupMappings.setAction(RIGHT_TO_LEFT, () -> writeValue(RIGHT_TO_LEFT));
        radioGroupMappings.setAction(MIXED, () -> writeValue(MIXED));
        view.bindLearnificationPromptStrategyRadioGroup(radioGroupMappings);
    }
}
