package com.rrm.learnification.settings;

import com.rrm.learnification.button.OnClickCommand;
import com.rrm.learnification.settings.learnificationpromptstrategy.LearnificationPromptStrategyRadioGroup;

class SavePromptStrategyOnClickCommand implements OnClickCommand {
    private final SettingsRepository settingsRepository;
    private final LearnificationPromptStrategyRadioGroup learnificationPromptStrategyRadioGroup;

    SavePromptStrategyOnClickCommand(SettingsRepository settingsRepository, LearnificationPromptStrategyRadioGroup learnificationPromptStrategyRadioGroup) {
        this.settingsRepository = settingsRepository;
        this.learnificationPromptStrategyRadioGroup = learnificationPromptStrategyRadioGroup;
    }

    @Override
    public void onClick() {
        settingsRepository.writeLearnificationPromptStrategy(learnificationPromptStrategyRadioGroup.getValue());
    }
}
