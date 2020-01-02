package com.rrm.learnification.settings.learnificationpromptstrategy;

import com.rrm.learnification.button.OnClickCommand;
import com.rrm.learnification.settings.SettingsRepository;

public class SavePromptStrategyOnClickCommand implements OnClickCommand {
    private final SettingsRepository settingsRepository;
    private final LearnificationPromptStrategyRadioGroup learnificationPromptStrategyRadioGroup;

    public SavePromptStrategyOnClickCommand(SettingsRepository settingsRepository, LearnificationPromptStrategyRadioGroup learnificationPromptStrategyRadioGroup) {
        this.settingsRepository = settingsRepository;
        this.learnificationPromptStrategyRadioGroup = learnificationPromptStrategyRadioGroup;
    }

    @Override
    public void onClick() {
        settingsRepository.writeLearnificationPromptStrategy(learnificationPromptStrategyRadioGroup.getValue());
    }
}
