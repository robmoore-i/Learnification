package com.rrm.learnification.settings.learnificationpromptstrategy;

import com.rrm.learnification.logger.AndroidLogger;
import com.rrm.learnification.settings.radiogroup.RadioGroupMappings;
import com.rrm.learnification.settings.radiogroup.RadioGroupView;

import static com.rrm.learnification.settings.learnificationpromptstrategy.LearnificationPromptStrategy.LEFT_TO_RIGHT;
import static com.rrm.learnification.settings.learnificationpromptstrategy.LearnificationPromptStrategy.MIXED;
import static com.rrm.learnification.settings.learnificationpromptstrategy.LearnificationPromptStrategy.RIGHT_TO_LEFT;

public class LearnificationPromptStrategyRadioGroup {
    private static final String LOG_TAG = "LearnificationPromptStrategyRadioGroup";

    private final AndroidLogger logger;
    private final RadioGroupView<LearnificationPromptStrategy> view;
    private final RadioGroupMappings<LearnificationPromptStrategy> radioGroupMappings;

    public LearnificationPromptStrategyRadioGroup(AndroidLogger logger, RadioGroupView<LearnificationPromptStrategy> view) {
        this.logger = logger;
        this.view = view;
        this.radioGroupMappings = this.view.radioGroupMappings();
        bindRadioButtonActions();
    }

    LearnificationPromptStrategy getValue() {
        return radioGroupMappings.optionOfViewId(view.viewIdOfCheckedOption());
    }

    public void setValue(LearnificationPromptStrategy learnificationPromptStrategy) {
        int radioButtonViewId = radioGroupMappings.viewIdOfOption(learnificationPromptStrategy);
        logger.i(LOG_TAG, "checking radio button for learnification prompt strategy '" + learnificationPromptStrategy.name() + "', " +
                "with view id '" + radioButtonViewId + "'");
        view.checkOption(radioButtonViewId);
    }

    private void bindRadioButtonActions() {
        radioGroupMappings.setAction(LEFT_TO_RIGHT, () -> logRadioButtonClick(LEFT_TO_RIGHT));
        radioGroupMappings.setAction(RIGHT_TO_LEFT, () -> logRadioButtonClick(RIGHT_TO_LEFT));
        radioGroupMappings.setAction(MIXED, () -> logRadioButtonClick(MIXED));
        view.bindActionsToRadioGroupOptions(radioGroupMappings);
    }

    private void logRadioButtonClick(LearnificationPromptStrategy learnificationPromptStrategy) {
        logger.i(LOG_TAG, "setting learnification prompt strategy to '" + learnificationPromptStrategy.name() + "'");
    }
}
